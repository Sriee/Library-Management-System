package utdallas.librarymanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class AddBorrowerDAO {
	
	private MySQLConnectionDAO connectionObj = null;

	public String addBorrower(int Ssn, String fname, String lname,String address, String telephoneNumber) throws Exception {
		connectionObj = new MySQLConnectionDAO();
		try {
			Connection connection = connectionObj.getSQLConnection();
			int isSsnPresent = 0;
			String cardNo = "";
			Integer cardNoCount = null;
			String query = "SELECT COUNT(*) FROM BORROWER WHERE Ssn = ?";
			PreparedStatement statement= connection.prepareStatement(query);
			statement.setInt(1, Ssn);
			
			ResultSet rs = statement.executeQuery();
			while(rs.next())
				isSsnPresent = rs. getInt(1);
			
			rs.close();
			
			if(isSsnPresent != 0)
				throw new Exception("Borrower exists in the system!!!");
			
			query = "SELECT COUNT(*) FROM BORROWER";
			Statement stmt= connection.createStatement();
			rs = stmt.executeQuery(query);
			while(rs.next())
				cardNoCount = rs.getInt(1); 
				
			cardNoCount++;
			
			if(cardNoCount <= 9999)
				cardNo = "ID00" + cardNoCount;
			else if ((cardNoCount >= 10000) && (cardNoCount <= 99999))
				cardNo = "ID0" + cardNoCount;
			else if ((cardNoCount >= 100000) && (cardNoCount <= 999999))
				cardNo = "ID" + cardNoCount;
			else
				throw new Exception("Maximum capacity of Borrower reached!!! Please delete few records to proceed further !!");
			
			rs.close();
			query = "INSERT INTO BORROWER VALUES(?,?,?,?,?,?)";
			statement = connection.prepareStatement(query);
			statement.setString(1, cardNo);
			statement.setInt(2, Ssn);
			statement.setString(3, fname);
			statement.setString(4, lname);
			statement.setString(5, address);
			statement.setString(6, telephoneNumber);
			statement.executeUpdate();
			
			rs.close();
			connection.close();
			return cardNo;
					
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
}
