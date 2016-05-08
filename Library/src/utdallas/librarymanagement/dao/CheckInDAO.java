package utdallas.librarymanagement.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import utdallas.librarymanagement.bean.LoanList;

public class CheckInDAO {

	MySQLConnectionDAO connectionObj = null; 

	public List<LoanList> checkInSearch(String bookID, String borrowerName, String cardNo) throws Exception {
		connectionObj = new MySQLConnectionDAO();
		List<LoanList> loanList = new ArrayList<LoanList>();
		try {
			boolean andFlag = false;
			String query = null;
			Connection connection = connectionObj.getSQLConnection();

			query = "SELECT Loan_id, Isbn, Branch_id, Card_no FROM BOOK_LOANS WHERE ";
			if(!(bookID == null || bookID =="")) {
				andFlag=true;
				query += "Isbn = '" + bookID  + "' ";
			}
			if(!(cardNo == null || cardNo == "")) {
				if(andFlag)
					query += " AND ";

				andFlag = true;
				query += "Card_no = '" + cardNo  + "' ";
			}
			if(!(borrowerName == null || borrowerName == "")) {
				if(andFlag)
					query += " AND ";

				query += "Card_no IN (SELECT Card_no FROM BORROWER WHERE Fname LIKE '%"+borrowerName+"%' or Lname LIKE '%"+borrowerName+"%')" ;
			}

			query += " AND Date_in IS NULL";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);

			while(rs.next()) {
				LoanList loan = new LoanList();
				Integer loanID = rs.getInt("Loan_id");
				Integer branchID = rs.getInt("Branch_id");
				String sqlCardNo =  rs.getString("Card_no");
				String sqlBookID = rs.getString("Isbn");
				loan.setLoanId(loanID);
				loan.setBranchId(branchID);
				loan.setCardNo(sqlCardNo);
				loan.setBookId(sqlBookID);
				loanList.add(loan);
			}

			if(loanList.size()==0)
				throw new Exception("Sorry!!! No results found");

			rs.close();
			connection.close();
			return loanList;							
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void checkInCompletion(List<Integer> loanIds) throws Exception {
		try {
			connectionObj = new MySQLConnectionDAO();
			Connection connection = connectionObj.getSQLConnection();

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String currentDate = dateFormat.format(date);

			for(int i=0;i<loanIds.size();i++) {
				Statement statement = connection.createStatement();
				String query = "UPDATE BOOK_LOANS SET Date_in = '"+ currentDate + "' WHERE Loan_id = "+loanIds.get(i);
				statement.executeUpdate(query);
			}
			connection.close();
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
