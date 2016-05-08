package utdallas.librarymanagement.managedBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import utdallas.librarymanagement.bean.LoanList;
import utdallas.librarymanagement.service.CheckInService;

public class CheckInMB {
	
	private Map<Integer, Boolean> checkStatus = new HashMap<Integer, Boolean>();
	private String borrowerName;
	private String bookID;
	private String cardNo = null;
	private List<LoanList> loanList = null;
	private String message;
	
	/**
	 * @return the checkStatus
	 */
	public Map<Integer, Boolean> getCheckStatus() {
		return checkStatus;
	}
	/**
	 * @param checkStatus the checkStatus to set
	 */
	public void setCheckStatus(Map<Integer, Boolean> checkStatus) {
		this.checkStatus = checkStatus;
	}
	/**
	 * @return the bookID
	 */
	public String getBookID() {
		return bookID;
	}
	/**
	 * @param bookID the bookID to set
	 */
	public void setBookID(String bookID) {
		this.bookID = bookID;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the borrowerName
	 */
	public String getBorrowerName() {
		return borrowerName;
	}
	/**
	 * @param borrowerName the borrowerName to set
	 */
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}
	
	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}
	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	/**
	 * @return the loanList
	 */
	public List<LoanList> getLoanList() {
		return loanList;
	}
	/**
	 * @param loanList the loanList to set
	 */
	public void setLoanList(List<LoanList> loanList) {
		this.loanList = loanList;
	}
	public String checkInSearch()
	{
		try
		{
			if((this.bookID == null && this.borrowerName == null && this.cardNo == null) || (this.bookID == "" && this.borrowerName == "" && this.cardNo == ""))
				throw new Exception("Enter aleast one value!... All fields can't be left blank!...");
	
			this.setMessage(null);
			CheckInService checkIn = new CheckInService();
			this.loanList = checkIn.checkInSearch(this.bookID, this.borrowerName,this.cardNo);
			this.setMessage(null);
		}catch(NumberFormatException e)
		{
			this.setMessage("Invalid Card Number");
		}
		catch(Exception e)
		{
			this.setMessage(e.getMessage());
			return "failure";
		}
		return "success";
	}
	public String checkInCompletion()
	{
		try
		{
			this.setMessage(null);
		List<Integer> checkedLoans = new ArrayList<Integer>();
		Iterator<Integer> iterator = this.checkStatus.keySet().iterator();
		while(iterator.hasNext())
		{
			Integer mapLoanID =(Integer)iterator.next();
			Boolean b = this.checkStatus.get(mapLoanID);
			if(b)
			{
				checkedLoans.add(mapLoanID);
			}
			
		}
		CheckInService checkIn = new CheckInService();
		this.loanList = null;
		checkIn.checkInCompletion(checkedLoans);
		this.loanList = null;
		this.checkStatus = new HashMap<Integer, Boolean>();
		this.setMessage("Books checked In successfully");
		}catch(Exception e)
		{
			this.setMessage(e.getMessage());
		}
		return "success";
	}
	public String goBack()
	{
		return "startPage";
	}

}
