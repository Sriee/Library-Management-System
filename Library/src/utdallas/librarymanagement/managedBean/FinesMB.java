package utdallas.librarymanagement.managedBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ValueChangeEvent;

import utdallas.librarymanagement.bean.FineList;
import utdallas.librarymanagement.service.FinesService;

public class FinesMB {
	
	private Map<Integer, Boolean> checkStatus = new HashMap<Integer, Boolean>();
	private String cardNo;
	private String fname;
	private String lname;
	private String message;
	private List<FineList> fineList = null;
	private Boolean wishToPay=null;
	private String payment;
	private int loanID; 
	

	/**
	 * @return the payment
	 */
	public String getPayment() {
		return payment;
	}

	/**
	 * @param payment the payment to set
	 */
	public void setPayment(String payment) {
		this.payment = payment;
	}

	/**
	 * @return the loanID
	 */
	public int getLoanID() {
		return loanID;
	}

	/**
	 * @param loanID the loanID to set
	 */
	public void setLoanID(int loanID) {
		this.loanID = loanID;
	}

	/**
	 * @return the wishToPay
	 */
	public Boolean getWishToPay() {
		return wishToPay;
	}

	/**
	 * @param wishToPay the wishToPay to set
	 */
	public void setWishToPay(Boolean wishToPay) {
		this.wishToPay = wishToPay;
	}

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
	 * @return the fineList
	 */
	public List<FineList> getFineList() {
		return fineList;
	}

	/**
	 * @param fineList the fineList to set
	 */
	public void setFineList(List<FineList> fineList) {
		this.fineList = fineList;
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
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * @param fname the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	/**
	 * @return the lname
	 */
	public String getLname() {
		return lname;
	}

	/**
	 * @param lname the lname to set
	 */
	public void setLname(String lname) {
		this.lname = lname;
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

	public String refreshFines()
	{
		try
		{
		this.fineList = null;
		this.setMessage(null);
		FinesService fines = new FinesService();
		fines.refreshFines();
		this.setMessage("Fines refreshed successfully");
		return "success";
		}catch(Exception e)
		{
			this.setMessage(e.getMessage());
			return "failure";
		}
	}
	public String searchFines()
	{
		try
		{
			this.setMessage(null);
			FinesService fines= new FinesService();
			this.fineList = fines.searchFines(fname, lname, cardNo);
			return "success";
		}catch(Exception e)
		{
			this.setMessage(e.getMessage());
			return "failure";
		}
	}
	public String payFine()
	{
		try
		{
		this.fineList = null;
		FinesService fine = new FinesService();
		fine.payFine(this.loanID, Double.parseDouble(this.payment));
		this.setMessage("Payment Successfull");
		return "success";
		}catch(Exception e)
		{
			this.setMessage(e.getMessage());
			return "failure";
		}
		
	}
	public void radioButtonCheck(ValueChangeEvent e){
		
		this.wishToPay = (Boolean)e.getNewValue();
		
	}
	public String goBack()
	{
		return "startPage";
	}

}
