package utdallas.librarymanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import utdallas.librarymanagement.bean.FineList;


public class FinesDAO {

	MySQLConnectionDAO connectionObj = null;

	public void addFines(int loanId,double fine, Connection connection) throws Exception{

		try{
			PreparedStatement insertPrepareStmt = connection.prepareStatement("INSERT INTO FINES (" +
					" Loan_id, " +
					" fine_amt, " +
					" fine_amt_paid, " +
					" paid" +
					") VALUES (" +
					"?,?,?,?)");
			insertPrepareStmt.setInt(1, loanId);
			insertPrepareStmt.setDouble(2, fine);
			insertPrepareStmt.setDouble(3, 0);
			insertPrepareStmt.setBoolean(4, false);
			insertPrepareStmt.executeUpdate();
			insertPrepareStmt.close();
		} catch(Exception e) {
			throw e;
		}
	}

	public void updateFines(int loanId,double fine, Connection connection) throws Exception {
		String sqlStr = "";
		try {
			sqlStr = "Update FINES SET fine_amt="+fine+" WHERE Loan_id="+loanId;
			PreparedStatement updatePrepareStmt = connection.prepareStatement(sqlStr);
			updatePrepareStmt.executeUpdate();
			updatePrepareStmt.close();
		} catch (Exception e) {
			throw e;
		}
	}

