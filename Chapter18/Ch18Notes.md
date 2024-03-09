# Race Conditions and Immutable Data - Dealing with Concurrency Issues

- Concurrency issues: issuses that happen when multiple threads run at the same time
- For example, if two or more threads are trying to access and change a single object's data, BAD things can happen
    - They are both trying to read and write to an object without knowing the other is there, trying to do the SAME thing

## Synchronized
- The synchronized keyword means that a thread needs a key in order to access the syncronized code, it locks an object so only one thread can use it at a time
- To protect data, syncronize the code that acts on that data
- The point of syncronization is to make code work atomically; not just individual methods we care about, it's the methods that require more than one set to complete
- Don't use synchronize everywhere, it can lead to slower performance because it limits concurrency. Synchronized methods can also lead to deadlock, so synchronize the bare minimum (including synchronizing only a line of code with the keyword)

### Using an Object's Lock
- Every object has a lock, and the defualt is unlocked. Object locks come into play only when there is a synchronized block for an object or a class has syncronized methods
    - A method is synchronized if it has the syncronized keyword in the method decleration
- When an object has one or more syncronized methods, **a thread can enter a synchronized method only if the thread can get the key to the object's lock**
- Goal of synchronization is to protect an object's data, but you don't lock the data itself, you lock the methods that access that data
- While one thread holds the object's key, no other threads can enter ANY of the object's syncronized methods
- Static methods: each object has a lock, but the class as a whole also has a lock. So if you have three Dog objects on the heap, you'll have 3 locks for the objects, and one lock for the Dog class
    - This means when you synchronize a static method, Java uses the lock of the class itself

## Race Conditions
- Where two or more threads are changing the same data at the same time (losing updates)
- Example:
~~~ java
// Get balance in account
int i = balance;
balance = i + 1;
// OR
balance ++;
// This is probably not an atomic process, it's actually multiple operations: a read of the current value and then adding one to that value and setting it back into original variable
// To fix this, we need to syncronize the method that increments balance
~~~
- Once a thread enters the method, we have to make sure that all the steps in the method complete (as one atomic process) before any other thread can enter the method

## Deadlock
- Thread deadlock happens when you have two threads, both of which are holding a key the other thread wants, they just wait in deadlock, forever. Java has no mechanism to handle deadlock, it won't even know when deadlock has occured
- A deadlock scenario:
1) Thread A enters a synchronized method of object foo and gets the key -> Thread A goes to sleep, holding the foo key
2) Thread B enters a synchronized method of object bar and gets the key -> Thread B tries to enter method of object foo, but it can't get to that key (becuase A has it). B goes to the waiting lounge, until foo key is available B keeps the bar key
3) Thread A wakes up (still holding foo key), and tries to enter a synchronized method on object bar but can't get that key because B has it. A goes to waiting lounge until bar key is available (and it never will be), so both threads wait... and wait... and wait

## Alternatives for Synchronized
- Atomic variables: If the shared data is an int, long, or boolean, we might be able to replace it with an atomic variable. These wrapper classes provide atomic methods that can be safley used by a thread without worrying about another thread changing the object's values at the same time
    - AtomicInteger, AtomicLong, AtomicBoolean, AtomicReference
    - More powerful when you use their compare-and-swap methods (CAS) -> use the compareAndSet method, which takes a value, which is what you expect the atomic variable to be, compares it to the current value, and if that matches, then the operation will complete
    - Ex:
    ~~~ java
    private AtomicInteger balance = new AtomicInteger(100);
    boolean sucess = balance.compareAndSet(expectedValue, newValue);
    // YOU have to deal with the case that the operation does not succeed
    ~~~

## Immutable Data
- Make an object immutable if you're going to share it between threads and you don't want the threads to change its data
- Immutable object's data can not be changed
- For example:
~~~ java
public final class ImmutableData { // Final classes can not have subclasses (they might add mutable data!)
    // All instance fields should be FINAL, the value will be set once, here or in the constructor
    private final String name;
    private final int value;
    private final MuttableClass obj; // Assuming MutableClass is mutable, the contents of this class can still be changed, even though the reference itself will not change

    public ImmutableData(String name, int value) {
        this.name = name;
        this.value = value;
    }
    // Immutable objects can have getters, but no setters
    public String getName() { return name; }
    public int getValue() { return value; }
}
~~~
- There is no need for synchronization of immutable objects, becuase the data will not chnage
- Instead of changing the existing object, we have to make a new object with new data to replace an immutable object 

### Changing Immnutable Data
- Example: a system that has customers, and that each Customer has an immutable object Address
1) The customer has a reference to the original Address object containing the customer's street address data
2) When the customer moves, a brand new Address object is created with the new street address for the customer
3) The Customer object's reference to their address is changed to point to the new Address object
- This example illustrates that not all of your classes have to change. In fact, you should minimize & centralize the things that do change, so you can have more control over what data is changed by multiple threads

## More Problems with Shared Data
- We can have problems with threads writing to data in Collections (Collections are not thread safe)
    - Reading from a changing data structure causes an Exception (sometimes), like ConcurrentModificationExecption (thrown by reading thread when the List it is reading is changed WHILE the thread is reading it)
- We can also have problems when we have lots of threads reading the same data, even if only one is making changes
- **Marking an instance variable that is a reference to another object does NOT guarantee that the data inside that reference won't change. It just gaurantees the reference won't change**

### Thread-Safe Data Structures
- java.util.concurrent has a number of thread-safe data structures, one being:
- CopyOnWriteArrayList - Implements the List interface, so we can use it as a replacement for any List; a good choice when you have a List that is being read a lot, but not changed very often 
    - When a thread is writing to the list, it's actually writing to a copy of the list. When the changes have been made, the new copy replaces the original. In the meantime, any threads that are reading are reading the original copy
    - Due to the fact that the reading threads read the original while the new copy is being written, this can lead to some data being outdated