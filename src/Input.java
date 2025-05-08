// Spencer Lommel
// Mar 7th, 2025
import java.util.Scanner;

public class Input {

    public static void displayOptionsList() {
        int selection = selectorList("Enter a number:", "Artists", "Playlists", "Quit");
        System.out.printf("You selected: %d", selection);


    }


    // this function allows you to pass in a title and options and returns an int based on what you selected
    // NOTE: we return a base index 1 selection. So if a user selects the first option we return 1 instead of 0
    private static int selectorList(String title, String... options) {
        Scanner scanner = new Scanner(System.in);

        // print title
        System.out.println(title);
        // print all the options from the method
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d: %s%n", i+1, options[i]);
        }

        int valueSelection;
        while (true) {
            if (scanner.hasNextInt()) {
                valueSelection = scanner.nextInt();
                if (valueSelection >= 1 && valueSelection < options.length) {
                    return valueSelection;
                } else {
                    System.out.printf("Please input an integer between 1 and %d:\n", options.length);
                    for (int i = 0; i < options.length; i++) {
                        System.out.printf("%d: %s%n", i+1, options[i]);
                    }
                }

            } else {
                scanner.next(); // gets rid of non int inputs
                System.out.printf("Please input an integer between 1 and %d:\n", options.length);
                for (int i = 0; i < options.length; i++) {
                    System.out.printf("%d: %s%n", i+1, options[i]);
                }
            }
        }
    }

}
