/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package bookrental;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author tranc
 */
public class TextUI {
    private static String repeat(int n, String s) {
        return new String(new char[n]).replace("\0", s);
    }
    private final File file;
    private final Scanner sc;
    private final ComicBookStore store;

    /**
     *
     * @param store
     * @param sc
     * @param file
     */
    public TextUI(ComicBookStore store, Scanner sc, File file) {
        this.store = store;
        this.sc = sc;
        this.file = file;
    }

    /**
     *
     */
    public void start() {
        loadStore(file);
        while (true) {
            menu();
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    addBook();
                    break;
                case "2":
                    if (storeAvailable()) {
                        search();
                    }
                    break;
                case "3":
                    if (storeAvailable()) {
                        searchByAuthor();
                    }
                    break;
                case "4":
                    if (storeAvailable()) {
                        changePrice();
                    }
                    break;
                case "5":
                    if (storeAvailable()) {
                        deleteBook();
                    }
                    break;
                case "6":
                    saveStore(file);
                    System.out.println("Thank you, and goodbye!");
                    return;
                default:
                    System.out.println("Invalid input, please try again!");
            }
        }
    }
    private void addBook() {
        String title, author;
        int volume;
        double price;

        while (true) {
            do {
                System.out.print("Enter book's title (or 0 to return): ");
                title = sc.nextLine().trim();
                if (title.equals("0")) {
                    return;
                }
            }
            while (title.isEmpty());

            while (true) {
                System.out.print("Enter volume (or 0 to return): ");
                String next = sc.nextLine();

                if (next.equals("0")) {
                    return;
                }
                try {
                    volume = Integer.parseInt(next);
                    if (volume > 0) {
                        break;
                    }

                    System.out.println("Volume number must be an integer larger than 0!");
                }
                catch (NumberFormatException e) {
                    System.out.println("Please enter only a positive integer larger than 0!");
                }
            }

            if (store.containsBook(title, volume)) {
                System.out.println("Book already existed!");
                continue;
            }

            break;
        }

        do {
            System.out.print("Enter author (or 0 to return): ");
            author = sc.nextLine().trim();
            if (author.equals("0")) {
                return;
            }
        }
        while (author.isEmpty());

        while (true) {
            System.out.print("Enter rental price (or 0 to return): ");
            String next = sc.nextLine();

            if (next.equals("0")) {
                return;
            }
            try {
                price = Double.parseDouble(next);

                if (price > 0.0) {
                    break;
                }

                System.out.println("Rental price must be a positive number larger than 0!");
            }
            catch (NumberFormatException e) {
                System.out.println("Please enter only a positive number larger than 0!");
            }
        }

        try {
            store.addBook(title, price, author, volume);
        }
        catch (StoreException | BookException e) {
            System.out.println(e.getMessage());
        }
        print(store.allBooks());
    }
    private void changePrice() {
        int id;
        ComicBook cb;
        print(store.allBooks());

        while (true) {
            System.out.print("Enter the id of the book (or 0 to return): ");
            String next = sc.nextLine();

            if (next.equals("0")) {
                return;
            }

            try {
                id = Integer.parseInt(next);
                cb = store.getBook(id);
                break;
            }
            catch (NumberFormatException e) {
                System.out.println("Please enter only a positive integer! larger than 0");
            }
            catch (StoreException e) {
                System.out.println(e.getMessage());
            }
        }

        while (true) {
            double price;

            while (true) {
                System.out.print("Enter rental price (or 0 to return): ");
                String next = sc.nextLine();

                if (next.equals("0")) {
                    return;
                }
                try {
                    price = Double.parseDouble(next);
                    cb.setBookRentalPrice(price);
                    System.out.println("Changed book: ");
                    print(cb);
                    return;
                }
                catch (NumberFormatException e) {
                    System.out.println("Please enter only a positive number larger than 0!");
                }
                catch (BookException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void deleteBook() {
        int id;
        print(store.allBooks());

        while (true) {
            System.out.print("Which book do you want to delete? (or 0 to return) ");
            String next = sc.nextLine();

            if (next.equals("0")) {
                return;
            }

            try {
                id = Integer.parseInt(next);
                store.removeBook(id);
                return;
            }
            catch (NumberFormatException e) {
                System.out.println("Please enter only a positive integer! larger than 0");
            }
            catch (StoreException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    private void loadStore(File file) {
        System.out.println("Loading book store database ...");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int count = Integer.parseInt(br.readLine());
            
            for (int i = 0; i < count; i++) {
                int id = Integer.parseInt(br.readLine());
                String title = br.readLine();
                double price = Double.parseDouble(br.readLine());
                String author = br.readLine();
                int volume = Integer.parseInt(br.readLine());
                store.addBook(id, title, price, author, volume);
            }
            
            System.out.println("Finish loading database!");
            System.out.println("Number of book in the store: " + store.allBooks().size());
            print(store.allBooks());
        }
        catch (StoreException | BookException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println("Cannot load database! An empty database will be used!");
        }
    }

    private void menu() {
        System.out.println("          COMIC RENTAL SHOP");
        System.out.println("1. Add new comic book.\n"
                           + "2. Search book by title.\n"
                           + "3. Search book of an author.\n"
                           + "4. Update book rental price.\n"
                           + "5. Delete comic book.\n"
                           + "6. Quit.");
        System.out.print("     Please select a function: ");
    }
    private void print(List<ComicBook> books) {
        
        String id = "ID";
        String title = "Title";
        String volume = "Volume";
        String author = "Author";
        String price = "Price";

        if (books.isEmpty()) {
            System.out.println("There is nothing to print!");
            return;
        }

        int idLength = books.stream()
                .mapToInt(cb -> String.valueOf(cb.getId()).length())
                .max()
                .getAsInt();
        idLength = (idLength > id.length()) ? idLength : id.length();

        int titleLength = books.stream()
                .mapToInt(cb -> cb.getTitle().length())
                .max()
                .getAsInt();
        titleLength = (titleLength > title.length()) ? titleLength : title.length();

        int volumeLength = books.stream()
                .mapToInt(cb -> String.valueOf(cb.getVolume()).length())
                .max()
                .getAsInt();
        volumeLength = (volumeLength > volume.length()) ? volumeLength : volume.length();

        int authorLength = books.stream()
                .mapToInt(cb -> cb.getAuthor().length())
                .max()
                .getAsInt();
        authorLength = (authorLength > author.length()) ? authorLength : author.length();

        int priceLength = books.stream()
                .mapToInt(cb -> String.valueOf(cb.getBookRentalPrice()).length())
                .max()
                .getAsInt();
        priceLength = (priceLength > price.length()) ? priceLength : price.length();

        System.out.println("+-" + repeat(idLength, "-")
                           + "-+-" + repeat(titleLength, "-")
                           + "-+-" + repeat(volumeLength, "-")
                           + "-+-" + repeat(authorLength, "-")
                           + "-+-" + repeat(priceLength, "-") + "-+");

        id = String.format("%-" + idLength + "s", id);
        title = String.format("%-" + titleLength + "s", title);
        volume = String.format("%" + volumeLength + "s", volume);
        author = String.format("%-" + authorLength + "s", author);
        price = String.format("%-" + priceLength + "s", price);

        System.out.println("| " + id + " | " + title + " | " + volume + " | " + author + " | "
                           + price + " |");

        System.out.println("+-" + repeat(idLength, "-")
                           + "-+-" + repeat(titleLength, "-")
                           + "-+-" + repeat(volumeLength, "-")
                           + "-+-" + repeat(authorLength, "-")
                           + "-+-" + repeat(priceLength, "-") + "-+");

        for (ComicBook cb : books) {
            id = String.format("%" + idLength + "s", String.format("%0" + idLength + "d", cb.getId()));
            title = String.format("%-" + titleLength + "s", cb.getTitle());
            volume = String.format("%" + volumeLength + "s", String.valueOf(cb.getVolume()));
            author = String.format("%-" + authorLength + "s", cb.getAuthor());
            price = String.format("%" + priceLength + "s", String.valueOf(cb.getBookRentalPrice()));

            System.out.println("| " + id + " | " + title + " | " + volume + " | " + author + " | "
                               + price + " |");
        }

        System.out.println("+-" + repeat(idLength, "-")
                           + "-+-" + repeat(titleLength, "-")
                           + "-+-" + repeat(volumeLength, "-")
                           + "-+-" + repeat(authorLength, "-")
                           + "-+-" + repeat(priceLength, "-") + "-+");
    }
    private void print(ComicBook book) {
        List<ComicBook> singleBook = new ArrayList<>();
        singleBook.add(book);
        print(singleBook);
    }

    private void saveStore(File file) {
        System.out.println("Saving database ...");
        try (FileWriter fw = new FileWriter(file)) {
            int count = store.size();
            fw.write(count + "\n");

            for (ComicBook cb : store.allBooks()) {
                String id = String.format("%04d", cb.getId());
                String title = cb.getTitle();
                String volume = "" + cb.getVolume();
                String author = cb.getAuthor();
                String price = String.format("%.2f", cb.getBookRentalPrice());

                String format = id + "\n"
                                + title + "\n"
                                + price + "\n"
                                + author + "\n"
                                + volume + "\n";

                fw.write(format);
            }

            System.out.println("Finish saving database");
            print(store.allBooks());
        }
        catch (IOException e) {
            System.out.println("Unable to write to file!");
        }
    }


    private void search() {
        String query;
        System.out.print("Search Bar: ");
        query = sc.nextLine();

        List<ComicBook> result = store.search(query);

        if (result.isEmpty()) {
            System.out.println("No matched result!");
            return;
        }

        print(result);
    }

    private void searchByAuthor() {
        String name;
        System.out.print("Author's name (enter the whole name correctly): ");
        name = sc.nextLine();

        List<ComicBook> result = store.searchByAuthor(name);

        if (result.isEmpty()) {
            System.out.println("Author not found!");
            return;
        }

        print(result);
    }


    private boolean storeAvailable() {
        if (store.isEmpty()) {
            System.out.println("There is no books in the store! Please add some books first!");
            return false;
        }

        return true;
    }
}
