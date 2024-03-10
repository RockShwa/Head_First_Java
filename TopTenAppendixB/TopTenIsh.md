# Appendix B - Top Ten (Ish) Concepts

## 1 - JShell (Java REPL)
- A REPL (Read Eval Print Loop) lets you run snippets of code without needing a full application or framework
- Starting the REPL: command line tool that comes with the JDK; if JAVA_HOME/bin is on your system's path, you can just type in jshell from the command line
- You can run Java code without a class, main method, or semicolon
- You can define variables and methods, and it supports forward references
- do /help for list of commands and more info
- JShell User Guide (https://oreil.ly/Ei3Df)

## 2 - Packages
- Packages prevent **class** name conflicts
- The package name is the full name of the class, which helps us tell class names apart
- Preventing package name conflicts: standard naming convention is to prepend every class with your reverse domain name (domain names, like Example.com, are guaranteed to be unique)
    - Two different guys can be named Bartholomew Simpson, but tow different domains can not be named doh.com
    - com.headfirstjava.projects.Chart (class names always capitalized)
- To put your class in a package:
1) Choose a package name (reverse domain name here!)
2) Put a package statement in your class 
    - Must be the first statement in the source file, above any import statements
    - There can only be one package statement per source code file, **so all classes in a source file must be in the same package** (includes inner classes)
3) Set up a matching directory structure (actually make directories and classes that match the package)
- Directories: src and class files usually kept in seperate directories
    - Common structure for Java Projects (src)
        - **MyProject/src/main/java - Application sources**
        - **MyProject/src/test/java - Test sources**
    - Common structure for class files
        - Gradle:
            - **MyProject/out/production/classes - Application classes**
            - **MyProject/out/test/classes - Test classes**
        - Maven:
            - **MyProject/target/classes - Application classes**
            - **MyProject/target/test-classes - Test classes**
- Compiling/Running Packages:
    - By using the -d flag, you get to decide which directory the compiled code lands in, it know to put the class into the correct directory structure for the packaage the class is in, and it tells the compiler to build the directories if they don't exist
    - EX:
        - cd MyProject/source
        - javac -d ../classes com/headfirstjava/PackageExercise.java
            - Tells compiler to put the compiled code into classes directory, within the right package structure
            - Then you specifiy the actual path to get to the source file
    - Running your code:
        - cd MyProject/classes
        - java com.headfirstjava.PackageExercise
        - You must give the fully qualified name

## 3 - Immutability in Strings and Wrappers
- Strings in Java are immutable, so:
~~~ java
String s = "0";
for (int i = 1; i < 10; i++) {
    s = s + i;
}
// What's actually happening here is that you're creating ten String objects. In the end, s is referring to the String "0123456789," but at this point there are ten different Strings in existance
~~~
- If you use methods to "change" a String (like toUpperCase()), it actually creates a copy of that former String
- How does this save memory?
    - The JVM has a special memory area called the String pool that it checks when a new String is created. If a String already has the same value as another String in the pool, it won't create a duplicate, it refers instead to the existing entry
    - The JVM can get away with this because String are immutable
- If you have to do a lot of String manipulations (like concatenations), you can avoid the creating of unnecessary strings by using a StringBuilder (mutable)
    ~~~ java
    StringBuilder s = new StringBuilder("0");
    for (int i = 1; i < 10; i++) {
        s.append(i);
    }
    String finalString = s.toString();
    // This updates the mutable StringBuilder and then turns it into one final String
    ~~~
- Wrappers in Java are immutable, so there is no setter method for a wrapper object. Once you create a wrapper object, you can not change its value

## 4 - Access Levels and Access Modifiers 
- Java has 4 access levels and 3 access modifiers
- Access Levels (least to most restrictive):
    1) public - any code anywhere can access the public thing (class, method, variable, constructor, etc.)
    2) protected - works just like default (code in the same package has access), but it also allows subclasses outside the package to inherit the protected thing
    3) default - only code within the same package as the class with the default thing can access it
    4) private - only code within the same class can access the private thing (private to the class, not the object, Dogs can see other Dog's stuff, but a Cat can't see a Dog's stuff)
- Access Modifiers:
    - Only three modifiers because default is what you get when you don't use an access modifier
    1) public - Use public for classes, constants (static final variables), and methods you're exposing to other code and most constructors
    2) protected - only applies to inheritance, if the subclass-outside-the-package has a reference to an instance of the superclass, the subclass can't access the protected method using that superclass reference (the subclass dosen't have access to the method, it has the method through inheritance)
    3) private - Use private for virtually all instance variables, and for methods that you don't want outside code to call (methods only used within your class)

