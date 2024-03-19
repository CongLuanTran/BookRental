/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookrental;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tranc
 */
public class ComicBookStore {

    private List<ComicBook> bookList;

    public ComicBookStore() {
        bookList = new ArrayList<>();
    }

    public void addBook(ComicBook book) throws Exception {
        if (bookList.size() >= 999) {
            throw new Exception("The capacity of the bookstore cannot exceed 999 books!");
        }
        bookList.add(book);
    }
    
    public void removeBook(int id) throws Exception {
        for (ComicBook cb : bookList) {
            if (cb.getId() == id) {
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
