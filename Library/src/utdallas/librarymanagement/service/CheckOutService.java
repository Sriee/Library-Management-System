package utdallas.librarymanagement.service;

import utdallas.librarymanagement.dao.CheckOutDAO;

public class CheckOutService {

	public Integer checkOut(String bookID, Integer branchID, String cardNO) throws Exception
	{
		Integer loanId = null; 
		try
		{
			CheckOutDAO checkOutDAO = new CheckOutDAO();
			loanId = checkOutDAO.checkOutBook(bookID, branchID, cardNO);
		}catch(Exception e)
		{
			throw e;
		}
		return loanId;
	}
	
}
