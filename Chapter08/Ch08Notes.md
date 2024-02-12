# Abstract Classes & Object Class

- Interface: A class that is 100% abstract (it can not be instantiated)
- Keyword abstract: compiler will stop any code anywhere from creating an instance of that abstract type (you can still use it as a referance type)
- Concrete classes: opposite of abstract classes, specific enough to be instantiated, you can make objects of this type
- Abstract classes: an new instance of that class can never be instantiated. It's virtually useless unless it's extended. In runtime, the instances of a subclass of the abstract class do the work. You can have non-abstact methods in an abstract class, but you need at least one abstract method
    For example:
    Canine c; //this is okay
    c = new Dog();
    c = new Canine(); //this BLOWS UP THE WORLD, don't do it :D
- Methods can be abstract: means the method must be overriden since the method is so abstract. Don't have a method body. Abstract methods can only go in an abstract class
- Implementing an abstract method is just like overriding a method; the first concrete subclass must implement all abstract methods (provide a body for all the abstract methods)
- Every class in Java extends form the class Object
- Basic Object class methods: equals(), getClass(), hashCoode(), toString(). Object class is not abstract, you can override equals(), hashCode(), toString(), but getClass() is final
- Object class's 2 main purposes:
    1) Act as a polymorphic type for methods that need to work for any class
    2) Provide real method code that all objects in Java need at runtime
- Treating everything polymorphically with Object class means that objects appear to lose their true essence (Enter a Dog into an ArrayList<Object> and it's return type is an Object, not a Dog)
- The complier decides whether you can call a method based on the REFERANCE type, not the actual OBJECT type
- You can cast Objects as their real type, so if an Object ArrayList returns an Object, but it was originally passed as a Dog, cast the returned Object as Dog 
    - Object o = al.get(index)
    - Dog d = (Dog) o
    - d.roam() //Dog essence returned!
- keyword super: lets you invoke a superclass version of an overriden method from within the subclass

## Interfaces
- keyword interface: a 100% abstract class 
- Side-steps Deadly Diamond of Death (multiple inheritance) by making all the methods abstract so JVM isn't confused about which inherited method to use
- Any subclass of the interface HAS to implement the methods of that interface
- Interfaces are best for Polymorphism, they are the ultimate flexibility. If you use interfaces as arguments or return types, you can pass anything that implements the interface, and it all dosen't have to come from the same inheritance tree
- Classes from different inheritance trees can implement the same interface, and the same class can implement multiple imterfaces
- Child classes implement interfaces of parent classes

## How to Know When to make a Class, a Subclass, an Abstract Class, or an Interface
- Class: make a class that dosen't extend anything (Other than Object) when your new class dosen't pass the IS-A test for any other type
- Subclass: extend a class/make a subclass only when you need a more specific version of a class and need to override or add new behaviors
- Abstract Class: make when you want to define a template for a group of subclasses, and yiou have at least some implementation code that all subclasses could use. Make only when you want to gaurantee that nobody can make objects of that type
- Interfaces: make when you want to define a role that other classes can play, regardless of where those classes are in the inheritance tree
