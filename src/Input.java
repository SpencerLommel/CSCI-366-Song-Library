// Spencer Lommel
// Mar 7th, 2025
import java.util.Scanner;

public class Input {

    public static void userInput() {
        DatabaseAPI dbAPI = new DatabaseAPI();

        // we start with outputting the main dialog window and then most stuff is based off this value
        int selection = displayMainMenu();
        System.out.println(selection);

        switch (selection) {
            case 1:
                System.out.println("You entered 1");
                String artistName = stringInput("Enter artist name: ");
                dbAPI.searchArtist(artistName);

            case 2:
                String playlistName = stringInput("Enter playlist name: ");
                dbAPI.deletePlaylist(playlistName);
        }


    }


    public static int displayArtistsMenu() {
        String artist = stringInput("Enter an artist: ");

        return 0;

    }

    public static int displayMainMenu() {
        return selectorList("Enter a number:", "Artists", "Playlists", "Quit");

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

    private static String stringInput(String title) {
        System.out.println(title);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
