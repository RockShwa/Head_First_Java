import java.util.*;
public class ArtistCompare implements Comparator<SongV2> {
    public int compare(SongV2 one, SongV2 two) {
        //Lets the String compareTo method do the work, since it know the proper way to sort the artists
        return one.getArtist().compareTo(two.getArtist());
    }
}