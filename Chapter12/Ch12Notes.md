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
- Lambdas expressions are objects

