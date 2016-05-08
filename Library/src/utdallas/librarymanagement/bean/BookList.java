package utdallas.librarymanagement.bean;

public class BookList {

	private String bookName;
	private String bookId;
	private String authorName;
	private String branchID;
	private String branchName;
	private int noOfCopiesAvailable;
	private int noOfCopiesInventored;

	
	/**
	 * @return branchID
	 */
	public String getBranchID() {
		return branchID;
	}

	/**
	 * @param branchID  sets branchID 
	 */
	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}

	public BookList(String bookTitle, String bookID, String bookAuthor, int noOfAvailablecopies, int noOfCopiesInventored, String branchID,String branchName) {
		this.bookName = bookTitle;
		this.bookId = bookID;
		this.branchID = branchID;
		this.authorName = bookAuthor;
		this.branchName = branchName;
		this.noOfCopiesAvailable = noOfAvailablecopies;
		this.noOfCopiesInventored= noOfCopiesInventored;
	}

	/**
	 * @return bookName
	 */
	public String getBookName() {
		return bookName;
	}

	/**
	 * @param bookName sets bookName
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
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
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @param authorName sets authorName
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	/**
	 * @return brnchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param authorName sets authorName 
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	/**
	 * @return noOfCopiesAvailable
	 */
	public int getNoOfCopiesAvailable() {
		return noOfCopiesAvailable;
	}

	/**
	 * @param noOfCopiesAvailable sets noOfCopiesAvailable
	 */
	public void setNoOfCopiesAvailable(int noOfCopiesAvailable) {
		this.noOfCopiesAvailable = noOfCopiesAvailable;
	}

	/**
	 * @return noOfCopiesInventored
	 */
	public int getNoOfCopiesInventored() {
		return noOfCopiesInventored;
	}

	/**
	 * @param noOfCopiesInventored sets noOfCopiesInventored 
	 */
	public void setNoOfCopiesInventored(int noOfCopiesInventored) {
		this.noOfCopiesInventored = noOfCopiesInventored;
	}

}
