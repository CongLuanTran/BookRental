package bookrental;

import java.io.File;
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
        File file = new File(filePath);
        ComicBookStore store = new ComicBookStore();
        try (Scanner sc = new Scanner(System.in)) {
            TextUI ui = new TextUI(store, sc, file);
            ui.start();
        }
    }
}
