/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    private static int simplerIDGenerator(String title, String author, int volume) {
        int hash = 7;
        hash = 31 * hash * title.hashCode() + author.hashCode() + volume;
        hash = Math.abs(hash) % 10000;

        while (usedID.contains(hash)) {
            hash++;
        }

        return hash;
    }

    /*
     * Exprerimented a more complex hashing function. This one will produce more
     * randomized result, items with similar names can generate very different ID
     * 
     * private static int idGenerator(String title, String author, int volume) {
     * int hash = 7;
     * hash = 31 * hash + volume;
     * hash = 31 * hash + title.hashCode();
     * hash = 31 * hash + author.hashCode();
     * hash = Math.abs(hash) % 10000;
     * 
     * while (usedID.contains(hash)) {
     * hash++;
     * }
     * 
     * return hash;
     * }
     */

    private List<ComicBook> bookList;

    public ComicBookStore() {
        bookList = new ArrayList<>();
    }

    public void addBook(int id, String title, double price, String author, int volume) throws Exception {
        if (bookList.size() >= 9999) {
            throw new Exception("The capacity of the bookstore cannot exceed 9999 books!");
        }

        if (usedID.contains(id)) {
            System.out.println("ID collision detected! Generating new ID ...");
            id = simplerIDGenerator(title, author, volume);
        }
        ComicBook cb = new ComicBook(id, title, price, author, volume);
        bookList.add(cb);
        usedID.add(cb.getId());
    }

    public void addBook(String title, double price, String author, int volume) throws Exception {
        addBook(simplerIDGenerator(title, author, volume), title, price, author, volume);
    }

    public void removeBook(int id) throws Exception {
        for (ComicBook cb : bookList) {
            if (cb.getId() == id) {
                usedID.remove(cb.getId());
                bookList.remove(cb);
                return;
            }
        }

        throw new Exception("No book with this ID!");
    }

    public List<ComicBook> allBooks() {
        return this.bookList;
    }

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

    public ComicBook getBook(int id) {
        for (ComicBook cb : bookList) {
            if (cb.getId() == id) {
                return cb;
            }
        }

        return null;
    }

    public int size() {
        return bookList.size();
    }

    public boolean containsBook(String title, int volume) {
        for (ComicBook cb : bookList) {
            if (cb.getTitle().equalsIgnoreCase(title.trim())
                    && cb.getVolume() == volume) {
                return true;
            }
        }

        return false;
    }
}
