# Data Structures - Collections and Generics
- ArrayList implements List, List (interface) extends Collection (interface)
- Code that stands in for other code = mock code
- Diamomd Operator: Compiler can figure out the type of the ArrayList from the right side, so you don't need to restate it
    - ArrayList<String> songs = new ArrayList<>(); //No type needed
- ArrayLists ARE-A List, so we can tranfer most of the same methods and things from List to ArrayList
- Collections are known as a utility class because it has lots of handy methods for working with various collection types

## Helpful List/Collection Methods
- java.util.List
    - sort(Comparator): sorts list according to the order induced by the specified comparator
- java.util.Collections
    - sort(List): Sorts specified list into ascending order, according to the natural ordering of its elements // only takes lists of Comparable objects
    - sort(List, Comparator): Sorts the specified list according to the order defined by the Comparator

## Generics
- Anytime you see something with angle brackets in Java documentation or source code, it means generics
- Generics often used to write type-safe collections (code that stops you from putting a Dog into a collection of Ducks)
- Without generics, you could put a Cat, or a Dog, or a Football into an ArrayList, and they would come out as Objects
- With generics, you can put a Fish into an ArrayList, and it would come out as a Fish
- In generics, extends can mean extends OR implements
### 3 Things to Know about Generics (With Examples from ArrayList):
1) Creating instances of generic classes (like Arraylist)
    - 2 key things for generic classes: its class decleration, and the method declerations that let you add elements
    - public class ArrayList<**E**> extends AbstractList<**E**> implements List<**E**>
        - E is just a stand in for whatever type you use for the ArrayList (E = Element)
2) Declaring and assigning variables of generic types
    - A generic method means that the method decleration uses a type parameter in its signature
    - Use a type parameter defined in class decleration:
        - public boolean add (E o) // E has already been defined in the class decleration, so this is okay
    - Use a type paramter that was NOT defined in the class decleration
        - public <T extends Animal> void takeThing(ArrayList<T> list) //We can use <T> because we declared T at the start of the method decleration; this just says T can be any kind of Animal
3) Declaring (and invoking) methods that take generic types
    - Basically the same idea as #2

## Comparators
- Invoking the Collections.sort(List list) method means the list element's compareTo() method determines the order. The elements in the list MUST implement to the Comparable interface.
- Invoking List.sort(Comparator c) or Collections.sort(List list, Comparator c) means the Comparator's compare() method will be used 
    - means that elements in the list do NOT need to implement to Comparator interface, but if they do, the list element's compareTo() method will NOT be called
- A good approach is to handle all of the sorting definitions in classes that implement Comparator

## Lambdas
- Leverages what the compiler can infer
- Lambdas implement a specific method, without having to delare the class or the method, only the details about what goes into that method body
- Look at Lambda Example in src
- Single Abstract Method (SAM) = an interface with only one abstract method to implement
    - Also known as a Functional Interface
- If an interface only has one method that needs to be implemented, that interface can be implemented as a lambda expression

## Set
- When uniqueness matters, Collections that DO NOT allow duplicates
- You can never have more than one element referencing the same object (or more than one element referencing two objects considered equal)
- HashSet (implements Set) compares each element's hashcode to decide the duplicates 
### Tree Set

## Map
- When finding something by key matters 
- Duplicate values are okay, but no duplicate keys
- A key can be any object
- Do not extend Collections interface, but still considered part of the Collections API/Framework

## Object/Reference Equality
- Reference Equality
    - Two references, one object on the heap
    - If you want to know if two references are really refering to the same object, use the == operator. If both references point the same object, the bits will be identical
- Object Equality
    - Two references, two objects on the heap, but the objects are considered meaningfully equivlent 
    - If you want to treat two different objects as equal, you must override both the **hashCode()** and **equals()** method inherited from class Object
    - Override the hashCode() method by giving both objects the same hashcode, and override the equals() method so that when one object is passed the other, it always returns true
- Objects with the same hashcode do not have to be treated as equal
- If you override equals(), you must override hashcode()

