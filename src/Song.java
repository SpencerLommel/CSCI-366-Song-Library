public class Song {
//this is my test

    private int song_id;
    private String title;
    private int release_year;
    private int album_id;
    private int artist_id;
    private float length;

    public Song(int song_id, String title, int release_year, int album_id, int artist_id, float length)
    {
        this.song_id = song_id;
        this.title = title;
        this.release_year = release_year;
        this.album_id = album_id;
        this.artist_id = artist_id;
        this.length = length;
    }


    public int getSong_id() {
        return song_id;
    }

    public String getTitle() {
        return title;
    }

    public int getRelease_year() {
        return release_year;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public float getLength() {
        return length;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public void setAlbum_id(int album_id)
    {
        this.album_id = album_id;
    }

    public void setArtist_id(int artist_id)
    {
        this.artist_id = artist_id;
    }

    public void setLength(float length)
    {
        this.length = length;
    }

}
