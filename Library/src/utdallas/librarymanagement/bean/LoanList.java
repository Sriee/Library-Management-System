package utdallas.librarymanagement.bean;

public class LoanList {
	
	private String cardNo;
	private Integer loanId;
	private String bookId;
	private Integer branchId;
	/**
	 * @return cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}
	/**
	 * @param cardNo sets cardNo
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	/**
	 * @return loanId
	 */
	public Integer getLoanId() {
		return loanId;
	}
	/**
	 * @param loanId sets loanId
	 */
	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
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
	 * @return branchId
	 */
	public Integer getBranchId() {
		return branchId;
	}
	/**
	 * @param branchId sets branchId
	 */
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
}
