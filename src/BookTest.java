/**
 * Name: Keegan Farris
 * Date: 2-1-2022
 * Explanation: Sets of tests for each method in the Book.java class
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    Book bookTest;

    //1337,Headfirst Java,education,1337,Grady Booch,0000
    String isbn = "1337";
    String title = "Headfirst Java";
    String subject = "education";
    int pageCount = 1337;
    String author = "Grady Booch";
    LocalDate dueDate = LocalDate.of(2000, 2, 12);

    @BeforeEach
    void setUp() {
        System.out.println("Running before");
        bookTest = new Book(isbn, title, subject, pageCount, author, dueDate);
    }

    @AfterEach
    void tearDown() {
        System.out.println("After each");
        bookTest = null;
    }

    @Test
    void constructorTest(){
        Book bookTest = null;
        assertNull(bookTest);
        bookTest = new Book(isbn, title, subject, pageCount, author, dueDate);
        assertNotNull(bookTest);
    }

    @Test
    void getIsbn() {
        assertEquals(isbn, bookTest.getIsbn());
    }

    @Test
    void setIsbn() {
        assertEquals(isbn, bookTest.getIsbn());
        bookTest.setIsbn("9999");
        assertNotEquals(isbn, bookTest.getIsbn());
    }

    @Test
    void getTitle() {
        assertEquals(title, bookTest.getTitle());
    }

    @Test
    void setTitle() {
        assertEquals(title, bookTest.getTitle());
        bookTest.setTitle("New Title");
        assertNotEquals(title, bookTest.getTitle());
    }

    @Test
    void getSubject() {
        assertEquals(subject, bookTest.getSubject());
    }

    @Test
    void setSubject() {
        assertEquals(subject, bookTest.getSubject());
        bookTest.setSubject("New Subject");
        assertNotEquals(subject, bookTest.getSubject());
    }

    @Test
    void getPageCount() {
        assertEquals(pageCount, bookTest.getPageCount());
    }

    @Test
    void setPageCount() {
        assertEquals(pageCount, bookTest.getPageCount());
        bookTest.setPageCount(9909);
        assertNotEquals(pageCount, bookTest.getPageCount());
    }

    @Test
    void getAuthor() {
        assertEquals(author, bookTest.getAuthor());
    }

    @Test
    void setAuthor() {
        assertEquals(author, bookTest.getAuthor());
        bookTest.setAuthor("New Author");
        assertNotEquals(author, bookTest.getAuthor());
    }

    @Test
    void getDueDate() {
        assertEquals(dueDate, bookTest.getDueDate());
    }

    @Test
    void setDueDate() {
        assertEquals(dueDate, bookTest.getDueDate());
        bookTest.setDueDate(LocalDate.of(1999, 9, 22));
        assertNotEquals(dueDate, bookTest.getDueDate());
    }

    @Test
    void equals() {
        Book equalTest1 = new Book("1", "1", "1", 1, "1", LocalDate.of(1, 1, 1));
        Book equalTest2 = new Book("2", "2", "2", 2, "2", LocalDate.of(2, 2, 2));
        assertNotEquals(true, equalTest1.equals(equalTest2));
        equalTest2 = new Book("1", "1", "1", 1, "1", LocalDate.of(1, 1, 1));
        assertEquals(true, equalTest2.equals(equalTest1));
    }

    @Test
    void testHashCode() {
        Book equalTest1 = new Book("1", "1", "1", 1, "1", LocalDate.of(1, 1, 1));
        Book equalTest2 = new Book("2", "2", "2", 2, "2", LocalDate.of(2, 2, 2));
        assertNotEquals(equalTest1.hashCode(), equalTest2.hashCode());
        equalTest2 = new Book("1", "1", "1", 1, "1", LocalDate.of(1, 1, 1));
        assertEquals(equalTest1.hashCode(), equalTest2.hashCode());
    }
}