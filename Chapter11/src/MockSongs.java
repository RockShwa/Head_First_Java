import java.util.*;
public class MockSongs {
    public static List<SongV2> getSongsV2() {
        /* Using the interface List here becuase the code dosen't 
        * need to know it's an ArrayList, and it can be helpful to use the interface, 
        not the implementation */
        List<SongV2> songs = new ArrayList<>();
        songs.add(new SongV2("somersault", "zero 7", 147));
        songs.add(new SongV2("cassidy", "grateful dead", 158));
        songs.add(new SongV2("$10", "hitchhiker", 140));

        songs.add(new SongV2("havana", "cabello", 105));
        songs.add(new SongV2("$10", "hitchhiker", 140));
        songs.add(new SongV2("Cassidy", "grateful dead", 158));
        songs.add(new SongV2("50 Ways", "simon", 102));
        return songs;
    }
}