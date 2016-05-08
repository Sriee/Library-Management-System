package utdallas.librarymanagement.service;

import java.util.List;

import utdallas.librarymanagement.bean.BookList;
import utdallas.librarymanagement.dao.SearchBookDAO;

public class SearchBookService {
	
public List<BookList> bookSearch(String bookTitle, String authorName,String bookId) throws Exception
{
	List<BookList> bookList= null;
	try
	{
		bookList = new SearchBookDAO().searchBook(bookId, authorName, bookTitle);
	}catch(Exception e)
	{
		throw e;
	}
	return bookList;
}

}
