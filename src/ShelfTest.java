import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Keegan Farris
 * Date: 2-9-2022
 * Description: Tests each method for the Shelf class.
 */

class ShelfTest {

    Shelf shelf;

    String isbn = "1337";
    String title = "Headfirst Java";
    String subject = "education";
    int pageCount = 1337;
    String author = "Grady Booch";
    LocalDate dueDate = LocalDate.of(2000, 2, 12);

    //Creating two books to use in test cases
    Book book = new Book(isbn, title, subject, pageCount, author, dueDate);

    int shelfNumber = 0;

    HashMap<Book, Integer> books = new HashMap<>();

    //Executes before each test
    @BeforeEach
    void setUp() {
        System.out.println("Before each.");
        shelf = new Shelf(shelfNumber, book.getSubject());
    }

    //Executes after each test
    @AfterEach
    void tearDown() {
        System.out.println("After each.");
        shelf = null;
    }

    @Test
    void constructorTest() {
        Shelf shelfTest = null;
        assertNull(shelfTest);
        shelfTest = new Shelf(shelfNumber, book.getSubject());
        assertNotNull(shelfTest);
    }

    @Test
    void getShelfNumber() {
        assertEquals(shelf.getShelfNumber(), shelfNumber);
    }

    @Test
    void setShelfNumber() {
        assertEquals(shelf.getShelfNumber(), shelfNumber);
        shelf.setShelfNumber(1);
        assertNotEquals(shelf.getShelfNumber(), shelfNumber);
    }

    @Test
    void getSubject() {
        assertEquals(shelf.getSubject(), subject);
    }

    @Test
    void setSubject() {
        assertEquals(shelf.getSubject(), subject);
        shelf.setSubject("new subject");
        assertNotEquals(shelf.getSubject(), subject);
    }

    @Test
    void getBooks() {
        assertEquals(shelf.getBooks(), books);
    }

    @Test
    void setBooks() {
        assertEquals(shelf.getBooks(), books);
        shelf.addBook(book);
        assertNotEquals(shelf.getBooks(), books);
    }

    @Test
    void testEquals() {
        Shelf equalTest1 = new Shelf(1, "education");
        Shelf equalTest2 = new Shelf(1, "math");
        assertNotEquals(equalTest1, equalTest2);
        equalTest2 = new Shelf(1, "education");
        assertEquals(equalTest1, equalTest2);
    }

    @Test
    void testHashCode() {
        Shelf equalTest1 = new Shelf(1, "education");
        Shelf equalTest2 = new Shelf(1, "math");
        assertNotEquals(equalTest1.hashCode(), equalTest2.hashCode());
        equalTest2 = new Shelf(1, "education");
        assertEquals(equalTest1.hashCode(), equalTest2.hashCode());
    }

    @Test
    void getBookCount() {
        shelf.addBook(book);
        assertEquals(shelf.getBookCount(book), 1);
        shelf.addBook(book);
        assertEquals(shelf.getBookCount(book), 2);
    }

    @Test
    void addBook() {
        assertEquals(shelf.addBook(book), Code.SUCCESS);
        Shelf testShelf = new Shelf(1, "math");
        assertEquals(testShelf.addBook(book), Code.SHELF_SUBJECT_MISMATCH_ERROR);
    }

    @Test
    void removeBook() {
        Book book2 = new Book(isbn, "Test Book", subject, pageCount, author, dueDate);
        shelf.addBook(book);
        assertEquals(shelf.removeBook(book), Code.SUCCESS);
        assertEquals(shelf.removeBook(book), Code.BOOK_NOT_IN_INVENTORY_ERROR);
        assertEquals(shelf.removeBook(book2), Code.BOOK_NOT_IN_INVENTORY_ERROR);
    }

    @Test
    void listBooks() {
        String compare = "1 book on shelf: 0 : education\nHeadfirst Java by Grady Booch ISBN: 1337 1";
        shelf.addBook(book);
        assertEquals(compare, shelf.listBooks());
    }

}