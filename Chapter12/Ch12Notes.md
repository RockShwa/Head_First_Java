# Lambdas and Streams
- More (basic) info on lambdas in Chapter 11 Notes

## Streams API
- These are Intermediate Operations:
    - Stream<T> distinct() - Returns a stream consisting of the distinct elements
    - Stream<T> filter(Predicate<? super T> predicate) - Returns a stream of the elements that match the givin predicate
    - Stream<T> limit(long maxSize) - Returns a stream of elements truncated to be no longer than maxSize length
    - <R> Stream<R> map(Function<? super T,? exetends R> mapper) - Returns a stream with the results of applying the given function to the elements of this stream
    - Stream<T> skip(long n) - Returns a stream of the remaining elements of this stream after discarding the first n elements of the stream
    - Stream<T> sorted() - Returns a stream of the elements of this stream, sorted according to the natural order
    - <T> usually means the Type of object in the stream
    - <R> usually means the type of the Result of the method
- These are Terminal Operations:
    - boolean anyMatch(Predicate<? super T> predicate) - Returns true if any element matches the predicate
    - long count() - Returns the number of elements in this stream
    - <R,A> R collect(Collector<? super T,A,R> collector) - Performs a mutable reduction operation on the elements on this stream a Collector; this basically just gets the output of the stream pipeline in a List/Collection
    - Optional<T> findFirst() - Returns an Optional desctibing the first element of this stream, or an empty Optional if stream is empty
- map() operations states how to map from one type to another type
    - Takes a Function interface, which takes something of one type and returns something of a different type
- distinct(), stops any duplicate elements from getting through the stream
- anyMatch(Predicate), allMatch(Predicate), noneMatch(Predicate) - Pretty self-explanatory, returns a boolean value:
    - .anyMatch(s -> s.getGenre().equals("R&B"));
- long count() - find out number of elements in stream 
### Optional (the class)
- Optional<T> findAny(), Optional<T> findFirst(), Optional<T> max(Comparator c), Optional<T> min(Comparator c), Optional<T> reduce(BinaryOperator a)
- Return an Optional value looking for a specific thing in the stream
- Optional: may return something, maybe not; it's a wrapper class that wraps the result so you can decide what to do next
- Optional results can be empty, if you don't check first if they're empty, you'll get an Exception
~~~ java
Optional<IceCream> optional = getIceCream("Strawberry");
if (optional.isPresent()) {
    IceCream ice = optional.get();
} 
else {
    System.out.println("No icecream for you!");
}
~~~

### Using Streams
- A stream does NOT contain the elements in the collection, its like a set of instructions for the operations to perform on the Collection data
- Intermediate Operations - Stream methods that return another Stream (instructions of things to do, but don't actually perform the operation on their own)
- Terminal Operations - Methods that actually do the thing the Intermediate Operations tell them to do:
    1) Perform all intermediate operations as efficiently as possible, ideally going through the data once
    2) Work out the result of the operation, defined by terminal operation itself. Could be a list of values, a single value, or a boolean
    3) Returns result
- Stream Pipeline: stream + intermediate operation + terminal operation (like building blocks); represents a query to the original collection
    - Streams designed to be chained (see examples)
- Collectors.toList():
    - Collectors is a class that has static methods that provide different implementations of Collector
    - The method overall takes a Collector, the recipe for how to put together the results. In this case, its using a predefined Collector that puts the results in a list
- A stream does NOT change the original collection, even after terminal command, it creates a modified copy

### Guidelines for Working with Streams
1) You need at least the first and last pieces to create a stream pipeline (the stream() and terminal operation)
2) You can't reuse Streams. Once a pipeline has been executed, that stream is closed and can't be used in another pipeline, even if you store it in a variable. No adding to or changing a stream after the terminal command is called
3) You can't change the underlying collection while the stream is operating

## Lambdas 
- forEach() from Iterable interface: do something to each element of a collection, helps reduce accidental errors that a for loop or even a normal for each loop create
    ~~~java
    List<String> allColors = List.of("Red", "Blue", "Green");
    allColors.forEach(color -> System.out.println(color));
    ~~~
