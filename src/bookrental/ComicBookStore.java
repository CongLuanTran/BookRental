/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package bookrental;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author tranc
 */
public class ComicBookStore {

    private static Set<Integer> usedID = new HashSet<>();

    /**
     * A simple, less randomized generator for book's id. Books with similar
     * titles and authors will probably appear closer to each other.
     *
     * @param title
     * @param author
     * @param volume
     * @return int
     */
    private static int simplerIDGenerator(String title, String author, int volume) {
        int hash = title.hashCode() % 10000;
        hash = hash + author.hashCode() % 10000;
        hash = hash + volume % 10000;
        hash = Math.abs(hash) % 10000;

        if (hash == 0) {
            hash++;
        }

        while (usedID.contains(hash)) {
            hash++;
        }

        return hash;
    }

    /*
     * Experimented a more complex hashing function. This one will produce more randomized result,
     * items with similar names can generate very different ID
     * 
     * private static int idGenerator(String title, String author, int volume) { int hash = 7; hash
     * = 31 * hash + volume; hash = 31 * hash + title.hashCode(); hash = 31 * hash +
     * author.hashCode(); hash = Math.abs(hash) % 10000;
     * 
     * while (usedID.contains(hash)) { hash++; }
     * 
     * return hash; }
     */
    private List<ComicBook> bookList;

    /**
     * Creating a list to store books' data.
     */
    public ComicBookStore() {
        bookList = new ArrayList<>();
    }

    /**
     * Add a new book, limited to 9999 books, and add the ID to usedID (id from
     * 0001 to 9999, 0 is reserved for exiting a function). If a id collision
     * occurs, it will generate a new ID. This should be used only to read books
     * already stored in the text file. Don't use to read user input.
     *
     * @param id
     * @param title
     * @param price
     * @param author
     * @param volume
     * @throws StoreException
     * @throws bookrental.BookException
     */
    public void addBook(int id, String title, double price, String author, int volume)
            throws StoreException, BookException {
        if (bookList.size() >= 9999) {
            throw new StoreException("The capacity of the bookstore cannot exceed 9999 books!");
        }

        if (usedID.contains(id)) {
            System.out.println("ID collision detected! Generating new ID ...");
            id = simplerIDGenerator(title, author, volume);
        }
        try {
            ComicBook cb;
            cb = new ComicBook(id, title, price, author, volume);
            bookList.add(cb);
            usedID.add(cb.getId());
        }
        catch (BookException e) {
            throw e;
        }
    }

    /**
     * Add book without id input. Essentially the same as the full addBook
     * function, but the ID is autogenerated.
     *
     * @param title
     * @param price
     * @param author
     * @param volume
     * @throws StoreException
     * @throws bookrental.BookException
     */
    public void addBook(String title, double price, String author, int volume) throws StoreException, BookException {
        addBook(simplerIDGenerator(title, author, volume), title, price, author, volume);
    }

    /**
     * remove book from the list, also remove its id from usedID
     *
     * @param id
     * @throws StoreException
     */
    public void removeBook(int id) throws StoreException {
        ComicBook cb = getBook(id);
        usedID.remove(cb.getId());
        bookList.remove(cb);
    }

    /**
     * Return all books in the list.
     *
     * @return List
     */
    public List<ComicBook> allBooks() {
        return this.bookList;
    }

    /**
     * Return all books that partially match the query.
     *
     * @param query
     * @return List
     */
    public List<ComicBook> search(String query) {
        List<ComicBook> searchResult = new ArrayList<>();
        query = query.toLowerCase().trim();
        for (ComicBook cb : bookList) {
            if (cb.getTitle().toLowerCase().contains(query)) {
                searchResult.add(cb);
            }
        }

        return searchResult;
    }

    /**
     * Return all books that have exactly the searched author.
     *
     * @param author
     * @return List
     */
    public List<ComicBook> searchByAuthor(String author) {
        List<ComicBook> searchResult = new ArrayList<>();
        author = author.toLowerCase().trim();
        for (ComicBook cb : bookList) {
            if (cb.getAuthor().toLowerCase().equals(author)) {
                searchResult.add(cb);
            }
        }

        return searchResult;
    }

    /**
     * Return the comic book with the specified id or null if that id doesn't
     * exist.
     *
     * @param id
     * @return ComicBook
     */
    public ComicBook getBook(int id) throws StoreException {
        for (ComicBook cb : bookList) {
            if (cb.getId() == id) {
                return cb;
            }
        }

        throw new StoreException("Book not found!");
    }

    /**
     * Return the amount of book in the store.
     *
     * @return int
     */
    public int size() {
        return bookList.size();
    }

    /**
     *
     * @param title
     * @param volume
     * @return
     */
    public boolean containsBook(String title, int volume) {
        for (ComicBook cb : bookList) {
            if (cb.getTitle().equalsIgnoreCase(title.trim()) && cb.getVolume() == volume) {
                return true;
            }
        }

        return false;
    }

}
