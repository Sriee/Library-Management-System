package utdallas.librarymanagement.service;

import utdallas.librarymanagement.dao.AddBorrowerDAO;

public class AddBorrowerService {
	
	public String addBorrower(int Ssn,String fname, String lname, String address,String city, String stateCode, String telephoneNumber) throws Exception
	{
		String cardNo = null;
		try
		{
		String addressFull = address + " " + city + " " + stateCode;
		AddBorrowerDAO addBorrowerDAO = new AddBorrowerDAO();
		cardNo = addBorrowerDAO.addBorrower(Ssn,fname, lname, addressFull, telephoneNumber);
		}
		catch(Exception e)
		{
			throw e;
		}
		return cardNo;
	}

}
