/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookrental;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author tranc
 */
public class TextUI {

    private static String strip(String string) {
        return string.substring(4);
    }

    private ComicBookStore store;
    private Scanner sc;

    private String filePath;

    public TextUI(ComicBookStore store, Scanner sc, String filePath) {
        this.store = store;
        this.sc = sc;
        this.filePath = filePath;
    }

    public void start() {
        System.out.println("Loading book store database ...");
        loadStore(filePath);
        System.out.println("Finish loading database!");
        while (true) {
            menu();
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    addBook();
                    break;
                case "2":
                    search();
                    break;
                case "3":
                    searchByAuthor();
                    break;
                case "4":
                    changePrice();
                    break;
                case "5":
                    deleteBook();
                    break;
                case "6":
                    System.out.println("Saving database ...");
                    saveStore(filePath);
                    System.out.println("Finish saving database");
                    System.out.println("Thank you, and goodbye!");
                    return;
                default:
                    System.out.println("Invalid input, please try again!");
            }
        }
    }

    public void deleteBook() {
        print(store.allBooks());
        System.out.print("Which book do you want to delete? ");
        int id = Integer.parseInt(sc.nextLine());

        try {
            store.removeBook(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

    private void loadStore(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int count = Integer.parseInt(br.readLine());

            for (int i = 0; i < count; i++) {
                int id = Integer.parseInt(strip(br.readLine()));
                String title = strip(br.readLine());
                double price = Double.parseDouble(strip(br.readLine()));
                String author = strip(br.readLine());
                int volume = Integer.parseInt(strip(br.readLine()));
                store.addBook(id, title, price, author, volume);
                ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void saveStore(String filePath) {
        try (FileWriter fw = new FileWriter(filePath)) {
            int count = store.size();
            fw.write(count + "\n");

            for (ComicBook cb : store.allBooks()) {
                String id = String.format("%04d", cb.getId());
                String title = cb.getTitle();
                String volume = "" + cb.getVolume();
                String author = cb.getAuthor();
                String price = String.format("%.2f", cb.getBookRentalPrice());

                String format = "#1. " + id + "\n"
                        + "#2. " + title + "\n"
                        + "#3. " + price + "\n"
                        + "#4. " + author + "\n"
                        + "#5. " + volume + "\n";

                fw.write(format);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addBook() {
        String title, author;
        int volume;
        double price;

        while (true) {
            System.out.print("Enter book's title (or 0 to return): ");
            title = sc.nextLine().trim();
            if (title.equals("0")) {
                return;
            }

            System.out.print("Enter volume (or 0 to return): ");
            volume = Integer.parseInt(sc.nextLine());
            if (volume == 0) {
                return;
            }

            if (store.containsBook(title, volume)) {
                System.out.println("Book already existed!");
                continue;
            }

            break;
        }

        System.out.print("Enter author (or 0 to return): ");
        author = sc.nextLine().trim();
        if (author.equals("0")) {
            return;
        }

        System.out.print("Enter rental price (or 0 to return): ");
        price = Double.parseDouble(sc.nextLine());
        if (price == 0) {
            return;
        }

        try {
            store.addBook(title, price, author, volume);
            ;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        print(store.allBooks());
    }

    private void search() {
        String query;
        System.out.print("Search Bar: ");
        query = sc.nextLine();

        List<ComicBook> result = store.search(query);
        print(result);
    }

    private void searchByAuthor() {
        String name;
        System.out.print("Author's name (enter the whole name correctly): ");
        name = sc.nextLine();

        List<ComicBook> result = store.searchByAuthor(name);
        print(result);
    }

    private void changePrice() {
        int id;
        System.out.print("Enter the id of the book: ");
        id = Integer.parseInt(sc.nextLine());

        ComicBook cb = store.getBook(id);
        while (true) {
            try {
                double price;
                System.out.print("Enter a new price (or 0 to return): ");
                price = Double.parseDouble(sc.nextLine());

                if (price == 0) {
                    return;
                }

                cb.setBookRentalPrice(price);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void print(List<ComicBook> books) {
        System.out
                .println("+------+------------------------------------------+--------+----------------------+-------+");
        System.out
                .println("|  ID  | Book                                     | Volume | Author               | Price |");
        System.out
                .println("+------+------------------------------------------+--------+----------------------+-------+");
        for (ComicBook cb : books) {
            String id = String.format("%04d", cb.getId());
            String title = String.format("%-40.40s", cb.getTitle());
            String volume = String.format("%6d", cb.getVolume());
            String author = String.format("%-20.20s", cb.getAuthor());
            String price = String.format("%5.2f", cb.getBookRentalPrice());

            System.out.println("| " + id + " | " + title + " | " + volume + " | " + author + " | " + price + " |");
        }
        System.out
                .println("+------+------------------------------------------+--------+----------------------+-------+");
    }
}
