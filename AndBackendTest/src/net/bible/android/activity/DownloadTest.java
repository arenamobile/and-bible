package net.bible.android.activity;

import java.util.List;

import junit.framework.TestCase;
import net.bible.service.sword.SwordApi;

import org.crosswire.jsword.book.Book;
import org.crosswire.jsword.book.BookFilters;
import org.crosswire.jsword.book.Defaults;
import org.crosswire.jsword.bridge.BookInstaller;

public class DownloadTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetRepositoryBooks() {
    	List<Book> bibles = new BookInstaller().getRepositoryBooks("CrossWire", BookFilters.getOnlyBibles());
    	assertTrue("Could not get bible list", bibles.size()>0);
		System.out.println("Count:"+bibles.size());
	}

	public void testGetRepositoryDictionaries() {
    	List<Book> books = new BookInstaller().getRepositoryBooks("CrossWire", BookFilters.getDictionaries());
    	assertTrue("Could not get dictionary list", books.size()>0);
		System.out.println("Count:"+books.size());
		for (Book book : books) {
			System.out.println(book.getLanguage()+" init:"+book.getInitials()+" "+book.getBookMetaData().getProperty("SourceType")+book.getBookMetaData().getProperty("ModDrv")+" "+book.getDriverName()+" "+book.getBookMetaData().getKeyType()+" "+book.getName());
		}
	}

	public void testDownloadIndex() {
		try {
			Book book = SwordApi.getInstance().getDocumentByInitials("ESV");
			
			SwordApi.getInstance().downloadDocument(book);
			Thread.sleep(30000);
			System.out.println("fin");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Strongs problem");
		}
	}

	public void testDownloadJubilee2000() throws Exception {
		SwordApi.getInstance().downloadDocument(getBook("Jubilee2000"));
		System.out.println("finished");
	}


	public void testGetGreekDictionary() {
		try {
			Book sgBook = getStrongsGreek();
			SwordApi.getInstance().downloadDocument(sgBook);
			Thread.sleep(30000);
			System.out.println("fin");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Strongs problem");
		}
	}
	
	private Book getStrongsGreek() {
    	List<Book> books = new BookInstaller().getRepositoryBooks("CrossWire", BookFilters.getCustom("Feature=GreekDef"));
    	assertTrue("Could not get dictionary list", books.size()>0);
		System.out.println("Count:"+books.size());
		for (Book book : books) {
			if (book.getInitials().indexOf("Strong")!=-1) {
				System.out.println(book.getLanguage()+" init:"+book.getInitials()+" "+book.getBookMetaData().getProperty("SourceType")+book.getBookMetaData().getProperty("ModDrv")+" "+book.getDriverName()+" "+book.getBookMetaData().getKeyType()+" "+book.getName());
				return book;
			}
		}
		return null;
	}
	
	private Book getBook(String initials) {
    	List<Book> books = new BookInstaller().getRepositoryBooks("CrossWire", BookFilters.getAll());
    	for (Book book : books) {
    		System.out.println(book.getInitials());
    		if (initials.equals(book.getInitials())) {
    			return book;
    		}
    	}
    	assertTrue("Could not get "+initials, false);
    	return null;
	}

	private void usefulStuff() {
		Book bookg = Defaults.getGreekDefinitions();
		Book bookh = Defaults.getHebrewDefinitions();
	}

}