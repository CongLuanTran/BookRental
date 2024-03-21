/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookrental;

/**
 *
 * @author tranc
 */
public class ComicBook {
    private final String author;
    private double bookRentalPrice;

    private final int id;
    private final String title;
    private int volume;

    /**
     * creating comic book, id uniqueness will be handled in the store
     *
     * @param id
     * @param title
     * @param bookRentalPrice
     * @param author
     * @param volume
     * @throws bookrental.BookException
     */
    public ComicBook(int id, String title, double bookRentalPrice, String author, int volume) throws BookException {
        if (volume <= 0) {
            throw new BookException("Volume must be a positive number larger than 0!");
        }
        if (bookRentalPrice <= 0) {
            throw new BookException("Rental price must be a positive number larger than 0!");
        }

        this.id = id;
        this.title = title;
        this.bookRentalPrice = bookRentalPrice;
        this.author = author;
        this.volume = volume;

    }
    /**
     * return book's author
     *
     * @return String
     */
    public String getAuthor() {
        return author;
    }


    /**
     * return book's price
     *
     * @return double
     */
    public double getBookRentalPrice() {
        return bookRentalPrice;
    }

    /**
     * set book's price throws exception if it is negative
     *
     * @param bookRentalPrice
     * @throws bookrental.BookException
     */
    public void setBookRentalPrice(double bookRentalPrice) throws BookException {
        if (bookRentalPrice <= 0) {
            throw new BookException("Rental price must be a positive number larger than 0!");
        }
        this.bookRentalPrice = bookRentalPrice;
    }
    /**
     * return book's id
     *
     * @return int
     */
    public int getId() {
        return id;
    }
    /**
     * return book's title
     *
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * return book's volume
     *
     * @return int
     */
    public int getVolume() {
        return volume;
    }
}
