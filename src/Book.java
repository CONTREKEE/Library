/**
 * Name: Keegan Farris
 * Date: 2-1-2022
 * Explanation: Sets up fields, isbn, title, subject, pageCount, author, and dueDate.
 * Contains a constructor to insert data into those fields.
 * Contains getters and setters for each field.
 * Contains a hashCode method to get the hash, a equals method to check if equal, and a toString method
 * to print out the formatted book information.
 */

import java.time.LocalDate;
import java.util.Objects;

public class Book {


    //Locates where each field is in the comma separated data
    public static final int ISBN_ = 0;
    public static final int TITLE_ = 1;
    public static final int SUBJECT_ = 2;
    public static final int PAGE_COUNT_ = 3;
    public static final int AUTHOR_ = 4;
    public static final int DUE_DATE_ = 5;

    //Stores information about the book in these fields
    private String isbn;
    private String title;
    private String subject;
    private int pageCount;
    private String author;
    private LocalDate dueDate;

    //Creates constructor with isbn, title, subject, page count, author, and due date
    public Book(String isbn, String title, String subject, int pageCount, String author, LocalDate dueDate) {
        this.isbn = isbn;
        this.title = title;
        this.subject = subject;
        this.pageCount = pageCount;
        this.author = author;
        this.dueDate = dueDate;
    }

    //All of the setters and getters below
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) { this.author = author; }

    public LocalDate getDueDate() { return dueDate; }

    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    //Formats the information into a string that contains the title, author, and isbn
    @Override
    public String toString() {
        return getTitle() + " by " + getAuthor() + " ISBN: " + getIsbn();
    }

    //Checks to see if the objects are equal, comparing the book information
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return getPageCount() == book.getPageCount() && getIsbn().equals(book.getIsbn()) && getTitle().equals(book.getTitle()) && getSubject().equals(book.getSubject()) && getAuthor().equals(book.getAuthor());
    }

    //Gets the hashcode of the object by taking in the isbn, title, subject, page count, and author.
    @Override
    public int hashCode() {
        return Objects.hash(getIsbn(), getTitle(), getSubject(), getPageCount(), getAuthor());
    }
}
