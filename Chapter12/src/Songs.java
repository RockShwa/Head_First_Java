import java.util.*;
import java.util.stream.*;

public class Songs {
    public List<Song> getSongs() {
        List<Song> songs = new ArrayList<>();
        songs.add(new Song("$10", "Hitchhiker", "Electronic", 2016, 183));
        songs.add(new Song("Havana", "Camila Cabello", "R&B", 2017, 324));
        songs.add(new Song("Cassidy", "Grateful Dead", "Rock", 1972, 123));
        songs.add(new Song("50 ways", "Paul Simon", "Soft Rock", 1975, 199));
        songs.add(new Song("Hurt", "Nine Inch Nails", "Industrial Rock", 1995, 257));
        songs.add(new Song("Silence", "Delerium", "Electronic", 1999, 134));
        songs.add(new Song("Hurt", "Johnny Cash", "Soft Rock", 2002, 392));
        songs.add(new Song("Watercolour", "Pendulum", "Electronic", 2010, 155));
        songs.add(new Song("The Outsider", "A Perfect Circle", "Alternative Rock", 2004, 312));
        songs.add(new Song("With a Little Help from My Friends", "The Beatles", "Rock", 1968, 173));
        songs.add(new Song("Come Together", "The Beatles", "Blues rock", 1968, 173));
        songs.add(new Song("Come Together", "Ike & Tina Turner", "Rock", 1970, 165));
        songs.add(new Song("With a Little Help from My Friends", "Joe Cocker", "Rock", 1968, 46));
        songs.add(new Song("Immigrant Song", "Karen O", "Industrial Rock", 2011, 12));
        songs.add(new Song("Breathe", "The Prodigy", "Electronic", 1996, 337));
        songs.add(new Song("What's Going On", "Gaye", "R&B", 1971, 420));
        songs.add(new Song("Hallucinate", "Dua Lipa", "Pop", 2020, 75));
        songs.add(new Song("Walk Me Home", "P!nk", "Pop", 2019, 459));
        songs.add(new Song("I am not a woman, I'm a god", "Halsey", "Alternative Rock", 2021, 384));
        songs.add(new Song("Pasos de cero", "Pablo Alborán", "Latin", 2014, 117));
        songs.add(new Song("Smooth", "Santana", "Latin", 1999, 244));
        songs.add(new Song("Immigrant song", "Led Zeppelin", "Rock", 1970, 484));
        return songs;
    }
}