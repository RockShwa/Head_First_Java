import java.util.*;
public class StreamExamples {
    // List does not implement Stream, but the Collection interface has the stream method,
    // which returns a Stream object for the Collection

    List<String> strings = List.of("I", "am", "a", "list", "of", "Strings");
    Stream<String> stream = strings.stream(); //Returns a Stream of these Strings

    Stream<String> limit = stream.limit(4); // Max # of results is four

    List<String> result = limit.collect(Collectors.toList());
    System.out.println("result = " + result);

    // Stream Pipeline (Chained), indent for clarity
    List<String> result = string.stream()
                                // Method from the String class that compares a String to another String, ignoring upper/lower cases
                                .sorted((s1, s2) -> s1.compareToIgnoreCase(s2)) 
                                .skip(2) //skips first two elements
                                .limit(4)
                                .collect(Collectors.toList());
    System.out.println("result = " + result);
}