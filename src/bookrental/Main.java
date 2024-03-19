package bookrental;

import java.util.Scanner;

/**
 *
 * @author tranc
 */
public class Main {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        String filePath = "ComicBooks.txt";
        ComicBookStore store = new ComicBookStore();
        Scanner sc = new Scanner(System.in);
        TextUI ui = new TextUI(store, sc, filePath);
        ui.start();
    }
}
