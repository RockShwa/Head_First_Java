import java.awt.Color;

public class Hippo extends Animal{

    private Color color;
    
    //This is the real constructor that does the real work of initing the object
    public Hippo (String name, Color c) {
        super(name);
        color = c;
    }

    public Hippo() { //No-arg constructor supplies default Color and calls overloaded constructor
        this("Matembo", Color.RED);
    }

    // Other hippo specific methods
}