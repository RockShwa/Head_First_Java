# Serialization and File I/O - Saving Obejcts (and Text)

// TODO: FIX THE BEATBOX AND FIGURE OUT THE JFILECHOOSER

- Objects can be flattened and inflated; Objects have state and behavior
    - Behavior lives in the class, but state lives within each individual object
- I/O: input/output

## Options for Saving State
1) Use serialization: write a file that holds flattened (serialized) objects. Then have your program read the serialized objects from the file and inflate them back to living, breathing, heap-inhabiting objects
    - The serialization file is much harder for humans to read, but its easier (and safer) for your program to restore objects from serialization
2) Write a plain-text file: write a file, wuith delimiters that other programs can parse. For example, a tab-delimited file that a spreadsheet or database application can use
    - Easier for humans to write, but less reliable for your computer

## Fundamental I/O Techniques
- This is when you're NOT using an actual database 
- Input: Write some data to something, ususally a file on a disk or a stream from a network connection
- Output: Read some data from either a file on a disk or a stream from a network connection

## Writing a Serialized Object to a File
1) Make a Serialized Object to a File
~~~ java
// knows how to connect to and create a file
FileOutputStream fileStream = new FileOutputStream("MyGame.ser");
~~~
2) Make an ObjectOutputStream
~~~ java
// lets you write objects, but it can't directly connect to a file. Needs to be fed a helper (called) chaining one stream to another
ObjectOutputStream os = new ObjectOutputStream(fileStream);
~~~
3) Write the Object
~~~ java
// serializes the objects referenced by characterOne, characterTwo, and characterThree and writes them IN ORDER to the file "myGame.ser"
os.writeObject(characterOne);
os.writeObject(characterTwo);
os.writeObject(characterThree);
~~~
4) Close the ObjectOutputStream
~~~ java
// closing the stream at the top closes the ones underneath, so the FileOutputStream (and the file) will close automatically
os.close(); 
~~~

### Data and Streams
- Connection Streams represent a connection to a source or destination (file, network socket, etc.)
    - usually pretty low level, has methods for writing bytes
- Chain Streams can NOT connect on their own and must be chained to a connection stream
    - higher level, has methods for writing objects, but must be chained to a connection stream to dp useful things
- Object is written to an ObjectOutputStream (chain stream) and serialized, which is chained to a FileOutputStream (connection) and written as bytes, whcih is put into a file destination

## Serialization
1) Obejcts on the Heap
    - have a state -> value of the object's instance variables; these values make one instance of a class different from another instance of the same class
2) Object Serialized
    - Serialized objects save the values of the instance variables so that an identical instance (object) can be brought back to life on the heap
- Example:
~~~ java
// Object on the Heap
Foo myFoo = new Foo();
myFoo.setWidth(37);
myFoo.setHeight(70);

// Serialized Object
FileOutputStream fs = new FileOutputStream("foo.ser");
ObjectOutputStream os = new ObjectOutputStream(fs);
os.writeObject(myFoo);
~~~
- When an object is serialized, all the objects it refers to from instance variables are also serialized. And all the objects those objects refer to are serialized... and so on. Saves the entire object graph (all objects referenced by instance variables, starting with the object being serialized)
- If you want your class to be serializable, implement Serializable
    - known as the marker or tag interface, because the interface doesn't have any methods to implement. It's sole purpose is to declare something as serializable
    - in the java.io.*; package
- Serialization is all or nothing, either everything is able to be serialized, or the entire operation fails
- You can mark an instance variable as **transient** if it can't or shouldn't be saved. Some things that depend on runtime specific information can't be saved; once you bring the object back to life, a transient reference instance variable will be given the automatic value null (which means the object graph connected to this variable is lost)
- Static variables are not serialized

## Deserialization
- Restoring an Object:
1) Make a FileInputStream
~~~ java
FileInputStream fileStream = new FileInputStream("MyGame.ser");
~~~
2) Make an ObjectInputStream
~~~ java
ObjectInputStream os = new ObjectInputStream(fileStream);
~~~
3) Read the Objects
~~~ java
Object one = os.readObject(); // gets the next object in the stream, in the order they were written
Object two = os.readObject();
Object three = os.readObject();
~~~
4) Cast the Objects
~~~ java
// return value of readObject() is an Object, so you have to cast it
GameCharacter elf = (GameCharacter) one;
GameCharacter troll = (GameCharacter) two;
GameCharacter magician = (GameCharacter) three;
~~~
5) Close the ObjectInputStream
~~~ java
os.close();
~~~
- When an object is deserialized, JVM attempts to bring the object back to lifeby making a new object on the heap that has the same state the serialized object had at the time it was serialized
- The Process:
1) Object is read from the stream,
2) The JVM determines the object's class type 
3) JVM attempts to find and load the object's class type (if it can't find/load the class, the JVM throws an exception and deserializtion fails)
4) A new object is given space on the heap, but **the serialized object's constructor does NOT run**
5) If object has a non-serializable class somewhere up its inheritance tree, **the constructor for that non-serializable class will run** along with any other constructors above that, even if they're serializable
6) The object's instance variables are given the values from the serialized state. Transient variable are given a calue of null for object references and defaults for primitives

