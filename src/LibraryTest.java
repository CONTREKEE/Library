import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    Library test;

    String date = "2022-01-01";

    LocalDate testDate = LocalDate.parse(date);

    Book testBook = new Book("12345","Test Book",
            "Test Subject",123,"Test Author", testDate);

    Reader testReader = new Reader(1, "Test Reader", "999-999-9999");

    Shelf testShelf = new Shelf(2, "Test Subject 2");


    @BeforeEach
    void setUp() {
        System.out.println("Running before");
        test = new Library("Test");
    }

    @AfterEach
    void tearDown() {
        System.out.println("After each");
        test = null;
    }

    @Test
    void init() {
        assertNotEquals(2, test.listBooks());
        assertNotEquals(1, test.listReaders());
        test.init("test.csv");
        assertEquals(2, test.listBooks());
        assertEquals(1, test.listReaders());

    }

    @Test
    void addBook() {
        assertNotEquals(1, test.listBooks());
        test.addBook(testBook);
        assertEquals(1, test.listBooks());
    }

    @Test
    void getBookByISBN() {
        test.addBook(testBook);
        assertNotNull(test.getBookByISBN("12345"));
        assertNull(test.getBookByISBN("123456"));

    }

    @Test
    void checkOutBook() {
        test.addBook(testBook);
        test.addReader(testReader);
        assertNotEquals(Code.SUCCESS, test.checkOutBook(testReader, testBook));
        test.addShelf("Test Subject");
        assertEquals(Code.SUCCESS, test.checkOutBook(testReader, testBook));
    }

    @Test
    void returnBook() {

        test.addBook(testBook);
        test.addReader(testReader);

        assertNotEquals(Code.SUCCESS, test.returnBook(testReader, testBook));

        test.addShelf("Test Subject");
        test.checkOutBook(testReader, testBook);

        assertEquals(Code.SUCCESS, test.returnBook(testReader, testBook));
    }

    @Test
    void addShelf() {
        assertNull(test.getShelf("New Test Subject"));
        test.addShelf("New Test Subject");
        assertNotNull(test.getShelf("New Test Subject"));
    }

    @Test
    void testAddShelf() {
        assertNull(test.getShelf("Test Subject 2"));
        test.addShelf(testShelf);
        assertNotNull(test.getShelf("Test Subject 2"));
    }

    @Test
    void addReader() {
        assertNull(test.getReaderByCard(1));
        test.addReader(testReader);
        assertNotNull(test.getReaderByCard(1));
    }

    @Test
    void removeReader() {
        assertEquals(Code.READER_NOT_IN_LIBRARY_ERROR, test.removeReader(testReader));
        test.addShelf("Test Subject");
        test.addBook(testBook);
        assertEquals(Code.READER_NOT_IN_LIBRARY_ERROR, test.removeReader(testReader));
        test.addReader(testReader);
        test.checkOutBook(testReader, testBook);
        assertEquals(Code.READER_STILL_HAS_BOOKS_ERROR, test.removeReader(testReader));
        test.returnBook(testReader, testBook);
        assertEquals(Code.SUCCESS, test.removeReader(testReader));
    }

    @Test
    void getReaderByCard() {
        assertNull(test.getReaderByCard(1));
        test.addReader(testReader);
        assertNotNull(test.getReaderByCard(1));
    }

    @Test
    void listBooks() {
        assertNotEquals(1, test.listBooks());
        test.addBook(testBook);
        assertEquals(1, test.listBooks());
    }

    @Test
    void listReaders() {
        assertNotEquals(1, test.listReaders());
        test.addReader(testReader);
        assertEquals(1, test.listReaders());
    }

    @Test
    void getShelf() {
        assertNull(test.getShelf(1));
        test.addShelf(testShelf);
        assertNotNull(test.getShelf(1));
    }

    @Test
    void testGetShelf() {
        assertNull(test.getShelf("Test Subject 2"));
        test.addShelf("Test Subject 2");
        assertNotNull(test.getShelf("Test Subject 2"));
    }

    @Test
    void listShelves() {
        assertEquals(Code.SUCCESS, test.listShelves(true));
        test.addBook(testBook);
        test.addShelf("Test Subject");
        assertEquals(Code.SUCCESS, test.listShelves(true));
        assertEquals(Code.SUCCESS, test.listShelves(false));
    }

    @Test
    void convertInt() {
        assertNotEquals(1, Library.convertInt("test", Code.BOOK_COUNT_ERROR));
        assertEquals(-2, Library.convertInt("test", Code.BOOK_COUNT_ERROR));
        assertEquals(1, Library.convertInt("1", Code.BOOK_COUNT_ERROR));
    }

    @Test
    void getLibraryCarNumber() {
        assertNotEquals(2, test.getLibraryCardNumber());
        test.addReader(testReader);
        assertEquals(2, test.getLibraryCardNumber());
    }

    @Test
    void convertDate() {
        assertNotEquals(LocalDate.parse(date), test.convertDate("2022-02-01", Code.DATE_CONVERSION_ERROR));
        assertEquals(LocalDate.parse(date), test.convertDate("2022-01-01", Code.DATE_CONVERSION_ERROR));
        assertEquals(LocalDate.EPOCH, test.convertDate("2022", Code.DATE_CONVERSION_ERROR));
    }

}