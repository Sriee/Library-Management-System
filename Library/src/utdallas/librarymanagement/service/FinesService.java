package utdallas.librarymanagement.service;

import java.util.List;

import utdallas.librarymanagement.bean.FineList;
import utdallas.librarymanagement.dao.FinesDAO;

public class FinesService {
	
	public void refreshFines() throws Exception
	{
		try
		{
		FinesDAO fines = new FinesDAO();
		fines.refreshFines();
		}catch(Exception e)
		{
			throw e;
		}
	}
	
	public List<FineList> searchFines(String fname, String lname, String cardNo) throws Exception
	{
		try
		{
			FinesDAO fines =new FinesDAO();
			List<FineList> fineList = fines.searchFines(fname, lname, cardNo);
			return fineList;
			}catch(Exception e)
		{
				
			throw e;
		}
	}
	public void payFine(int loanID, double amount) throws Exception
	{
		try
		{
		FinesDAO fines =new FinesDAO();
		fines.payFine(loanID, amount);
		}catch(Exception e)
		{
			throw e;
		}
	}

}
