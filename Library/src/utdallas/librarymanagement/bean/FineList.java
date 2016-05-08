package utdallas.librarymanagement.bean;

public class FineList {
    
    private String loanId;
    private String cardNo;
    private String fname;
    private String lname;
    private String bookId;
    private String branchId;
    private Double fineAmountToBePaid;
    
	/**
	 * @return fname
	 */
	public String getFname() {
		return fname;
	}
	/**
	 * @param fname sets fname 
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}
	/**
	 * @return lname
	 */
	public String getLname() {
		return lname;
	}
	/**
	 * @param lname sets lname 
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}
	/**
	 * @return bookId
	 */
	public String getBookId() {
		return bookId;
	}
	/**
	 * @param bookId sets bookId 
	 */
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	/**
	 * @return loanId
	 */
	public String getLoanId() {
		return loanId;
	}
	/**
	 * @param loanId sets loanId
	 */
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	/**
	 * @return cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}
	/**
	 * @param cardNo sets cardNos
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	/**
	 * @return branchId
	 */
	public String getBranchId() {
		return branchId;
	}
	/**
	 * @param branchId sets branchId
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	/**
	 * @return fineAmountToBePaid
	 */
	public Double getFineAmountToBePaid() {
		return fineAmountToBePaid;
	}
	/**
	 * @param fineAmountToBePaid sets fineAmountToBePaid
	 */
	public void setFineAmountToBePaid(Double fineAmountToBePaid) {
		this.fineAmountToBePaid = fineAmountToBePaid;
	}
}
