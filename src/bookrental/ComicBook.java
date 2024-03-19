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
    private int id;
    private String title;
    private double bookRentalPrice;
    private String author;
    private int volume;

    /**
     * creating comic book, id uniqueness will be handled in the store
     * 
     * @param id
     * @param title
     * @param bookRentalPrice
     * @param author
     * @param volume
     */
    public ComicBook(int id, String title, double bookRentalPrice, String author, int volume) {
        this.id = id;
        this.title = title;
        this.bookRentalPrice = bookRentalPrice;
        this.author = author;
        this.volume = volume;
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
     * return book's price
     * 
     * @return double
     */
    public double getBookRentalPrice() {
        return bookRentalPrice;
    }

    /**
     * set book's price
     * throws exception if it is negative
     * 
     * @param bookRentalPrice
     * @throws Exception
     */
    public void setBookRentalPrice(double bookRentalPrice) throws BookException {
        if (bookRentalPrice < 0) {
            throw new BookException("Rental price cannot be negative!");
        }
        this.bookRentalPrice = bookRentalPrice;
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
     * return book's volume
     * 
     * @return int
     */
    public int getVolume() {
        return volume;
    }

}
