import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Name: Keegan Farris
 * Date: 2-4-2022
 * Explanation: Sets up test methods using JUnit.
 */

class ReaderTest {

    Reader readerTest;

    String toString = "First Name(#1234) has checked out {Headfirst Java, New Book}";

    //Book information to test against
    String isbn = "1337";
    String title = "Headfirst Java";
    String subject = "education";
    int pageCount = 1337;
    String author = "Grady Booch";
    LocalDate dueDate = LocalDate.of(2000, 2, 12);

    //Creating two books to use in test cases
    Book book = new Book(isbn, title, subject, pageCount, author, dueDate);
    Book book2 = new Book("9999", "New Book", "Math", 1920, "Me", dueDate);


    List<Book> books = new ArrayList<>();

    //Reader information
    int cardNumber = 1234;
    String name = "First Name";
    String phone = "209-444-4444";

    //Executes before each test
    @BeforeEach
    void setUp() {
        System.out.println("Running before");
        readerTest = new Reader(cardNumber, name, phone);
    }

    //Executes after each test
    @AfterEach
    void tearDown() {
        System.out.println("After each");
        readerTest = null;
    }

    //Test for constructor
    @Test
    void constructorTest(){
        Reader readerTest = null;
        assertNull(readerTest);
        readerTest = new Reader(cardNumber, name, phone);
        assertNotNull(readerTest);
    }

    //Test for adding a book to a reader
    @Test
    void addBook_test() {
        assertEquals(readerTest.addBook(book), Code.SUCCESS);
        assertEquals(readerTest.addBook(book), Code.BOOK_ALREADY_CHECKED_OUT_ERROR);
    }

    //Test for removing a book from a reader
    @Test
    void removeBook_test() {
        assertEquals(readerTest.removeBook(book), Code.READER_DOESNT_HAVE_BOOK_ERROR);
        readerTest.addBook(book);
        assertEquals(readerTest.removeBook(book), Code.SUCCESS);
    }

    //Checks if user has a book
    @Test
    void hasBook_test() {
        assertEquals(false, readerTest.hasBook(book));
        readerTest.addBook(book);
        assertEquals(true, readerTest.hasBook(book));
    }

    //Tests amount books a user has
    @Test
    void getBookCount() {
        assertEquals(0, readerTest.getBooks().size());
        readerTest.addBook(book);
        assertEquals(1, readerTest.getBooks().size());
        readerTest.removeBook(book);
        assertEquals(0, readerTest.getBooks().size());
    }

    //Tests to see if retrieving books works
    @Test
    void getBooks() {
        assertEquals(readerTest.getBooks(), books);
    }

    //Tests the updating of the books list
    @Test
    void setBooks() {
        assertEquals(readerTest.getBooks(), books);
        readerTest.addBook(book);
        assertNotEquals(readerTest.getBooks(), books);
    }

    //Tests if retrieving card number works
    @Test
    void getCardNumber() {
        assertEquals(readerTest.getCardNumber(), cardNumber);
    }

    //Tests if setting card number works
    @Test
    void setCardNumber() {
        assertEquals(readerTest.getCardNumber(), cardNumber);
        readerTest.setCardNumber(9999);
        assertNotEquals(readerTest.getCardNumber(), cardNumber);
    }

    //Tests if retrieving reader name works
    @Test
    void getName() {
        assertEquals(readerTest.getName(), name);
    }

    //Tests if setting name works
    @Test
    void setName() {
        assertEquals(readerTest.getName(), name);
        readerTest.setName("New Name");
        assertNotEquals(readerTest.getName(), name);
    }

    //Tests if retrieving phone works
    @Test
    void getPhone() {
        assertEquals(readerTest.getPhone(), phone);
    }

    //Tests if setting phone works
    @Test
    void setPhone() {
        assertEquals(readerTest.getPhone(), phone);
        readerTest.setPhone("999-000-0000");
        assertNotEquals(readerTest.getPhone(), phone);
    }

    //Tests if comparing readers works
    @Test
    void testEquals() {
        Reader equalTest1 = new Reader(9999, "Name", "Phone");
        Reader equalTest2 = new Reader(0000, "NotName", "NotPhone");
        assertEquals(false, equalTest1.equals(equalTest2));
        equalTest2 = new Reader(9999, "Name", "Phone");
        assertEquals(true, equalTest1.equals(equalTest2));
    }

    //Tests if comparing hash codes works
    @Test
    void testHashCode() {
        Reader equalTest1 = new Reader(9999, "Name", "Phone");
        Reader equalTest2 = new Reader(0000, "NotName", "NotPhone");
        assertNotEquals(equalTest1.hashCode(), equalTest2.hashCode());
        equalTest2 = new Reader(9999, "Name", "Phone");
        assertEquals(equalTest1.hashCode(), equalTest2.hashCode());
    }

    //Tests if toString prints correct information
    @Test
    void toString_test() {
        readerTest.addBook(book);
        readerTest.addBook(book2);
        assertEquals(readerTest.toString(), toString);
        readerTest.removeBook(book);
        assertNotEquals(readerTest.toString(), toString);
    }
}