	public void updateFines(int loanId, Connection connection) throws Exception {
		String sqlStr = "";
		try {
			sqlStr = "Update FINES SET paid = true WHERE Loan_id="+loanId;
			PreparedStatement updatePrepareStmt = connection.prepareStatement(sqlStr);
			updatePrepareStmt.executeUpdate();
			updatePrepareStmt.close();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void payFine(int loanId,double amt) throws Exception {
		String sqlStr = "";
		connectionObj = new MySQLConnectionDAO();
		PreparedStatement updatePrepareStmt = null;
		Statement stChckFull = null, stChckPart = null;
		ResultSet rsChckFull = null, rsChckPart = null;
		try {
			Connection connection = connectionObj.getSQLConnection();
			double fineAmountPaid = 0, fineAmount = 0;
			
			boolean checkingForLoanId = false;
			
			checkingForLoanId = checkLoanIdPresence(loanId,connection);
			
			if(checkingForLoanId){
				
				sqlStr = "SELECT (fine_amt - fine_amt_paid) AS Fine FROM FINES WHERE Loan_id ="+loanId + " AND paid = false";
				Statement stmt = connection.createStatement();
				ResultSet rs =stmt.executeQuery(sqlStr);
				while(rs.next())
					fineAmountPaid = rs.getDouble("Fine");
				
				stmt.close();
				rs.close();
				
				if(fineAmountPaid == 0.00){
					updateFines(loanId,connection);
					throw new Exception("Fine amount already paid...");
				} else if(amt > fineAmountPaid){
					throw new Exception("Seems like your are entering excess amount!...");
				} else if (Math.abs(amt - fineAmountPaid) == 0.00 ){
					sqlStr = "SELECT fine_amt_paid FROM FINES WHERE Loan_id ="+loanId + " AND paid = false";
					stChckFull = connection.createStatement();
					rsChckFull =stChckFull.executeQuery(sqlStr);
					
					while(rsChckFull.next())
						fineAmount = rsChckFull.getDouble("fine_amt_paid");
					
					sqlStr = "UPDATE FINES SET fine_amt_paid="+ (fineAmount + amt) +" WHERE Loan_id="+loanId;
					updatePrepareStmt = connection.prepareStatement(sqlStr);
					updatePrepareStmt.executeUpdate();
					updatePrepareStmt.close();
					updateFines(loanId,connection);
					stChckFull.close();
					rsChckFull.close();
				} else if (Math.abs(amt - fineAmountPaid) != 0.00 ){
					sqlStr = "SELECT fine_amt_paid FROM FINES WHERE Loan_id ="+loanId + " AND paid = false";
					stChckPart = connection.createStatement();
					rsChckPart =stChckPart.executeQuery(sqlStr);
					
					while(rsChckPart.next())
						fineAmount = rsChckPart.getDouble("fine_amt_paid");
					
					stChckPart.close();
					rsChckPart.close();
					sqlStr = "UPDATE FINES SET fine_amt_paid="+ (fineAmount + amt) +" WHERE Loan_id="+loanId;
					updatePrepareStmt = connection.prepareStatement(sqlStr);
					updatePrepareStmt.executeUpdate();
					updatePrepareStmt.close();
				}
			}else
				throw new Exception("Loan Id not present");
			
			connection.close();
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void refreshFines() throws Exception {
		String sqlStr = "";
		connectionObj = new MySQLConnectionDAO();
		try {
			Connection connection = connectionObj.getSQLConnection();
			sqlStr = "SELECT * FROM BOOK_LOANS WHERE (Date_in IS NULL OR DATEDIFF(Date_in,Due_date)>=1) ";

			PreparedStatement selectPrepareStmt = connection.prepareStatement(sqlStr);
			ResultSet dataResultSet = selectPrepareStmt.executeQuery();
			List<List> fineList=new ArrayList<List>();
			List dataList=new ArrayList();

			while (dataResultSet.next()) {
				dataList=new ArrayList();
				Date date_in=dataResultSet.getDate("Date_in");
				Date currdate=new Date();
				Date due_date=dataResultSet.getDate("Due_date");

				if(date_in==null) {
					long diff=currdate.getTime() - due_date.getTime();
					if(diff>0) {
						diff=diff/ (1000 * 60 * 60 * 24);
						dataList.add(dataResultSet.getInt("Loan_id"));
						dataList.add(diff *.25);
						fineList.add(dataList);
					}
				} else {
					long diff=date_in.getTime() - due_date.getTime();
					if(diff>0) {
						diff=diff/ (1000 * 60 * 60 * 24);
						dataList.add(dataResultSet.getInt("Loan_id"));
						dataList.add(diff *.25);
						fineList.add(dataList);
					}
				}
			}
			dataResultSet.close();
			selectPrepareStmt.close();

			//Insert data into fines table
			for(List dList:fineList) {
				int loanId=(Integer) dList.get(0);
				double fine=(Double)dList.get(1);
				if(!isLoanIdFinePaid(loanId, connection)) { // if fine not paid completely , go in
					if(checkLoanIdPresence(loanId, connection))
						updateFines(loanId, fine, connection);
					else
						addFines(loanId, fine, connection);
				}
			}
			connection.close();
		} catch (Exception e) {
			throw e;
		}
	}

	public boolean checkLoanIdPresence(int loanId, Connection connection) throws Exception {
		String sqlStr = "";
		int totalCount=0;
		try {
			sqlStr = "SELECT COUNT(*) AS count FROM FINES WHERE Loan_id="+loanId;

			PreparedStatement selectPrepareStmt = connection.prepareStatement(sqlStr);
			ResultSet dataResultSet = selectPrepareStmt.executeQuery();
			while (dataResultSet.next())
				totalCount=dataResultSet.getInt("count");
			
			dataResultSet.close();
			selectPrepareStmt.close();
			
			if(totalCount > 0)
				return true;
			else
				return false;
			
		} catch (Exception e){
			throw e;
		}
	}

	public boolean isLoanIdFinePaid(List<Integer> loanIDs) throws Exception {
		String sqlStr = "";
		int totalCount=0;
		connectionObj = new MySQLConnectionDAO();
		try	{
			Connection connection = connectionObj.getSQLConnection();
			for(int i=0; i<loanIDs.size();i++){

				sqlStr = "SELECT COUNT(*) AS count FROM FINES WHERE Loan_id="+loanIDs.get(i)+" AND paid="+true;

				PreparedStatement selectPrepareStmt = connection.prepareStatement(sqlStr);
				ResultSet dataResultSet = selectPrepareStmt.executeQuery();
				while (dataResultSet.next())
					totalCount += dataResultSet.getInt("count");

				dataResultSet.close();
				selectPrepareStmt.close();
				connection.close();
			}
		}catch (Exception e){
			throw e;
		}
		
		if(totalCount > 0)
			return true;
		else
			return false;
	}

	public boolean isLoanIdFinePaid(int loanID, Connection connection) throws Exception {

		String sqlStr = "";
		int totalCount=0;
		try {
			sqlStr = "SELECT COUNT(*) AS count FROM FINES WHERE Loan_id="+loanID+" and paid="+true;

			PreparedStatement selectPrepareStmt = connection.prepareStatement(sqlStr);
			ResultSet dataResultSet = selectPrepareStmt.executeQuery();
			while (dataResultSet.next())
				totalCount=dataResultSet.getInt("count");
			
			dataResultSet.close();
			selectPrepareStmt.close();
			
			if(totalCount > 0)
				return true;
			else
				return false;
		} catch (Exception e){
			throw e;
		}
	}

	public List<FineList> searchFines(String fname,String lname,String cardNo) throws Exception {
		String sqlStr = "";
		List<FineList> fineList=new ArrayList<FineList>();
		connectionObj = new MySQLConnectionDAO();
		try {
			Connection connection = connectionObj.getSQLConnection();
			sqlStr = "SELECT * FROM FINES f,BORROWER b,BOOK_LOANS bl WHERE f.Loan_id=bl.Loan_id and b.Card_no=bl.Card_no and f.paid=false";

			if(fname!=null && fname.length()>0)
				sqlStr+="  and b.Fname like '%"+fname+"%'";
			if(lname!=null && lname.length()>0)
				sqlStr+="  and b.Lname like '%"+lname+"%'";
			if(cardNo!=null && cardNo.length()>0)
				sqlStr+="  and b.Card_no = '"+ cardNo + "'";

			PreparedStatement selectPrepareStmt = connection.prepareStatement(sqlStr);
			ResultSet dataResultSet = selectPrepareStmt.executeQuery();
			while (dataResultSet.next()) {
				FineList fine = new FineList();
				fine.setLoanId(dataResultSet.getString("Loan_id"));
				fine.setCardNo(dataResultSet.getString("Card_no"));
				fine.setFname(dataResultSet.getString("Fname"));
				fine.setLname(dataResultSet.getString("Lname"));
				fine.setBookId(dataResultSet.getString("Isbn"));
				fine.setBranchId(dataResultSet.getString("Branch_id"));
				double totalFine=dataResultSet.getDouble("fine_amt");
				double paid=dataResultSet.getDouble("fine_amt_paid");
				fine.setFineAmountToBePaid(totalFine-paid);
				fineList.add(fine);
			}
			
			dataResultSet.close();
			selectPrepareStmt.close();
			connection.close();
		} catch (Exception e) {
			throw e;
		}
		
		return fineList;
	}

}
