// Spencer Lommel
// Mar 7th, 2025
import java.util.Scanner;

public class Input {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DatabaseAPI dbAPI = new DatabaseAPI();

    public static void userInput() {
        while (true) {
            int selection = displayMainMenu();
            switch (selection) {
                case 1 -> handleArtistsMenu();
                case 2 -> handlePlaylistsMenu();
                case 3 -> handleSongsMenu();
                case 4 -> handleAlbumsMenu();
                case 5 -> { System.out.println("Goodbye!"); return; }
            }
        }
    }

    private static void handleArtistsMenu() {
        while (true) {
            String artistName = stringInput("Enter artist name (or press ENTER to list all, or type 'back' to go back): ");
            if (artistName.equalsIgnoreCase("back")) return;

            if (artistName.isBlank()) {
                dbAPI.searchArtist("");
            } else {
                dbAPI.searchArtist(artistName);
            }

            int artistId = intInput("Enter Artist ID to view profile (or 0 to go back): ");
            if (artistId == 0) continue;

            dbAPI.getArtistAlbums(artistId);
            dbAPI.getArtistSongs(artistId);
        }
    }

    private static void handlePlaylistsMenu() {
        while (true) {
            int action = selectorList("What would you like to do?", "Create New Playlist", "Search Existing Playlist", "Back");
            if (action == 3) return;

            if (action == 1) {
                String newName = stringInput("Enter new playlist name: ");
                dbAPI.createPlaylist(newName);
                System.out.println("Playlist created.");
                continue;
            }

            // Search or List
            String playlistName = stringInput("Enter playlist name (or press ENTER to list all, or type 'back' to go back): ");
            if (playlistName.equalsIgnoreCase("back")) return;

            if (playlistName.isBlank()) {
                dbAPI.searchPlaylist(""); // Lists all playlists
            } else {
                dbAPI.searchPlaylist(playlistName); // Filtered search
            }

            int playlistId = intInput("Enter Playlist ID to manage (or 0 to go back): ");
            if (playlistId == 0) continue;

            managePlaylist(playlistId);
        }
    }


    private static void handleSongsMenu() {
        while (true) {
            String songName = stringInput("Enter song title (or press ENTER to list all, or type 'back' to go back): ");
            if (songName.equalsIgnoreCase("back")) return;

            if (songName.isBlank()) {
                dbAPI.displaySongsWithAlbumAndArtist(""); // List all songs with album/artist
            } else {
                dbAPI.displaySongsWithAlbumAndArtist(songName); // Filtered search
            }

            int songId = intInput("Enter Song ID to manage (or 0 to go back): ");
            if (songId == 0) continue;

            int action = selectorList("What would you like to do with this song?", "Add to Playlist", "View Artist", "Back");
            switch (action) {
                case 1 -> {
                    dbAPI.searchPlaylist("");  // List all playlists
                    int playlistId = intInput("Enter Playlist ID to add this song to (or 0 to cancel): ");
                    if (playlistId == 0) continue;
                    dbAPI.addSongToPlaylist(playlistId, songId);
                    System.out.println("Song added to playlist.");
                }
                case 2 -> {
                    int artistId = dbAPI.getArtistIdFromSong(songId);
                    if (artistId == -1) {
                        System.out.println("Artist not found for this song.");
                    } else {
                        System.out.println("Opening artist profile...");
                        dbAPI.getArtistAlbums(artistId);
                        dbAPI.getArtistSongs(artistId);
                    }
                }
                case 3 -> {
                    continue;
                }
            }

        }
    }


    private static void handleAlbumsMenu() {
        while (true) {
            String albumName = stringInput("Enter album name (or press ENTER to list all, or type 'back' to go back): ");
            if (albumName.equalsIgnoreCase("back")) return;

            if (albumName.isBlank()) {
                dbAPI.displayAlbumsWithArtist(""); // List all albums with artist name
            } else {
                dbAPI.displayAlbumsWithArtist(albumName); // Filtered search
            }

            int albumId = intInput("Enter Album ID to view songs (or 0 to go back): ");
            if (albumId == 0) continue;

            dbAPI.displaySongsInAlbumWithDetails(albumId);  // Show all songs with album/artist details
            dbAPI.getAlbumDuration(albumId);                // Show album duration

        }
    }

    private static void managePlaylist(int playlistId) {
        while (true) {
            int action = selectorList("Manage Playlist:", "View Songs", "Edit Name", "Delete Playlist", "Back");
            switch (action) {
                case 1 -> managePlaylistSongs(playlistId);

                case 2 -> {
                    String newName = stringInput("Enter new playlist name: ");
                    dbAPI.updatePlaylistName(playlistId, newName);
                    System.out.println("Playlist name updated.");
                }
                case 3 -> {
                    dbAPI.deletePlaylistById(playlistId);
                    System.out.println("Playlist deleted.");
                    return;
                }
                case 4 -> { return; }
            }
        }
    }


    private static int displayMainMenu() {
        return selectorList("Enter a number:", "Artists", "Playlists", "Songs", "Album", "Quit");
    }

    private static int selectorList(String title, String... options) {
        System.out.println(title);
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d: %s%n", i + 1, options[i]);
        }

        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number:");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static String stringInput(String prompt) {
        System.out.println(prompt);
        scanner.nextLine(); // Consume leftover newline
        return scanner.nextLine();
    }

    private static int intInput(String prompt) {
        System.out.println(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid integer:");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void managePlaylistSongs(int playlistId) {
        while (true) {
            dbAPI.displaySongsInPlaylistWithDetails(playlistId);
            dbAPI.getPlaylistDuration(playlistId);

            int songId = intInput("Enter Song ID to remove (or 0 to go back): ");
            if (songId == 0) return;

            dbAPI.removeSongFromPlaylist(playlistId, songId);
            System.out.println("Song removed from playlist.");
        }
    }

}
