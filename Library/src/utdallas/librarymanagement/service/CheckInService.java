package utdallas.librarymanagement.service;

import java.util.List;

import utdallas.librarymanagement.bean.LoanList;
import utdallas.librarymanagement.dao.CheckInDAO;
import utdallas.librarymanagement.dao.FinesDAO;

public class CheckInService {
	
	
	public List<LoanList> checkInSearch(String bookId, String borrowerName, String cardNo) throws Exception
	{
		try
		{
		CheckInDAO checkIn = new CheckInDAO();  
		List<LoanList> loanList = checkIn.checkInSearch(bookId, borrowerName, cardNo);
		return loanList;
		}catch(Exception e)
		{
			throw e;
		}
	}
	public void checkInCompletion(List<Integer> loanIDList) throws Exception
	{
		try
		{
		CheckInDAO checkIn = new CheckInDAO();  
		checkIn.checkInCompletion(loanIDList);
		FinesDAO fine = new FinesDAO();
		fine.refreshFines();
		Boolean paidStatus = fine.isLoanIdFinePaid(loanIDList);
		if(!paidStatus)
		{
			throw new Exception("Check in Successfull!!You have outstanding fines for the checked in loan IDs. Please click the link below to pay the fees.");
		}
		}catch(Exception e)
		{
			throw e;
		}
	}
	
}
