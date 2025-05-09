public class Artist {
    private int artist_id;
    private String name;


    public Artist(int a_id, String name) {
        this.artist_id = a_id;
        this.name = name;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public String getName() {
        return name;
    }

    public void setArtist_id(int a_id) {
        this.artist_id = a_id;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String insert(String name) {
//        return ("INSERT INTO artist (name) VALUES ('" + name + "');");
//    }
//
//    public String delete(int artist_id) {
//        return ("DELETE FROM artist WHERE artist_id = " + artist_id + ";");
//    }
//
//    public String update() {
//        return ("UPDATE artist SET name = '" + name + "' WHERE artist_id = " + artist_id + ";");
//    }
}

