# Race Conditions and Immutable Data - Dealing with Concurrency Issues

- Concurrency issues: issuses that happen when multiple threads run at the same time
- For example, if two or more threads are trying to access and change a single object's data, BAD things can happen
    - They are both trying to read and write to an object without knowing the other is there, trying to do the SAME thing

## Synchronized
- The synchronized keyword means that a thread needs a key in order to access the syncronized code, it locks an object so only one thread can use it at a time
- To protect data, syncronize the code that acts on that data
- The point of syncronization is to make code work atomically; not just individual methods we care about, it's the methods that require more than one set to complete

### Using an Object's Lock
- Every object has a lock, and the defualt is unlocked. Object locks come into play only when there is a synchronized block for an object or a class has syncronized methods
    - A method is synchronized if it has the syncronized keyword in the method decleration
- When an object has one or more syncronized methods, **a thread can enter a synchronized method only if the thread can get the key to the object's lock**
- Goal of synchronization is to protect an object's data, but you don't lock the data itself, you lock the methods that access that data
- While one thread holds the object's key, no other threads can enter ANY of the object's syncronized methods
- Static methods: each object has a lock, but the class as a whole also has a lock. So if you have three Dog objects on the heap, you'll have 3 locks for the objects, and one lock for the Dog class
    - This means when you synchronize a static method, Java uses the lock of the class itself