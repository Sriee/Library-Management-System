package utdallas.librarymanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import utdallas.librarymanagement.bean.FineList;

public class CheckOutDAO {
	
	//Creating object for MySQL connector
	MySQLConnectionDAO connectionObj = null;

	/*
	 * Performs the checkout operation in the Library Management system 
	 * 
	 * @param
	 * bookId - Book Id is the Isbn entered by the Library Administrator
	 * 
	 * @return loanId - Loan ID of the checkout process if its successful  
	 **/
	public Integer checkOutBook(String bookID, Integer branchID, String cardNo) throws Exception {
		Integer loanID = null;

		connectionObj = new MySQLConnectionDAO();
		try {
			Connection connection = connectionObj.getSQLConnection();
			int prevBorrowedCount = 0;
			Integer noOfCopies = null;
			Integer numberOfCopiesAvailable = null;
			Integer cardIDCount = null; 

			//Checking whether the Borrower has already borrowed 3 books 
			String query = "SELECT COUNT(*) FROM BOOK_LOANS WHERE Card_no = ? AND Date_in IS NULL";
			PreparedStatement statement= connection.prepareStatement(query);
			statement.setString(1, cardNo);
			ResultSet rs = statement.executeQuery();
			while(rs.next())
				prevBorrowedCount = rs.getInt(1);

			rs.close();

			//Declaring error message if the borrower has already 3 loans borrowed
			if(!(prevBorrowedCount<3))
				throw new Exception("Sorry. Only three book loans allowed per borrower.");

			//Checking whether the card number is valid
			if(cardNo != null){
				query = "SELECT COUNT(*) FROM BORROWER WHERE Card_no = ?";
				statement= connection.prepareStatement(query);
				statement.setString(1, cardNo);
				rs = statement.executeQuery();
				while(rs.next())
					cardIDCount = rs.getInt(1);

				if(cardIDCount.equals(0))
					throw new Exception("Invalid Card Number");
				
				rs.close();
			}
			
			//Checking if the borrower associated with the card has no outstanding fines due
			List<FineList> fineList = new FinesDAO().searchFines("", "", cardNo); 
			if(fineList.size()>0)
				throw new Exception("You have outstanding bill amount.Not able to checkout");
			
			//Assigning Loan id for the new check out
			query = "SELECT MAX(Loan_id) FROM BOOK_LOANS";
			Statement stmt= connection.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next())
				loanID = rs.getInt(1);
		
			loanID++;
			rs.close();

			//Checking out book
			query = "SELECT No_of_copies FROM BOOK_COPIES WHERE Book_ID = ? AND Branch_ID = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, bookID);
			statement.setInt(2, branchID);
			rs = statement.executeQuery();
			if (!rs.isBeforeFirst() )   
				throw new Exception("No records found");
			
			while(rs.next()) {
				
				noOfCopies = rs.getInt(1);
				String availableCopiesQuery = "SELECT COUNT(*) FROM BOOK_LOANS WHERE Isbn = '"+ bookID + "' AND Branch_id = "+ branchID + " AND Date_in IS NULL";
				Statement statementBookLoans = connection.createStatement();
				ResultSet result = statementBookLoans.executeQuery(availableCopiesQuery);
				while(result.next()){
					Integer noOfCopiesLoaned = result.getInt(1);
					numberOfCopiesAvailable = noOfCopies-noOfCopiesLoaned;
				}
				result.close();
			}
			rs.close();
			
			//Notify when the book copies not available in this branch
			if(noOfCopies != null) {
				if(numberOfCopiesAvailable.equals(0)){
					throw new Exception("Sorry the book is not available in the branch. Please enter an alternate branch name");
				}
			} else{
				if(noOfCopies == null){
					throw new Exception("Sorry the book is not available in the branch. Please enter an alternate branch name");
				}
			}

			//Declaring Date variables
			String currentDateFormatted = "";
			String dueDate = "";
			
			//Gets the current date
			Date currentDate = new Date();
			
			//Formats the current date to the type specified
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
			//Formats the date and stores it as a string variable 
			currentDateFormatted = dateFormatter.format(currentDate);
			
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(dateFormatter.parse(currentDateFormatted));
				c.add(Calendar.DAY_OF_MONTH, 14);  
				dueDate  = dateFormatter.format(c.getTime()); 
			} catch (ParseException e) {
				e.printStackTrace();
			}
	
			query = "INSERT INTO BOOK_LOANS (Loan_id, Isbn, Branch_id, Card_no, Date_out, Due_date) values(?,?,?,?,?,?)";
			
			statement = connection.prepareStatement(query);
			statement.setInt(1, loanID);
			statement.setString(2, bookID);
			statement.setInt(3, branchID);
			statement.setString(4, cardNo);
			statement.setString(5, currentDateFormatted);
			statement.setString(6, dueDate);
			
			statement.executeUpdate();
			connection.close();
			return loanID;

		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
