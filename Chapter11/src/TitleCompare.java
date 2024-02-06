import java.util.*;
public class TitleCompare implements Comparator<SongV2> { //Compiler can infer this from sort() docs
    public int compare(SongV2 one, SongV2 two) { // Compiler can infer that these are SongV2 bc those are the contents of the collection
        return one.getTitle().compareTo(two.getTitle()); // This is the ONLY thing the compiler really needs
    }
}