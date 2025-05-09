public class Album {

    private int album_id;
    private String title;
    private int release_year;
    private int artist_id;

    public Album(int album_id, String title, int release_year, int artist_id) {
        this.album_id = album_id;
        this.title = title;
        this.release_year = release_year;
        this.artist_id = artist_id;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public String getTitle() {
        return title;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public int getRelease_year() {
        return release_year;
    }

    public void setAlbum_id(int a_id) {
        this.album_id = a_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }
}
