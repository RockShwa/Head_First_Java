import java.util.*;
public class JukeBox1 {
    public void go() {
        List<SongV2> songList = MockSongs.getSongsV2();
        System.out.println(songList);

        // Sorts by title; implements Comparable
        Collections.sort(songList); //Utilizes overriden compareTo() method in SongV2
        System.out.println(songList);

        // Sorts by artist
        // Uses a CUSTOM Comparator to sort
        // Makes an instance of the Comparator class and passes that to sort, so it knows how to sort
        ArtistCompare artistCompare = new ArtistCompare();
        songList.sort((one, two) -> one.getArtist().compareTo(two.getArtist())); //Same, but a lambda
        songList.sort(artistCompare); //Automatically calls the compare method :)
        System.out.println(songList);

        // Lambdas Example
        // Further explanation in TitleCompare
        TitleCompare titleCompare = new TitleCompare();
        songList.sort(titleCompare); 
        System.out.println(songList);
        // THIS IS THE SAME EXACT THING 
        songList.sort((one, two) -> one.getTitle().compareTo(two.getTitle()));

        Set<SongV2> songSet = new HashSet<>(songList);
        System.out.println(songSet);

        Set<SongV2> songSet2 = new TreeSet<>(songList);
        // This is with a lambda
        // Set<SongV2> songSet2 = new TreeSet<>((o1, o2) -> o1.getBpm() - o2.getBpm());
        System.out.println(songSet2); //uses SongV2's compareTo() method to sort
    }
    public static void main(String[] args) {
        new JukeBox1().go();
    }
}