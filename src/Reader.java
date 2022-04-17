import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Name: Keegan Farris
 * Date: 2-4-2022
 * Explanation: Sets up books, name, phone, cardNumber,.
 * Contains a constructor to insert data into those fields.
 * Contains getters and setters for each field.
 * Contains a hashCode method to get the hash, a equals method to check if equal, and a toString method
 * to print out the formatted book information.
 */

public class Reader {

    //Static fields that are not changed - used to get location of variables in comma separated database
    public static final int CARD_NUMBER_ = 0;
    public static final int NAME_ = 1;
    public static final int PHONE_ = 2;
    public static final int BOOK_COUNT_ = 3;
    public static final int BOOK_START_ = 4;

    //private fields used to store reader information and books checked out
    private int cardNumber;
    private String name;
    private String phone;
    private List<Book> books;

    //creates constructor with fields cardNumber, name, and phone
    public Reader(int cardNumber, String name, String phone) {
        this.books = new ArrayList<>();
        this.cardNumber = cardNumber;
        this.name = name;
        this.phone = phone;
    }

    //Adds a book to the current book list, if its not already added
    public Code addBook(Book book) {

        if (!books.contains(book)) {
            books.add(book);
            return Code.SUCCESS;
        }
        return Code.BOOK_ALREADY_CHECKED_OUT_ERROR;

    }

    //Removes a book from the book list if the list contains the book
    public Code removeBook(Book book) {

        if (books.contains(book)) {

            try {
                books.remove(book);
                return Code.SUCCESS;
            } catch (Exception e) {
                return Code.READER_COULD_NOT_REMOVE_BOOK_ERROR;
            }
        }
        return Code.READER_DOESNT_HAVE_BOOK_ERROR;

    }

    //Checks if reader has a book
    public Boolean hasBook(Book book) {

        if (books.contains(book)) {
            return true;
        }
        return false;
    }

    //gets amount of books a reader has
    public int getBookCount() {
        return books.size();
    }

    //gets the list of books a reader has
    public List<Book> getBooks() {
        return books;
    }

    //sets the books a reader has
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    //gets the readers card number
    public int getCardNumber() {
        return cardNumber;
    }

    //sets the readers card number
    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    //gets the readers name
    public String getName() {
        return name;
    }

    //sets the readers name
    public void setName(String name) {
        this.name = name;
    }

    //gets the readers phone number
    public String getPhone() {
        return phone;
    }

    //sets the readers phone number
    public void setPhone(String phone) {
        this.phone = phone;
    }

    //checks if readers are equal
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reader)) return false;
        Reader reader = (Reader) o;
        return getCardNumber() == reader.getCardNumber() && getName().equals(reader.getName()) && getPhone().equals(reader.getPhone());
    }

    //checks if reader hash codes are equal
    @Override
    public int hashCode() {
        return Objects.hash(getCardNumber(), getName(), getPhone());
    }

    //formats to a string that includes the readers name, card number, and list of books checked out
    @Override
    public String toString() {
        StringBuilder bookNames = new StringBuilder();

        //loops through every book the reader has
        for (int i = 0; i < books.size(); i++) {

            if (i != books.size() - 1) {
                bookNames.append(books.get(i).getTitle() + ", "); //adds a , if it's not the last book
                System.out.println(books.get(i).getTitle());
            } else {
                bookNames.append(books.get(i).getTitle());
            }
        }
        return name + "(#" + cardNumber + ") has checked out {" + bookNames + "}";
    }
}
