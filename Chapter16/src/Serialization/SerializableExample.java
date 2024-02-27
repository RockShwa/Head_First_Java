package Serialization;
import java.io.*;

public class SerializableExample implements Serializable{
    private int myWidth;
    private int myHeight;

    // These two values will be saved 
    public SerializableExample(int width, int height) {
        myWidth = width;
        myHeight = height;
    }

    public int getWidth() {
        return myWidth;
    }

    public int getHeight() {
        return myHeight;
    }

    public static void main(String[] args) {
        SerializableExample myExample = new SerializableExample(50, 20);

        try { // I/O Operations can throw exceptions
            FileOutputStream fs = new FileOutputStream("foo.ser");
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(myExample);
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
