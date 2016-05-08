package utdallas.librarymanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import utdallas.librarymanagement.bean.BookList;


public class SearchBookDAO {

	private MySQLConnectionDAO connectionObj = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;

	public List<BookList> searchBook(String bookID, String authorName, String bookTitle) throws Exception {
		connectionObj = new MySQLConnectionDAO();

		Connection connection = connectionObj.getSQLConnection();

		/* Query to be executed 
		 * (
		 * SELECT B.Isbn, B.Title,GROUP_CONCAT(DISTINCT(AU.Fullname) SEPARATOR ',') AS Author_Name, LB.Branch_id, LB.Branch_name, BC.No_of_copies
		 * FROM BOOK AS B INNER JOIN BOOK_AUTHORS AS BA 
		 *	ON B.Isbn = BA.Isbn
		 * INNER JOIN AUTHORS AS AU 
		 *	ON BA.Author_id = AU.Author_id 
		 * INNER JOIN BOOK_COPIES AS BC 
		 *	ON B.Isbn = BC.Book_id 
		 * INNER JOIN LIBRARY_BRANCH AS LB
		 *	ON BC.Branch_id = LB.Branch_id
		 * WHERE B.Title LIKE "%The Price Of%"
		 * GROUP BY LB.Branch_name, B.Isbn
		 * )
		 * UNION
		 * (
		 * SELECT B.Isbn, B.Title, B.Publisher, LB.Branch_id, LB.Branch_name, BC.No_of_copies
		 * FROM BOOK AS B INNER JOIN BOOK_COPIES AS BC 
		 *	ON B.Isbn = BC.Book_id 
		 * INNER JOIN LIBRARY_BRANCH AS LB
		 *	ON BC.Branch_id = LB.Branch_id
		 * WHERE B.Title LIKE "%The Price Of%" AND Type = 1  
		 * GROUP BY LB.Branch_name, B.Isbn
		 * );
		 * */
		List<BookList> bookList = new ArrayList<BookList>();
		try {
			String constructor = " ";
			String combinedQuery = " ";
			String query = "SELECT B.Isbn, B.Title, GROUP_CONCAT(DISTINCT(AU.Fullname) SEPARATOR ',') AS Author_Name, LB.Branch_id, LB.Branch_name, BC.No_of_copies "
					+ "FROM BOOK AS B INNER JOIN BOOK_AUTHORS AS BA ON B.Isbn = BA.Isbn "
					+ "INNER JOIN AUTHORS AS AU ON BA.Author_id = AU.Author_id "
					+ "INNER JOIN BOOK_COPIES AS BC ON B.Isbn = BC.Book_id "
					+ "INNER JOIN LIBRARY_BRANCH AS LB ON BC.Branch_id = LB.Branch_id "
					+ "WHERE ";			

			String query2 = "( SELECT B.Isbn, B.Title, B.Publisher, LB.Branch_id, LB.Branch_name, BC.No_of_copies "
					+ "FROM BOOK AS B INNER JOIN BOOK_COPIES AS BC ON B.Isbn = BC.Book_id "
					+ "INNER JOIN LIBRARY_BRANCH AS LB ON BC.Branch_id = LB.Branch_id "
					+ "WHERE Type = 1 AND ";
			
			if(!bookID.isEmpty()&&!authorName.isEmpty()&&!bookTitle.isEmpty()) {
				constructor = "abc"; 
				query += "B.Isbn = ? AND AU.Fullname LIKE ? AND B.Title LIKE ?";
			} else if(!bookID.isEmpty()&&!authorName.isEmpty()){
				constructor = "ab";
				query += "B.Isbn = ? AND AU.Fullname LIKE ?";
			} else if(!authorName.isEmpty()&&!bookTitle.isEmpty()){
				constructor= "bc";
				query += "AU.Fullname LIKE ? and B.Title LIKE ?";
			} else if(!bookID.isEmpty()&&!bookTitle.isEmpty()) {
				constructor= "ac";
				query += "B.Isbn = ? AND B.Title LIKE ?";
				query2 += "B.Isbn = ? AND B.Title LIKE ?";
			} else if(!bookID.isEmpty()) {
				constructor= "a";
				query += "B.Isbn = ?";
				query2 += "B.Isbn = ?";
			} else if(!authorName.isEmpty()) {
				constructor= "b";
				query += "AU.Fullname LIKE ?";
			} else if(!bookTitle.isEmpty()) {
				constructor= "c";
				query += "B.Title LIKE ?";
				query2 += "B.Title LIKE ?";
			}

			if(constructor.equals("abc") || constructor.equals("ab") || constructor.equals("bc") || constructor.equals("b")) {
				combinedQuery = query + " GROUP BY LB.Branch_name, B.Isbn";
			} else if(constructor.equals("ac") || constructor.equals("a") || constructor.equals("c")) {
				query += " GROUP BY LB.Branch_name, B.Isbn UNION ";
				query2 += " GROUP BY LB.Branch_name, B.Isbn)";
				combinedQuery = query + query2;
			} 
		
			stmt = connection.prepareStatement(combinedQuery);

			if(constructor.equals("abc")) {
				stmt.setString(1, bookID);
				stmt.setString(2, "%" + authorName + "%");
				stmt.setString(3, "%" + bookTitle + "%");
			} else if(constructor.equals("ab")) {
				stmt.setString(1, bookID);
				stmt.setString(2, "%" + authorName + "%");
			} else if(constructor.equals("bc")) {
				stmt.setString(1, "%" + authorName + "%");
				stmt.setString(2, "%" + bookTitle + "%");
			} else if(constructor.equals("ac")) {
				stmt.setString(1, bookID);
				stmt.setString(2, bookID);
			} else if(constructor.equals("a")) {
				stmt.setString(1, bookID);
				stmt.setString(2, bookID);
			} else if(constructor.equals("b")) {
				stmt.setString(1, "%" + authorName + "%");
			} else if(constructor.equals("c")) {
				stmt.setString(1, "%" + bookTitle + "%");
				stmt.setString(2, "%" + bookTitle + "%");
			}

			rs = stmt.executeQuery();

			while (rs.next()) {

				String sqlBookID = rs.getString("Isbn");
				String sqlBookTitle = rs.getString("Title");
				String sqlBranchID = rs.getString("Branch_id");
				String sqlBranchName = rs.getString("Branch_name");
				String sqlAuthorName = rs.getString("Author_Name");
				Integer sqlNoOfCopies = rs.getInt("No_of_copies");
				Integer numberOfCopiesAvailable= sqlNoOfCopies;
				String availableCopiesQuery = "SELECT COUNT(*) FROM BOOK_LOANS WHERE Isbn = '"+ sqlBookID + "' and Branch_id = "+ Integer.parseInt(sqlBranchID) + " AND Date_in IS NULL";
				Statement statement = connection.createStatement();
				ResultSet result = statement.executeQuery(availableCopiesQuery);

				while(result.next()) {
					Integer noOfCopiesLoaned = result.getInt(1);
					numberOfCopiesAvailable = sqlNoOfCopies-noOfCopiesLoaned;
				}

				result.close();
				BookList book = new BookList(sqlBookTitle, sqlBookID,sqlAuthorName, numberOfCopiesAvailable, sqlNoOfCopies, sqlBranchID, sqlBranchName);
				bookList.add(book);
				System.out.println();
			} 

			if(bookList.isEmpty()) 
				throw new Exception("Sorry!!! No Records Found...");

			connection.close();
			rs.close();
		} catch(Exception e) {
			throw e;
		}

		return bookList;
	}
}
