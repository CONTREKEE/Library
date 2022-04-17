import java.util.HashMap;
import java.util.Objects;

/**
 * Author: Keegan Farris
 * Date: 2-9-2022
 * Description: Responsible for adding, removing, and getting information about books on the shelf.
 */

public class Shelf {

    //Fields used to select shelf number and subject from comma separated database
    public static final int SHELF_NUMBER_ = 0;
    public static final int SUBJECT_ = 1;

    //Defines shelfNumber, subject, and books.
    private int shelfNumber;
    private String subject;
    private HashMap<Book, Integer> books;

    /**
     * Constructor
     * Initiates books, sets shelfNumber and subject
     * @param shelfNumber
     * @param subject
     */
    public Shelf(int shelfNumber, String subject) {
        this.books =  new HashMap<>();
        this.shelfNumber = shelfNumber;
        this.subject = subject;
    }

    //Getters and setters for shelfNumber, subject, and books.
    public int getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(int shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public HashMap<Book, Integer> getBooks() {
        return books;
    }

    public void setBooks(HashMap<Book, Integer> books) {
        this.books = books;
    }

    /**
     * Checks if Shelf objects are equal by comparing the shelf number and shelf subject.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shelf)) return false;
        Shelf shelf = (Shelf) o;
        return getShelfNumber() == shelf.getShelfNumber() && getSubject().equals(shelf.getSubject());
    }

    //Returns hashcode of shelf, using shelf number and subject.
    @Override
    public int hashCode() {
        return Objects.hash(getShelfNumber(), getSubject());
    }

    //Returns a string with shelf number and subject (ex: 1 : education)
    @Override
    public String toString() {
        return shelfNumber + " : " + subject;
    }

    /**
     * Adds a book to the list of books on shelf
     * Checks if book already exists on shelf and adds 1 to the amount of that book, returns Code.SUCCESS
     * Checks if subject equals the book trying to be added. If book is not in list and the subjects match, then adds the book to the shelf, returns Code.SUCCESS
     * Otherwise returns Code.SHELF_SUBJECT_MISMATCH_ERROR;
     * @param book
     * @return
     */
    public Code addBook(Book book) {

        if (books.containsKey(book)) {
            books.put(book, books.get(book)+1);
            System.out.println(book.toString() + " added to shelf " + this);
            return Code.SUCCESS;
        }

        if (getSubject().equals(book.getSubject())) {
            books.put(book, 1);
            System.out.println(book+ " added to shelf " + this);
            return Code.SUCCESS;
        }
        return Code.SHELF_SUBJECT_MISMATCH_ERROR;
    }

    /**
     * Returns the amount of books on the shelf if the book exists.
     * Returns -1 otherwise.
     * @param book
     * @return
     */
    public int getBookCount(Book book) {

        if (books.containsKey(book)) {
            return books.get(book);
        }
        return -1;

    }

    /**
     * Removes one book from the list of books on the shelf if it is greater than 0 (returns Code.SUCCESS)
     * If there are no more books in stock returns Code.BOOK_NOT_IN_INVENTORY_ERROR
     * If the book does not exist in the shelf, returns Code.BOOK_NOT_IN_INVENTORY_ERROR
     * @param book
     * @return
     */
    public Code removeBook(Book book) {

        if (books.containsKey(book)) {
            if (books.get(book) > 0) {
                books.put(book, books.get(book) - 1);
                System.out.println(book.getTitle() + " successfully removed from shelf " + getSubject() + ".");
                return Code.SUCCESS;
            }
            System.out.println("No copies of " + book.getTitle() + " remain on shelf " + getSubject() + ".");
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        System.out.println(book.getTitle() + " is not on shelf " + getSubject() + ".");
        return Code.BOOK_NOT_IN_INVENTORY_ERROR;

    }

    /**
     * Lists all the books on the shelf with how many books of each are available.
     * @return
     */
    public String listBooks() {
        StringBuilder allBooks = new StringBuilder();
        int addAllBooks = 0;

        for (Book book: books.keySet()) {
            addAllBooks += getBookCount(book);
        }

        allBooks.append(addAllBooks);

        if (addAllBooks > 1) {
            allBooks.append(" books on shelf: ");
        }else if (addAllBooks == 0){
            allBooks.append(" books on shelf: ");
        }else {
            allBooks.append(" book on shelf: ");
        }

        allBooks.append(getShelfNumber());
        allBooks.append(" : ");
        allBooks.append(getSubject());
        allBooks.append("\n");

        for (Book book: books.keySet()) {
            allBooks.append(book.toString());
            allBooks.append(" ");
            allBooks.append(books.get(book));
            allBooks.append("\n");
        }

        System.out.println(allBooks);
        return allBooks.toString().trim();
    }

}
