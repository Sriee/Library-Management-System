package utdallas.librarymanagement.managedBean;

import java.util.List;

import utdallas.librarymanagement.bean.BookList;
import utdallas.librarymanagement.service.SearchBookService;

public class StartPageMB {
	
	private String bookTitle;
	private String bookAuthor;
	private String bookId;
	List<BookList> bookList = null;
	public String message;
	
	public StartPageMB(){
		this.bookAuthor=null;
		this.bookId=null;
		this.bookTitle=null;
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
	 * @return the bookTitle
	 */
	public String getBookTitle() {
		return bookTitle;
	}
	/**
	 * @param bookTitle the bookTitle to set
	 */
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	/**
	 * @return the bookAuthor
	 */
	public String getBookAuthor() {
		return bookAuthor;
	}
	/**
	 * @param bookAuthor the bookAuthor to set
	 */
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	/**
	 * @return the bookId
	 */
	public String getBookId() {
		return bookId;
	}
	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	/**
	 * @return the bookList
	 */
	public List<BookList> getBookList() {
		return bookList;
	}
	/**
	 * @param bookList the bookList to set
	 */
	public void setBookList(List<BookList> bookList) {
		this.bookList = bookList;
	}
	
	public String bookSearch()
	{
		try
		{
		this.bookList = null;
		SearchBookService bookSearch = new SearchBookService();
		if(this.bookId.isEmpty()&&this.bookAuthor.isEmpty()&&this.bookTitle.isEmpty())
		{
			throw new Exception("Please enter atleast one value");
		}
		this.bookList = bookSearch.bookSearch(this.bookTitle, this.bookAuthor, this.bookId);
		this.setMessage(null);
		return "success";
		}catch(Exception e)
		{
			this.setMessage(e.getMessage());
			return "failure";
		}
		
	}
	public String goBack()
	{
		return "startPage";
	}

}
