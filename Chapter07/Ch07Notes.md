# Inheritance

- Subclasses inherit the methods of the superclass
- Overriding in a subclass: a subclass redefines one of its inherited methods when it needs to change or extend a behavior of that method (method has same name, just has different behaviors)
- inherit is the same idea as extend --> subclass extends superclass
- subclasses also inherit members of the superclass (members of a class are its instance variables and methods)
- subclasses can add new instance variables or methods
- Instance variables are not overriden in a subclass since instance variables don't define any special behavior
- When you call a method on an object referance, you're calling the most specific version of that method for that object type (the lowest (on the inheritance tree) one wins!)
- If you want to know if one thing should extend another --> "X IS A Y" test (Triangle IS A shape, tub IS NOT A bathroom). If it passes the "X HAS A Y" test, make Y an instance variable of X
- Moving from most to least restrictive: private, default, protected, public. Public members are inherited, while private members are not inherited
- Classes can not be subclassed if: 
    1) Class is not marked as public (only able to be subclassed by classes in same package)
    2) Class is marked as final
    3) Class only has private constructors
- You can mark a method as final if you don't want it to be overriden

# Designing Inheritance

1) Look for objects that have common attributes and behaviors
2) Design a class that represents the common state anf behavior
3) Decide if a subclass needs behaviors (method implementations) that are specific to that particular subclass type
4) Look for more opportunities to use abstraction, by finding two or more subclasses that might need common behavior
5) Finish class heirarchy

# Rules for Overriding Methods
1) Arguments must be the same, and return types must be compatible
2) The method can't be less accessible (can be more accessible)

# Rules for Overloading Methods
- Methods with the same name but different arguments
1) Return types can be different, as long as argument lists are different
2) You can vary the access levels in any direction

# Polymorphism

 - When you define a supertype for a group of classes, any subclass of that supertype can be subsituted where the supertype is expected (aka you get to reger to a subclass object using a reference declared as a supertype)
 - Normal declaration of a referance and object: Dog mollyWog = new Dog();
 - Polymorphism: Animal mollyWog = new Dog();
 - See src for examples that help :D