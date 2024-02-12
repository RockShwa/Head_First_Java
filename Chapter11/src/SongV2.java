public class SongV2 implements Comparable<SongV2> {
    private String title;
    private String artist;
    private int bpm;

    public int compareTo (SongV2 s) {
        return title.compareTo(s.getTitle());
    }

    @Override
    public boolean equals(Object aSong) {
        SongV2 other = (SongV2) aSong;
        return title.equals(other.getTitle()); //Objects will be equal if titles are the same
    }

    @Override
    public int hashCode() {
        return title.hashCode(); //String has an overriden hashCode() method 
    }

    public SongV2 (String title, String artist, int bpm) {
        this.title = title;
        this.artist = artist;
        this.bpm = bpm;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getBpm() {
        return bpm;
    }

    @Override
    public String toString() {
        return title;
    }
}