## 5 - Varargs
- Varargs let a method take as many arguments as they want, as long as they're the same type
- How to tell if a method takes a vararg:
    - The triple dot (...) says that this method takes an arbitrary number of Objects after the argument, **including zero**
    - Ex: static String format(String format, Object... args)
- A method can have only one varargs parameter, and it must be the last parameter
- Making your own method that takes a vararg:
~~~ java
void printAllObjects(Object... elements) {
    for (Object element : elements) {
        System.out.println(element);
    }
}
// This is equivilent to:
void printAllObjects(Object[] elements)
// However, in the first method call, you get to pass an arbitrary number of arguments instead of an Object array
~~~

## 6 - Annotations
- Adding an annotation to your code can add extra behavior, or it can be a sort of compiler friendly documentation
- You will see annotations in test code especially (JUnit, @Test)
- Annotations can be applied to classes and methods, variables (local and instance), and parameters
- Some annotations include elements, which are like parameters with names
    - EX: @Table (name = "customer")
    - If an annotation only has one element, you don't need to include name
- Other annotations add additional functionality to the code, just look at the documentation!

## 7 - Lambdas and Maps
- Maps hava a few methods that can take lambda expressions
- EX: computeIfAbsent():
    - You pass a lambda expression to this method that tells how to compute the value that should go into the Map if there isn't an entry for the given key
    - Map<String, Actions> custActs = new HashMap<>();
    - Actions actions = custActs.computeIfAbsent(usr, name -> new Actions(name));
- EX: computeIfPresent():
    - Update a value in the Map only if it exists
    - Map<String, Integer> metrics = new HashMap<>();
    metrics.computeIfPresent(metric, (key, value) -> ++value)

## 8 - Parallel Streams
- Parallel strems can take advantage of modern, multicore, multi-CPU hardware to run streams in parallel
- So far, we've just used streams to query our data structures
- If we had a really big data set, we could iterate through it all one by one (serial), or we could split it into multiple operations and run them in parallel, on different CPUs
- Going Parallel (both methods do the same thing):
    1) Start a Parallel Stream:
        ~~~ java
        List<Song> songs = getSongs();
        Stream<Song> par = songs.parallelStream();
        ~~~
    2) Add parallel() to the stream pipeline
        ~~~ java
        List<Song> songs = getSongs();
        Stream<Song> par = songs.stream()
                                .parallel();
        ~~~
- Now you just write your pipeline and add the operations you want and finishing with a terminator
- Multithreading is taken care of in the behind-the-scenes of parallel streams, can change dfualt, but best to leave it until you really know what you're doing
- Don't use parallel everywhere, going parallel and using multiple CPU cores is not free and is not always faster. For most ordinary cases, serial streams are faster than parallel streams
- Use parallel streams when the input collection is BIG (hundreds to thousands of elements), the stream pipeline is performing comlicated, long running operations, and the decomposition of the data/operations and merging of the results are not too costly

## 9 - Enumerations (AKA Enumerated Types or Enums)
- A way to create a set of constant values to represent the only valid values for a variable
- Enums are usually safer to use than static final constants, becuase they are the only possibilities for a variable
- When you create an Enum, you're implicitly extending from java.lang.Enum; you can decalre an Enum in its own file, standalone class, or a a member of another class
- You can use == or .equals() to compare enums, but == is usually used
- EX (with switch statements):
~~~ java
public enum Member { KEVIN, BOB, STUART};
Member member = Member.BOB;
switch (member) {
    case KEVIN: System.out.print("Uh... la cucaracha?");
    case BOB: System.out.print("King Bob");
    case STUART: System.out.print("Banana!");
}
// Output: King Bob Banana!
~~~
- You can add constructors, methods, variables, and a constant-specific class body to an enum (not common, but possible)