- Lambdas tell compiler WHAT you want it to do, not HOW
- -> = "do this"
- Lambdas expressions are objects, and you run them by calling their Single Abstract Method (SAM), they implement a Functional Interface
    - If you want method to accept a lambda expression, you need to have a parameter whose type is a functional interface, the SAM is called when we run the lambda
    ~~~java
    void forEach(someFunctionalInterface lambda) {
        for (Element element : list) {
            lambda.singleAbstractMethodName(element);
        }
    }
    ~~~
- Anatomy of a Lambda Expression:
    ~~~ java
    (String s1, String s2) -> return s1.compareToIgnoreCase(s2);
    ~~~
    - Number and types of parameters to lambda expression are determined by the Functional Interface it implements
    - Types of objects being compared not required (in this case s1 & s2)
    - Basically a shortened method!
- The type of a lambda is its interface
- You can assign a lambda expression to a variable, like any other object, which helps us see its type and if we can pass it to a method:
    ~~~ java
    Consumer<String> consumer = str -> System.out.println(str);
    Runnable runnable = () -> System.out.println("Hello!");
    ~~~
- Sometimes, you don't even need a lambda expression, you can use a method reference instead
    - Function<Song, String> getGenre = Song::getGenre;
    - This basically says the input will be a song, the output will be a String; the :: is the method reference, which tells the compiler where the method is at (so it can call it and get the result you want)
    - Can make code easier to understand
    - We can use a lot of static helper methods in the Functional Interfaces to use method references on

### Rules/Exceptions of Lambdas
- A lambda could be more than one line: its like any other method, it must be surrounded by curly brakets, have a return statement, and each line must end in a semicolon
    ~~~ java
    (str1, str2) -> {
        int 11 = str1.length();
        int 12 = str2.length();
        return 12-11;
        // Results in the collection being sorted in descending order
    }
    ~~~
- Single-line lambdas don't need ceremony: much of their syntax can be shortened. Choose between single and multi line lambdas based off clarity
    ~~~ java
    (str1, str2) -> str2.length() - str1.length()
    // No semicolon, brakets, or return statement
    ~~~
- A lambda might no return anything: if the return type on the Functional Interface is declared as void, the lamba just executes stuff and does not have to return something
    ~~~ java
    str -> { // No need for parenthesis if it's a single parameter
        String output = "str = " + str;
        System.out.println(output);
    }
    ~~~
- A lambda might have zero, one, or many parameters: dependent on the number of parameters the Functional Interface takes; don't always have to add types of parameters, but sometimes do so compiler can know and for clarity
    ~~~ java
    () -> System.out.println("Hello!") // Uses Runnable Interface, void run() method
    str -> System.out.println(str) // Uses interface Consumer<T>, void accept(T t)
    (str1, str2) -> str1.compareToIgnoreCase(str2) // Uses interface Comparator<T>, int compare (T o1, o2)
    ~~~

## Functional Interface
- An interface with a Single Abstract Method (SAM)
- Sometimes denoted with @FunctionalInterface, but not always, especially in older code
- Interfaces in Java 8 can also contain static and default methods that don't have to be overriden, anything else in an interface MUST be overriden when implemented
    - Default methods: like a standard method in an abstract class, have a body, and are inherited by the subclasses
- Don't be misled by methods inherited by Object either :D

## Collecting Results of a Stream
- Collectors.toList() & Collectors.toUnmodifiableList():
    - toUnmodifiableList is java 10 and up
    - Basically puts results of the stream into a List
- Collectors.toSet() & Collectors.toUnmodifiableSet():
    - toUnmodifiableSet is java 10 and up
    - Use this to put the results into a Set, which by definition does NOT allow duplicates
- Collectors.toMap() & Collectors.toUnmodifiableMap():
    - toUnmodifiableMap is java 10 and up
    - Collects stream into a Map of key/value pairs, need to provide some functions to tell the collector what will be the key and what will be the value
- Collectors.joining
    - Creates a String result from a stream, joins all elements in the stream together into one large String
    - Can optionally define a delimeter, which seperates each element by a character
    - Useful if you turn your stream into a String of Comma Seperated Values (CSV)