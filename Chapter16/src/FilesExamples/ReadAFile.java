package FilesExamples;
import java.io.*;

public class ReadAFile {
    // read happens by reading lines in a while loop, ending the lopp when the result of a 
    // readLine is null
    public static void main(String[] args) {
        try {
            File myFile = new File("MyText.txt");
            FileReader fileReader = new FileReader(myFile);

            // Goes back to file to read only when the buffer is empty (because the program)
            // has read everything in it
            BufferedReader reader = new BufferedReader(fileReader);

            // Make a String var to hold each line as the line is read
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
