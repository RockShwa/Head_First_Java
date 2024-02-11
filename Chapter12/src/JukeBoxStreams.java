import java.util.*;
import java.util.stream.*;

public class JukeBoxStreams {
    public static void main(String[] args) {
        
        // Goal is to filter the rock genre songs and collect them into a new list
        List<Song> songs = new Songs().getSongs();
        List<Song> rockSongs = songs.stream()
                                    .filter(song -> song.getGenre().equals("Rock")) //This lambda implements Predicate
                                    .collect(Collectors.toList());
        System.out.println(rockSongs);

        // Filters if first letter is H
        List<Song> hSongs = songs.stream()
                                    .filter(song -> song.getTitle().substring(0,1).equals("H"))
                                    .collect(Collectors.toList());
        System.out.println(hSongs);

        // Filters if more recent than 1995
        List<Song> yearSongs = songs.stream()
                                    .filter(song -> song.getYear() > 1995)
                                    .collect(Collectors.toList());
        System.out.println(yearSongs);

        // Goal #2: Turn the song elements in the Stream into genre (String) elements
        List<String> genres = songs.stream()
        // By calling getGenre on the song, the stream after this point will be made of Strings
                                   .map(song -> song.getGenre()) 
                                   .distinct()
                                   .collect(Collectors.toList()); //Puts results into a List
        System.out.println(genres);
    
        
    }
}