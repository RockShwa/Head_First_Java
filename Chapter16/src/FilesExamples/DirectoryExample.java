package FilesExamples;
import java.nio.file.*;

public class DirectoryExample {
    public static void main(String[] args) {
        // A Path object represents the location (name and path) of a file or
        // directory on disk, but does not give access to the data in the file
        try {
            Path myPath = Paths.get("MyApp");
            Path myPath2 = Paths.get("MyApp", "media");
            Path myPath3 = Paths.get("MyApp", "source");
            Path mySource = Paths.get("MyApp.class");
            Path myMedia = Paths.get("MyMedia.jpeg");

            Files.createDirectory(myPath);
            Files.createDirectory(myPath2);
            Files.createDirectory(myPath3);
            // Move the two files to their respective locations
            Files.move(mySource, myPath3.resolve(mySource.getFileName()));
            Files.move(myMedia, myPath2.resolve(myMedia.getFileName()));
        } catch (Exception e) {
            System.out.println("Got an NIO Exception" + e.getMessage());
        }
    }
}
