
package junit.org.rapidpm.lang.cache.generic.book;

import junit.framework.TestCase;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BookCacheWrapperTest extends TestCase {
    private List<Book> bookList;
    private BookCacheWrapper bookCache = new BookCacheWrapper();

    @Override
    protected void setUp() throws Exception {
        bookList = createBookList();
        bookCache.put2Cache(bookList);
    }

    public void testFillCache() {
        assertEquals(10, bookCache.getAllBooks().size());
    }

    public void testGetBook4KeyWords() {
        final List<String> keyWordList = new ArrayList<String>();
        keyWordList.add("q");
        keyWordList.add("g");
        final Collection<Book> books4KeyWords = bookCache.getBooks4KeyWords(keyWordList);
        assertTrue(books4KeyWords.contains(bookList.get(2)));
        assertTrue(books4KeyWords.contains(bookList.get(3)));
        assertTrue(books4KeyWords.contains(bookList.get(7)));
        assertTrue(books4KeyWords.contains(bookList.get(8)));
        assertTrue(books4KeyWords.contains(bookList.get(9)));
        assertEquals(5, books4KeyWords.size());
    }

    public void testGetBook4Month() {
        final Collection<Book> books4Month = bookCache.getBooks4Month("200503");
        assertTrue(books4Month.contains(bookList.get(8)));
        assertTrue(books4Month.contains(bookList.get(9)));
        assertEquals(2, books4Month.size());
    }

    public void testGetBook4Cw() {
        final Collection<Book> books4Month = bookCache.getBooks4Cw("2005KW5");
        assertTrue(books4Month.contains(bookList.get(4)));
        assertEquals(1, books4Month.size());
    }

    public void testGetBook4Date() {
        final Collection<Book> books4Date = bookCache.getBooks4Date("20050215");
        assertTrue(books4Date.contains(bookList.get(6)));
        assertEquals(1, books4Date.size());
    }

    public void testRemoveFromCache() throws IllegalAccessException, InvocationTargetException {
        final List<Book> bookListClone = new ArrayList<Book>();
        bookListClone.addAll(bookList);

        final List<String> keyWordList = new ArrayList<String>();
        keyWordList.add("q");
        keyWordList.add("g");
        Collection<Book> books4KeyWords = bookCache.getBooks4KeyWords(keyWordList);

        bookCache.removeBook(books4KeyWords);

        final Collection<Book> allBooks = bookCache.getAllBooks();
        assertEquals(5, allBooks.size());
        books4KeyWords = bookCache.getBooks4KeyWords(keyWordList);
        assertEquals(0, books4KeyWords.size());

        assertTrue(allBooks.contains(bookListClone.get(0)));
        assertTrue(allBooks.contains(bookListClone.get(1)));
        assertTrue(allBooks.contains(bookListClone.get(4)));
        assertTrue(allBooks.contains(bookListClone.get(5)));
        assertTrue(allBooks.contains(bookListClone.get(6)));
    }


    private List<Book> createBookList() {
        final List<Book> bookList = new ArrayList<Book>();
        // book 0
        Book book = new Book("a", "a", "a", "2005KW1", "20050101", "200501", createKeyWordList("a", "b", "c"));
        bookList.add(book);
        // book 1
        book = new Book("b", "b", "b", "2005KW1", "20050106", "200501", createKeyWordList("c", "d", "e"));
        bookList.add(book);
        // book 2
        book = new Book("c", "c", "c", "2005KW3", "20050115", "200501", createKeyWordList("e", "f", "g"));
        bookList.add(book);
        // book 3
        book = new Book("d", "d", "d", "2005KW4", "20050122", "200501", createKeyWordList("g", "h", "i"));
        bookList.add(book);
        // book 4
        book = new Book("e", "e", "e", "2005KW5", "20050201", "200502", createKeyWordList("i", "j", "k"));
        bookList.add(book);
        // book 5
        book = new Book("f", "f", "f", "2005KW6", "20050208", "200502", createKeyWordList("k", "l", "m"));
        bookList.add(book);
        // book 6
        book = new Book("g", "g", "g", "2005KW7", "20050215", "200502", createKeyWordList("m", "n", "o"));
        bookList.add(book);
        // book 7
        book = new Book("h", "h", "h", "2005KW8", "20050222", "200502", createKeyWordList("o", "p", "q"));
        bookList.add(book);
        // book 8
        book = new Book("i", "i", "i", "2005KW9", "20050301", "200503", createKeyWordList("q", "r", "s"));
        bookList.add(book);
        // book 9
        book = new Book("k", "k", "k", "2005KW10", "20050308", "200503", createKeyWordList("q", "r", "s"));
        bookList.add(book);
        return bookList;
    }

    private List<String> createKeyWordList(final String s, final String s1, final String s2) {
        final List<String> keyWordList = new ArrayList<String>();
        keyWordList.add(s);
        keyWordList.add(s1);
        keyWordList.add(s2);
        return keyWordList;
    }
}
