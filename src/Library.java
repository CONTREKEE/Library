import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;

/**
 * Author : Keegan Farris
 * Assignment Name: Library: Project 01
 * Date : 2-21-2022
 * Description : Sets up information from csv files using Book, Shelf, Reader, and Code.
 */

public class Library {

    public static final int LENDING_LIMIT = 3;

    private String name;
    private static int libraryCard;
    private List<Reader> readers;
    private HashMap<String, Shelf> shelves;
    private HashMap<Book, Integer> books;


    public Library(String library) {
        this.name = library;
        readers = new ArrayList<>();
        shelves = new HashMap<>();
        books = new HashMap<>();
    }

    /**
     * Takes in file to initialize.
     * @param fileName
     * @return
     */
    public Code init(String fileName) {

        File f = new File(fileName);
        Scanner scan;


        try {
            scan = new Scanner(f);
        } catch (FileNotFoundException e) {
            return Code.FILE_NOT_FOUND_ERROR;
        }

        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            if (convertInt(line, Code.BOOK_COUNT_ERROR) > 0) {
                initBooks(convertInt(line, Code.BOOK_COUNT_ERROR), scan);
                return Code.SUCCESS;
            }

        }
        return Code.UNKNOWN_ERROR;
    }

    /**
     * Takes in bookCount and scan from init to add books to the library.
     * @param bookCount
     * @param scan
     * @return
     */
    private Code initBooks(int bookCount, Scanner scan) {

        if (bookCount < 1) {
            return Code.LIBRARY_ERROR;
        }

        System.out.println("parsing " + bookCount + " books");

        for (int i = 0; i < bookCount; i++) {
            String bookLine = scan.nextLine();
            System.out.println("parsing book: " + bookLine);
            String[] line = bookLine.split(",");
            addBook(new Book(line[Book.ISBN_], line[Book.TITLE_],
                    line[Book.SUBJECT_], convertInt(line[Book.PAGE_COUNT_], Code.PAGE_COUNT_ERROR), line[Book.AUTHOR_],
                    convertDate(line[Book.DUE_DATE_], Code.DATE_CONVERSION_ERROR)));
        }

        listBooks();

        String shelfCount = scan.nextLine();

        if (convertInt(shelfCount, Code.SHELF_COUNT_ERROR) > 0) {
            initShelves(convertInt(shelfCount, Code.SHELF_COUNT_ERROR), scan);
        }

        return Code.SUCCESS;

    }

    /**
     * Takes in shelfCount and scan from initBooks to add shelves to the library.
     * @param shelfCount
     * @param scan
     * @return
     */
    private Code initShelves(int shelfCount, Scanner scan) {

        if (shelfCount < 1) {
            return Code.SHELF_COUNT_ERROR;
        }

        System.out.println("parsing " + shelfCount + " shelves.");

        for (int i = 0; i < shelfCount; i++) {
            String shelfLine = scan.nextLine();
            String[] shelf = shelfLine.split(",");
            System.out.println("Parsing Shelf : " + shelfLine);
            addShelf(shelf[Shelf.SUBJECT_]);

            for (Book book : books.keySet()) {
                if (!book.getSubject().equals(shelf[Shelf.SUBJECT_])) {
                    continue;
                }
                if (shelves.containsKey(book.getSubject())) {
                    for (int j = 0; j < books.get(book)-1; j++) {
                        shelves.get(book.getSubject()).addBook(book);
                    }
                }
            }

        }

        if (shelfCount != shelves.size()) {
            System.out.println("Number of shelves doesn't match expected");
            return Code.SHELF_NUMBER_PARSE_ERROR;
        }

        String readerCount = scan.nextLine();
        if (convertInt(readerCount, Code.READER_COUNT_ERROR) > 0) {
            initReader(convertInt(readerCount, Code.READER_COUNT_ERROR), scan);
        }

        return Code.SUCCESS;
    }

    /**
     * Takes in readerCount and scan from initShelves to add readers to the library.
     * @param readerCount
     * @param scan
     * @return
     */
    private Code initReader(int readerCount, Scanner scan) {

        if (readerCount < 1) {
            return Code.READER_COUNT_ERROR;
        }

        for (int i = 0; i < readerCount; i++) {
            String[] values = scan.nextLine().split(",");
            Reader reader = new Reader(convertInt(values[Reader.CARD_NUMBER_], Code.READER_CARD_NUMBER_ERROR),
                    values[Reader.NAME_], values[Reader.PHONE_]);
            addReader(reader);

            for (int j = 0; j < convertInt(values[Reader.BOOK_COUNT_], Code.BOOK_COUNT_ERROR)*2; j+=2) {

                Book book = getBookByISBN(values[Reader.BOOK_START_ + j]);

                if(book != null) {
                    LocalDate date = convertDate(values[Reader.BOOK_START_ + j + 1], Code.DATE_CONVERSION_ERROR);
                    book.setDueDate(date);
                    checkOutBook(reader, book);
                }

            }

        }

        return Code.SUCCESS;
    }

    /**
     * Adds a book to the library.
     * @param book
     * @return
     */
    public Code addBook(Book book) {


        if (books.containsKey(book)) {
            books.put(book, books.get(book) + 1);
            System.out.println(books.get(book) + " copies of " + book + " in the stacks.");
        } else {
            books.put(book, 1);
            System.out.println(book + " added to the stacks.");
        }

        if (shelves.containsKey(book.getSubject())) {
            addBookToShelf(book, getShelf(book.getSubject()));
            return Code.SUCCESS;
        }
        System.out.println("No shelf for " + book.getSubject() + " books.");
        return Code.SHELF_EXISTS_ERROR;
    }

    /**
     * Adds a book to the correct shelf by subject
     * @param book
     * @param shelf
     * @return
     */

    private Code addBookToShelf(Book book, Shelf shelf) {

        if (returnBook(book).equals(Code.SUCCESS)) {
            return Code.SUCCESS;
        }

        if (!shelf.getSubject().equals(book.getSubject())) {
            return Code.SHELF_SUBJECT_MISMATCH_ERROR;
        }

        Code addBookCode = shelf.addBook(book);

        if(addBookCode.equals(Code.SUCCESS)) {
            System.out.println(book + " added to shelf.");
            return Code.SUCCESS;
        }

        System.out.println("Could not add " + book + "to shelf.");
        return addBookCode;

    }

    /**
     * Finds a book with the given ISBN number.
     * @param isbn
     * @return
     */
    public Book getBookByISBN(String isbn) {

        for (Book book : books.keySet()) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        System.out.println("Error: could not find a book with isbn: " + isbn + ".");
        return null;
    }

    /**
     * Checks out the book for the given user if available and user has not reached the limit.
     * @param reader
     * @param book
     * @return
     */
    public Code checkOutBook(Reader reader, Book book) {

        if (!readers.contains(reader)) {
            System.out.println(reader.getName() + " doesn't have an account here.");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        }

        if(reader.getBooks().size() >= LENDING_LIMIT) {
            System.out.println(reader.getName() + " has reached the lending limit, (" + LENDING_LIMIT + ")");
            return Code.BOOK_LIMIT_REACHED_ERROR;
        }

        if (!books.containsKey(book)) {
            System.out.println("ERROR: could not find " + "[" + book.toString() + "]");
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        if (!shelves.containsKey(book.getSubject())) {
            System.out.println("No subject for " + book.getSubject() + " books!");
            return Code.SHELF_EXISTS_ERROR;
        }

        if (shelves.get(book.getSubject()).getBookCount(book) < 1) {
            System.out.println("Error: no copies of " + book + " remain.");
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        Code returnedCode = reader.addBook(book);

        if(!reader.addBook(book).equals(Code.SUCCESS)) {
            return returnedCode;
        }

        Code shelfCode = shelves.get(book.getSubject()).removeBook(book);

        if (shelfCode.equals(Code.SUCCESS)) {
            System.out.println(book + " checked out successfully.");
            return Code.SUCCESS;
        }
        return shelfCode;
    }

    /**
     * Returns a book for the given user.
     * @param reader
     * @param book
     * @return
     */
    public Code returnBook(Reader reader, Book book) {

        if (!reader.getBooks().contains(book)) {
            System.out.println(reader.getName() + " doesn't have " + book.getTitle() + " checked out.");
            return Code.READER_DOESNT_HAVE_BOOK_ERROR;
        }

        if (!books.containsKey(book)) {
            return Code.BOOK_NOT_IN_INVENTORY_ERROR;
        }

        System.out.println(reader.getName() + " is returning " + book);
        Code removeBookCode = reader.removeBook(book);

        if (removeBookCode.equals(Code.SUCCESS)) {
            return returnBook(book);
        }
        System.out.println("Could not return " + book);
        return removeBookCode;
    }

    /**
     * Adds book back to the shelf if shelf exists.
     * @param book
     * @return
     */
    public Code returnBook(Book book) {

        if (!shelves.containsKey(book.getSubject())) {
            System.out.println("No shelf for book" + book);
            return Code.SHELF_EXISTS_ERROR;
        }

        getShelf(book.getSubject()).addBook(book);
        return Code.SUCCESS;
    }

    /**
     * Adds a shelf to the library with a given String.
     * @param shelf
     * @return
     */
    public Code addShelf(String shelf) {

        if (shelves.containsKey(shelf)) {
            System.out.println("Error: Shelf already exists " + shelf);
            return Code.SHELF_EXISTS_ERROR;
        }

        shelves.put(shelf, new Shelf(shelves.size() + 1, shelf));

        for (Book book : books.keySet()) {

            if (book.getSubject().equals(shelf)) {
                getShelf(shelf).addBook(book);
            }
        }

        return Code.SUCCESS;
    }

    /**
     * Adds a shelf to the library with a given Shelf object.
     * @param shelf
     * @return
     */
    public Code addShelf(Shelf shelf) {

        if (shelves.containsKey(shelf.getSubject())) {
            System.out.println("Error: Shelf already exists " + shelf);
            return Code.SHELF_EXISTS_ERROR;
        }

        shelf.setShelfNumber(shelves.size() + 1);
        shelves.put(shelf.getSubject(), shelf);

        for (Book book : books.keySet()) {

            if (book.getSubject().equals(shelf)) {
                shelf.addBook(book);
            }
        }

        return Code.SUCCESS;

    }

    /**
     * Adds a reader to the library.
     * @param reader
     * @return
     */
    public Code addReader(Reader reader) {

        if (readers.contains(reader)) {
            System.out.println(reader.getName() + " already has an account!");
            return Code.READER_ALREADY_EXISTS_ERROR;
        }

        for (Reader user : readers) {

            if (reader.getCardNumber() == user.getCardNumber()) {
                System.out.println(user.getName() + " and " + reader.getName() + " have the same card number!");
                return Code.READER_CARD_NUMBER_ERROR;
            }
        }

        readers.add(reader);
        System.out.println(reader.getName() + " added to the library!");
        libraryCard = reader.getCardNumber() > this.libraryCard ? reader.getCardNumber() : this.libraryCard;
        return Code.SUCCESS;

    }

    /**
     * Removes a reader from the library.
     * @param reader
     * @return
     */
    public Code removeReader(Reader reader) {

        if(reader.getBookCount() > 0) {
            System.out.println(reader.getName() + " must return all books!");
            return Code.READER_STILL_HAS_BOOKS_ERROR;
        }

        if(!readers.contains(reader)) {
            System.out.println(reader + " is not part of this Library");
            return Code.READER_NOT_IN_LIBRARY_ERROR;
        }

        readers.remove(reader);
        return Code.SUCCESS;

    }

    /**
     * Returns a Reader if the given cardNumber exists.
     * @param cardNumber
     * @return
     */
    public Reader getReaderByCard(int cardNumber) {

        for (Reader reader : readers) {
            if (reader.getCardNumber() == cardNumber) {
                return reader;
            }
        }
        System.out.println("Could not find a reader with card #" + cardNumber + ".");
        return null;
    }

    /**
     * Lists all books in the library.
     * @return
     */
    public int listBooks() {
        int total = 0;
        int currentMax = -1;
        ArrayList<Integer> bookNum = new ArrayList<>();
        while (currentMax+1 < books.size()) {

            if (currentMax == -1) {

                for (Book book : books.keySet()) {

                    bookNum.add(books.get(book));
                    total+=books.get(book);

                }

                Collections.sort(bookNum, Collections.reverseOrder());
                currentMax++;
                continue;
            }

            for (Book book : books.keySet()) {

                if (currentMax >= books.size()) {
                    break;
                }

                if (books.get(book) == bookNum.get(currentMax)) {
                    System.out.println(books.get(book) + " copies of " + book);
                    currentMax++;
                }
            }

        }

        return total;
    }

    /**
     * Lists all readers in the library.
     * @return
     */
    public int listReaders() {

        for (Reader reader : readers) {
            System.out.println(reader);
        }
        return readers.size();
    }

    /**
     * Lists all readers in the library. If showBooks is true, lists all readers and the books they checked out.
     * @param showBooks
     * @return
     */
    public int listReaders(boolean showBooks) {

        if(showBooks) {

            for (Reader reader : readers) {

                if (reader.getBooks().size() > 0) {

                    System.out.println(reader.getName() + "(#" + reader.getCardNumber() + ") has the following books:");

                    System.out.println(reader.getBooks());


                }else {

                    System.out.println(reader.getName() + "(#" + reader.getCardNumber() + ") has no books.");

                }

            }

        }else {
            listReaders();
        }
        return readers.size();
    }

    /**
     * Returns shelf if the given shelfNumber exists.
     * @param shelfNumber
     * @return
     */
    public Shelf getShelf(Integer shelfNumber) {

        for (Shelf shelf : shelves.values()) {
            if (shelf.getShelfNumber() == shelfNumber) {
                return shelf;
            }
        }
        System.out.println("No shelf number " + shelfNumber + " found.");
        return null;
    }

    /**
     * Returns shelf if the given shelf subject exists.
     * @param subject
     * @return
     */
    public Shelf getShelf(String subject) {

        for (Shelf shelf : shelves.values()) {

            if (shelf.getSubject().equals(subject)) {
                return  shelf;
            }

        }
        System.out.println("No shelf for " + subject + " books.");
        return null;
    }

    /**
     * Lists all shelves in the library. If showBooks is true, lists all shelves and the books on each shelf.
     * @param showBooks
     * @return
     */
    public Code listShelves(boolean showBooks) {

        for (Shelf shelf : shelves.values()) {
            if (showBooks) {
                shelf.listBooks();
            }else {
                System.out.println(shelf.toString());
            }
        }

        return Code.SUCCESS;
    }

    /**
     * Converts a string to an integer.
     * @param recordCountString
     * @return
     */
    public static int convertInt(String recordCountString, Code code) {

        int number = 0;

        try {
            number = Integer.parseInt(recordCountString);
        } catch (NumberFormatException e) {
            System.out.println("Value which caused the error: " + recordCountString);

            if (code.equals(Code.BOOK_COUNT_ERROR)) {
                System.out.println("Error: Could not read number of books.");
            }else if (code.equals(Code.PAGE_COUNT_ERROR)) {
                System.out.println("Error: Could not parse page count.");
            }else if (code.equals(Code.DATE_CONVERSION_ERROR)) {
                System.out.println("Error: Could not parse date component.");
            }else {
                System.out.println("Error: Unknown conversion error.");
            }

            return code.getCode();
        }

        return number;

    }

    /**
     * Returns the next libraryCard number after the max.
     * @return
     */
    public static int getLibraryCardNumber() {
        return libraryCard + 1;
    }

    /**
     * Returns a Code object from the integer value of the Code.
     * @param code
     * @return
     */
    private Code errorCode(int code) {

        for (Code currentCode : Code.values()) {
            if (currentCode.getCode() == code) {
                return currentCode;
            }
        }
        return Code.UNKNOWN_ERROR;
    }

    /**
     * Converts the date from a string to a LocalDate.
     * @param date
     * @return
     */
    public static LocalDate convertDate(String date, Code code) {

        if (date.equals("0000")) {
            return LocalDate.EPOCH;
        }

        if (date.split("-").length < 3) {
            System.out.println("Error: Date conversion error, could not parse [" + date + "]");
            System.out.println("Using default date (01-Jan-1970)");
            return LocalDate.EPOCH;
        }

        return LocalDate.parse(date);


    }


}
