import java.sql.*;
import java.util.ArrayList;

public class DatabaseAPI {
    private final Connection connection;

    public DatabaseAPI() {
        DatabaseConnection dbConn = new DatabaseConnection();
        this.connection = dbConn.getConnection();
    }

    // ====================================
    //  Song Methods
    // ====================================

    public void displaySongsWithAlbumAndArtist(String songSearch) {
        String query = """
    SELECT s.song_id, s.title, s.release_year, al.title AS album_title, ar.name AS artist_name, s.duration
    FROM song s
    JOIN album al ON s.album_id = al.album_id
    JOIN artist ar ON s.artist_id = ar.artist_id
    WHERE s.title ILIKE ?
    """;
        runQueryToTable(query, "%" + songSearch + "%");
    }


    public int getArtistIdFromSong(int songId) {
        String query = "SELECT artist_id FROM song WHERE song_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, songId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("artist_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Not found
    }


    // searchSong(String song_name)
    public void searchSong(String searchName) {
        searchTable("song", "title", searchName);
    }


    // ====================================
    //  Playlist Methods
    // ====================================

    public void deletePlaylistById(int playlistId) {
        String query = "DELETE FROM playlist WHERE playlist_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, playlistId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void displaySongsInPlaylistWithDetails(int playlistID) {
        String query = """
    SELECT s.song_id, s.title, s.release_year, al.title AS album_title, ar.name AS artist_name, s.duration
    FROM playlist_song ps
    JOIN song s ON ps.song_id = s.song_id
    JOIN album al ON s.album_id = al.album_id
    JOIN artist ar ON s.artist_id = ar.artist_id
    WHERE ps.playlist_id = ?
    """;
        runQueryToTable(query, playlistID);
    }


    // searchPlaylist(String playlist_name)
    public void searchPlaylist(String playlistName) {
        searchTable("playlist", "name", playlistName);
    }

