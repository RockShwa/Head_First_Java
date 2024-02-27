package FilesExamples;
import java.io.*;
public class WriteAFile {
    public static void main(String[] args) {
        // ALL I/O must be in a try/catch, everything can throw an I/O exception
        try {
            FileWriter writer = new FileWriter("Foo.txt");

            writer.write("hello foo!");

            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}