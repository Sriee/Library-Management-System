package utdallas.librarymanagement.managedBean;

import utdallas.librarymanagement.service.AddBorrowerService;

public class AddBorrowerMB {

	private int Ssn;
	private String fname;
	private String lname;
	private String address;
	private String city;
	private String state;
	private String phoneNumber;
	private String message;
	
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
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String addBorrower()
	{
		try
		{
			if((Ssn == 0) || (fname==null || fname=="") && (lname == null || lname=="") && (address == null || address=="") && (city == null || city=="") && (state == null || state=="") && (phoneNumber == null || phoneNumber==""))
				throw new Exception("All fields are necessary....");
			
		AddBorrowerService addBorrowerService = new AddBorrowerService();
		String cardNO = addBorrowerService.addBorrower(Ssn,fname, lname, address, city, state, phoneNumber);
		this.setMessage("Borrower added successfuly!!! Your CardNo: "+ cardNO);
		}catch(Exception e)
		{
			this.setMessage(e.getMessage());
			return "failure";
		}
		return "success";
	}
	public String goBack()
	{
		return "startPage";
	}
	public int getSsn() {
		return Ssn;
	}
	public void setSsn(int ssn) {
		Ssn = ssn;
	}
	
}
