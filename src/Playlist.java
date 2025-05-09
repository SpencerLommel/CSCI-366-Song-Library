public class Playlist {

    private int playlist_id;
    private String name;
    private int user_id;


    public Playlist(int playlist_id, String name, int user_id) {
        this.playlist_id = playlist_id;
        this.name = name;
        this.user_id = user_id;
    }

    public int getPlaylist_id()
    {
        return playlist_id;
    }

    public String getName()
    {
        return name;
    }

    public int getUser_id()
    {
        return user_id;
    }

    public void setPlaylist_id(int id)
    {
        this.playlist_id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setUser_id(int userId)
    {
        this.user_id = userId;
    }


    public void getTotalDuration()
    {

    }

}
