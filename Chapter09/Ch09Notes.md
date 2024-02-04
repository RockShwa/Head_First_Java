# Chapter 9 - Constructors and Garbage Collection
- Garbage Collecter (GC): collects unused objects, vaporizes them, and reclaims the memory
- The Stack: Where method invocations and local variables live 
- The Heap: Where ALL objects live 
- Instance Variables: declared inside a class but NOT inside a method ("fields" that each individual object has; live inside object they belong to)
- Local Variables: declared inside a method, including method parameters (temporary and live only as long as the method is on the stack; basically only live as long as the method has not reached the closing curly brace)

## The Stack
- The method at the top of the stack is always the one executing and stays on the stack until it reaches its final curly brace
- Each method on the method stack is called a "frame" and it holds the method executing, plus all the local variables
- If the local variable is a reference to an object, only the variable (reference) goes on the stack, not the object itself (since objects ALWAYS go on the heap)

## The Heap
- Instance variable values live on the heap, inside their objects
- If that variable is a reference to an object, Java makes space in the heap for that reference, not an entire object
- An object is only assigned to be on the heap when this happens: new Antenna();
- A reference to the Antenna object would then be stored inside the class with the Antenna instance variable, but the object would be created outside the original class

## Constructors 
- new Antenna() <-- calls Antenna constructor; code that runs when you instantiate an object
- Every class has a constructor, even if you don't write it
- Compiler's default constructor (when you don't write one):
    public Antenna() {
    
    }
- Runs before obhect can be assigned to a reference, which means you can do things to get an object ready for use
- Constructors can NOT have a return type and are the same name as the class
- Overloaded constructors (more than one constructor in one class that take different parameters)
- Compiler will not automatically make a default constructor if you write a constructor, it only makes a default constructor if no constructor is written in the class at all
- You can have two constructors with the same patameter types, but the have to be in a different order

## Constructors & Inheritance
- Constructors are NOT inherited by child classes
- When an object is created, it's created with all of its own instance variables and everything from its superclass, but only ONE object is created on the Heap
- **All constructors in an object's inheritance tree must run when you make a new object**
    - Means that even abstract classes have constructors because they can be superclasses, and their constructor needs to be run by a child object
    - For a child class to work, all the instance variables of the superclasses must be initalized
    - Called constructor chaining :D

### Super()
- Even when a child class' constructor is called first, the parent class' constructor finishes first, so it would print/init stuff first
- super() calls the superclass' constructor -> compiler will put in a default super constructor call if you do not put one (means your superclass MUST have a default constructor)
- The superclass parts of an object have to be fully formed (completely built) beofre subclass parts can be constructed 
    - Means that the super() call MUST be first in your subclass constructor
- this() reference to the current object    
    - Used to call a constructor from another overloaded constructor in the same class
    - Can only be used in a constructor, and it must be the first statement in a constructor
    - A constructor can have super() **OR** this(), but never both

## Object Lifespan
- A reference is considered alive if object is alive on the Heap, if the reference dies, so does the object
- Local variables live only within the method that declared the variable & die after their methods finish executing (varaible is in scope only within its own method, you can only use it while that variable's method is running on the Stack)
- Instance variables live only as long as the object does (scoped to life of the object, not the life of the method)
- Reference variables work the same way with scope
- An object's life has no meaning unless there is a reference to it, otherwise, the Garbage Collecter will come and scoop up the object to save memory
- 3 ways to get rid of an object's reference:  
    1) When reference goes permently out of scope (once a method is popped of the Stack for example)
    2) When reference is assigned to a different object
    3) When reference is explicitly set to null (basically means you can't use the remote control reference until it's explicitly assigned another object again)