## Version ID
- Version Control is crucial: if you serialize an object, you must have the class in order to deserialize and use the object
- **Changes to a class that can hurt deserialization:**
    - Deleting an instance variable
    - Changing the declared type of an instance variable
    - Changing a non-transient instance variable to transient
    - Moving a class up or down the inheritance heirarchy
    - Changing a class (anywhere in the object graph) from Serializable to not Serializable 
    - Changing an instance variable to static
- **Changes to a class that are usually okay:**
    - Adding new instance variables to the class (existing objects will deserialize with default values for the instance variables they did not have when they were serialized)
    - Adding classes to the inheritance tree
    - Removing classes from the inheritance tree
    - Changing the access level (public, private, etc.) of an instance variable has no effect on the ability of deserialization to assign a value to the variable
    - Changing an instance variable from transient to non-tranient (previously serialized objects will simply have a default value for the previously transient variables)

### Using serialVersionUID
- Each time an object is serialized, the object (including its entire graph) os "stamped" with a version ID number for the object's class (called the serialVersionUID)
    - As an object is being deserialized, if the class has been changed since serialization, the class could have a different serialVersionUID and deserialization would fail
    - To solve this issue, if you think there is any possibility that your class might evolve, put a a serial version ID in your class. This makes the JVM see which objects are compatible with the class, even if it's been changed
- Use the command in the command line: serialver <ClassName> & paste the output into the class
    - Be warned, in doing this, you are responsible for any changes made to the class and how that might affect earlier serialized objects
    - should look like this: static final long serialVersionID = ### <someLongNumber>

## java.io.File class
- Older class in the API, been replaced by java.nio.file
- java.io.File represents a file on disk but dosen't actually represent the contents of the file; the File object is more like a path name of a file (or directory) rather than the actual File
    - EX: /Users/Kathy/Data/Game.txt
    - Does not have methods for reading and writing, but is useful because it's a safer way to represent a file
- Some things you can do with a File object:
1) Make a File object representing an existing file
~~~ java
File f = new File("MyCode.txt");
~~~
2) Make a new directory
~~~ java
File dir = new File("Chapter7");
dir.mkdir();
~~~
3) List the contents of a directory
~~~ java
if (dir.isDirectory()) {
    String[] dirContents = dir.list();
    for (String dirContent : dirContents) {
        System.out.println(dirContent);
    }
}
~~~
4) Delete a file or directory (returns true if successful) 
~~~ java
boolean isDeleted = f.delete();
~~~

### Buffers
- Buffers give you a temportaty holding place to group things until the holder (like a cart) is full
- EX: a String is written to a chain stream and grouped into the buffer with other Strings, which is chained to a FileWriter when the buffer is full, where all the Strings are written to a file
- writer.flush() will cause the FileWriter to write everything in the buffer to a file, even if it's not full
- More efficient, this allows the FileWriter to write everything you pass to the file instead of calling write(someString) everytime

### Parsing with String split() 
- In the QuizCard example, question and answer split in the file with a "/"
- The split() method lets you break a String into pieces, it takes a seperator and breaks apart everything according to that seperator (it does not count as a token)

## NIO.2 & the java.nio.file Package
- NIO.2 usually means 
    - java.nio.file: all you need for common text file reading and writing, and allows you to manipulate a computer's directories 
        - The Path Interface: always need a Path object to locate directories or files you want to work with
        - The Paths Class: use Paths.get() method to make the Path object you'll need when you use methods in the Files class
        - The Files Class: class whose (static) methods do all the work you'll want to do: making new readers and writers and creating, modifying, and searching through directories and files on file systems
    - java.nio.file.attribute: lets you manipulate the metadata associated with a computer's files and directories. For example, you would use the classes in this package if you wanted to read or change a file's permission settings (more complex)

## Finally & IO Exceptions
- "cleanup code" usually refers to closing resources we borrowed from the operating system
- If any write() or close() methods fail, the writer will never be closed because the JVM will jump to the catch block -> leackage and not good things
- Finally ALWAYS runs, regardless of an exception being thrown or not
- However, the close() operation is risky too, so we would have to nest try/catch
- The better way: Try-With-Resources

## Try-With-Resources (TWR)
- See saveFile in QuizCardBuilder for example
- When you use try-with-resources, the compiler makes a finally block for you, you can't see it, but it's there
- Only classes that implement Autocloseable can be used in TWR statements (most every class in I/O implements Autocloseable)
- Writing a TWR Statement:
1) Add a set of parentheses between "try" and "{}"
2) Inside parentheses, declare an object whose type implements Autoclosable
3) Use the object you declared inside the try block (just like you always did in the old way)
- You can declare more than one I/O resource in a single TWR block (seperate with ;)
    - If you do this, they will be closed in the order **OPPOSITE** to which they were declared
- If you add catch or finally blocks, the system will handle multiple close() invocations gracefully