    // create playlist (playlist_name) [using Statement]
//    public void createPlaylist(String playlistName) {
//        try(Statement s = connection.createStatement()) {
//            String query = "INSERT INTO playlist (name) VALUES ('" + playlistName + "')";
//            int count = s.executeUpdate(query);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    // createPlaylist playlist (playlistName) [using PreparedStatement]
    public void createPlaylist(String playlistName) {
        String query = "INSERT INTO playlist (name) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, playlistName);
            int count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // delete playlist
    public void deletePlaylist(String playlistName){
        deleteByValue("playlist", "name", playlistName );
    }

    // add song to playlist (playlist_id, song_id) [using Statement]
//    public void addSongToPlaylist(int p_id, int s_id) {
//        try(Statement s = connection.createStatement()) {
//            String query = "INSERT INTO playlist_song (playlist_id, song_id) VALUES ('" + p_id + ", " + s_id +  "')";
//            int count = s.executeUpdate(query);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    // add song to playlist (playlist_id, song_id) [using PreparedStatement]
    public void addSongToPlaylist(int p_id, int s_id) {
        String query = "INSERT INTO playlist_song (playlist_id, song_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, p_id);
            ps.setInt(2, s_id);
            int count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // remove song from playlist (playlist_id, song_id) [using Statement]
//    public void removeSongFromPlaylist(int p_id, int s_id) {
//        try(Statement s = connection.createStatement()) {
//            String query = "DELETE FROM playlist_song WHERE playlist_id = " + p_id + " AND song_id = " + s_id;
//            int count = s.executeUpdate(query);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    // remove song from playlist (playlist_id, song_id) [using PreparedStatement]
    public void removeSongFromPlaylist(int p_id, int s_id) {
        String query = "DELETE FROM playlist_song WHERE playlist_id = ? AND song_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, p_id);
            ps.setInt(2, s_id);
            int count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //get Playlist Length
    public void getPlaylistDuration(int playlistID) {
        String query = """
        SELECT SUM(s.duration) AS total_duration
        FROM playlist_song ps
        JOIN song s ON ps.song_id = s.song_id
        WHERE ps.playlist_id = ?
        """;
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, playlistID);


            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int totalDuration = rs.getInt("total_duration");
                    System.out.println("Total duration for playlist ID " + playlistID + " is: " + totalDuration + " seconds.");
                } else {
                    System.out.println("No songs found for playlist ID " + playlistID + ".");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //update playlist name
    public void updatePlaylistName(int playlistID, String newName) {
        String query = "UPDATE playlist SET name = ? WHERE playlist_id = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, newName);
            ps.setInt(2, playlistID);
            int count = ps.executeUpdate();
            if (count == 0) {
                System.out.println("No playlist found with ID: " + playlistID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //get all songs on a Playlist
    public void getSongsInPlaylist(int playlistID) {
        String query = """
        SELECT s.*
        FROM playlist_song ps
        JOIN song s ON ps.song_id = s.song_id
        WHERE ps.playlist_id = ?
        """;
        runQueryToTable(query, playlistID);
    }
    // ====================================
    //  Artist Methods
    // ====================================

    // search Artist
    public void searchArtist(String artistName) {
        searchTable("artist", "name", artistName);
    }



    // get Artist Albums
    public void getArtistAlbums(int artistID) {
        String query = """
        SELECT al.*, ar.*
        FROM "album" al
        JOIN "artist" ar ON ar.artist_id = al.artist_id
        WHERE ar.artist_id = %d
        """.formatted(artistID);

        runQueryToTable(query);
    }



    // get Artist Songs
    public void getArtistSongs(int artistID) {
        String query = """
        SELECT so.*, ar.*
        FROM "song" so
        JOIN "artist" ar ON ar.artist_id = so.artist_id
        WHERE ar.artist_id = %d
        """.formatted(artistID);

        runQueryToTable(query);
    }

    // ====================================
    //  Album Methods
    // ====================================

    public void displaySongsInAlbumWithDetails(int albumID) {
        String query = """
    SELECT s.song_id, s.title, s.release_year, al.title AS album_title, ar.name AS artist_name, s.duration
    FROM song s
    JOIN album al ON s.album_id = al.album_id
    JOIN artist ar ON s.artist_id = ar.artist_id
    WHERE s.album_id = ?
    """;
        runQueryToTable(query, albumID);
    }


    public void displayAlbumsWithArtist(String albumSearch) {
        String query = """
    SELECT al.album_id, al.title, al.release_year, ar.name AS artist_name
    FROM album al
    JOIN artist ar ON al.artist_id = ar.artist_id
    WHERE al.title ILIKE ?
    """;
        runQueryToTable(query, "%" + albumSearch + "%");
    }


    // search Album
    public void searchAlbum(String albumName) {
        searchTable("album", "title", albumName);
    }

    // get Album Duration
    public void getAlbumDuration(int albumID) {
        String query = """
    SELECT SUM(s.duration) AS total_duration
    FROM song s
    WHERE s.album_id = ?
    """;
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, albumID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int totalDuration = rs.getInt("total_duration");
                    System.out.println("Total duration for album ID " + albumID + " is: " + totalDuration + " seconds.");
                } else {
                    System.out.println("No songs found for album ID " + albumID + ".");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void getSongsInAlbum(int albumID) {
        runQueryToTable("SELECT * FROM song WHERE album_id = ?", albumID);
    }


    // ====================================
    //  Helper methods
    // ====================================

    // prints a nice formatted table :-----)
    private void printTable(String[] headers, String[][] rows) {
        // Print headers
        for (String header : headers) {
            System.out.printf("%-20s", header);
        }
        System.out.println();

        // Print separator
        for (int i = 0; i < headers.length; i++) {
            System.out.print("--------------------");
        }
        System.out.println();

        // Print rows
        for (String[] row : rows) {
            for (String cell : row) {
                System.out.printf("%-20s", cell);
            }
            System.out.println();
        }
    }

    // This runs a query and outputs a nice table
    // Here's some examples of the usage
    // runQueryToTable("SELECT * FROM song");
    // or to use a parameter you can do
    // runQueryToTable("SELECT * FROM song WHERE artist_id = ?", 1);
    private void runQueryToTable(String query, Object... params) {
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            // Bind parameters if provided
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                ResultSetMetaData meta = rs.getMetaData();
                int columnCount = meta.getColumnCount();

                // Collect headers
                String[] headers = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    headers[i - 1] = meta.getColumnName(i);
                }

                // Collect rows
                ArrayList<String[]> rowsList = new ArrayList<>();
                while (rs.next()) {
                    String[] row = new String[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = rs.getString(i);
                    }
                    rowsList.add(row);
                }

                String[][] rows = rowsList.toArray(new String[0][]);
                printTable(headers, rows);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // generic searchTable where you can pass a table name a search param
    public void searchTable(String tableName, String columnName, String searchParam) {
        String query = "SELECT * FROM \"" + tableName + "\" WHERE \"" + columnName + "\" ILIKE '%" + searchParam + "%'";
        runQueryToTable(query);
    }


    public void deleteByValue(String tableName, String columnName, String valueToDelete) {
        String query = "DELETE FROM \"" + tableName + "\" WHERE \"" + columnName + "\" = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, valueToDelete);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Record(s) deleted successfully from table '" + tableName + "'.");
            } else {
                System.out.println("No matching record found in table '" + tableName + "'.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
