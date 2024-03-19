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

    private static int nextID = 1;
    private int id;
    private String title;
    private double bookRentalPrice;
    private String author;
    private int volume;

    public ComicBook(String title, double bookRentalPrice, String author, int volume) {
        this.id = nextID;
        this.title = title;
        this.bookRentalPrice = bookRentalPrice;
        this.author = author;
        this.volume = volume;

        nextID++;
    }
    
    

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getBookRentalPrice() {
        return bookRentalPrice;
    }

    public void setBookRentalPrice(double bookRentalPrice) throws Exception {
        if (bookRentalPrice < 0) {
            throw new Exception("Rental price cannot be negative!");
        }
        this.bookRentalPrice = bookRentalPrice;
    }
    
    public String getAuthor() {
        return author;
    }

    public int getVolume() {
        return volume;
    }

    
}
