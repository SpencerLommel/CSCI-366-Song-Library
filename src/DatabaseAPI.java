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

    // searchSong(String song_name)
    public void searchSong(String searchName) {
        searchTable("song", "title", searchName);
    }


    // ====================================
    //  Playlist Methods
    // ====================================

    // searchPlaylist(String playlist_name)
    public void searchPlaylist(String playlistName) {
        searchTable("playlist", "name", playlistName);
    }

    // create playlist (playlist_name)
    public void createPlaylist(String playlistName) {
        try(Statement s = connection.createStatement()) {
            String query = "INSERT INTO playlist (name) VALUES ('" + playlistName + "')";
            int count = s.executeUpdate(query);
        } catch (SQLException e) {
        }
    }

    // delete playlist
    public void deletePlaylist(String playlistName){
        deleteByValue("playlist", "name", playlistName );
    }

    // add song to playlist (playlist_id, song_id)

    // remove song from playlist (playlist_id, song_id)


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

    // ====================================
    //  Album Methods
    // ====================================

    // search Album
    public void searchAlbum(String albumName) {
        searchTable("album", "title", albumName);
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
    private void runQueryToTable(String query) {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

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
