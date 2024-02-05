import java.util.*;
public class JukeBox1 {
    public void go() {
        List<SongV2> songList = MockSongs.getSongsV2();
        System.out.println(songList);

        Collections.sort(songList);
        System.out.println(songList);
    }
    public static void main(String[] args) {
        new JukeBox1().go();
    }
}