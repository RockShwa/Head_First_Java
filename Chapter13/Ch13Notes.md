# Risky Behavior - Exception Handling
- When things go WRONG, and the compiler acts like a 2 year old, we need a way to handle risky situations
- Most of the notes in this section will be closely tied to the MIDI Music Player Project

## JavaSound
- A set of classes/interfaces, 2 parts: MIDI (Musical Instrument Digital Interface) and Sampled
- MIDI is a standard protocol for getting different kinds of electrinic sound equipment to communicate, kind of like sheet music that you feed into a device and gets sound to come out (its instructions, not the sound itself)
- Use a synthesizer (or software synth) to create the sound you can hear
- Sequencer object - takes all MIDI data and sends it to the correct instruments (plays the instruments) 

## Exceptions
- Methods in Java use exceptions to tell the calling code that something bad happened
- Based on the method you're calling telling you its risky (might generate an exception)
- A risky method tells you its risky becayse you can can find a throws clause in the method's decleration (it tells you what exception will be thrown to put in catch)
- A try/catch block tells the compiler that you know an exceptional thing could happen in the method you're calling and that you're prepared to handle it 
    - Put the risky method call in try, and what do if there is an exception in catch
- Exceptions are also objects of type Exception, so you can use polymorphism when catching exceptions, since all exceptions are subclasses of Exception
- The compiler checks for everything except RuntimeExceptions, so you can try/catch/declare them, but its not needed and the compiler won't check it 
    - RuntimeExceptions typically have to do with an error in your logic, so you want those to be there and not try/catch them, so you can debug
- An exception that is handled try/catch will not print anything by default, you have to tell it to print something. In the event of an unhandled exception, the OS will automatically print something to the terminal
- finally block: where you put code that must run regardless of an exception (code that you would otherwise have to put in both try and catch blocks)
    - Will still run even if try or catch block has a return statement, flow jumps to finally, and then the return statement executes
    ~~~ java
    try {
        turnOvenOn();
        x.bake()
    } catch (BakingException e) {
        e.printStackTrace();
    } finally {
        turnOvenOff();
    }
    ~~~
### Rules of Exceptions
1) You cannot have a catch or finally without a try
~~~ java
void go() {
    Foo f = new Foo();
    f.foof();
    catch(FooException ex) {}
}
~~~
2) You cannot put code in between the try and the catch
~~~ java
try {
    x.doStuff
}
int y = 43;
catch (Exception ex) {}
~~~
3) A try MUST be followed by either a catch or a finally
~~~ java
try {
    x.doStuff(0;)
} finally {
    // This is legal
}
~~~
4) A try with only a finally (no catch) must still declare the exception
~~~ java
void go() throws FooException {
    try {
        x.doStuff();
    } finally {
        // This is legal
    }
}
~~~

### Catching Multiple Exceptions
- To declare multiple exceptions, the method decleration must declare all the checked exceptions it can throw
    - If the multiple exceptions have the same superclass, you can just use the superclass in the method decleration
~~~ java
public class Laundry {
    public void doLaundry() throws PantsException, LingerieException {
        // code that could throw either exception
    }
}
public class WashingMachine {
    public void go() {
        Laundry laundry = new Laundry();
        try {
            laundry.doLaundry();
        } catch (PantsExecption pex) {
            // recovery
        } catch (LingerieExceptrion lex) {
            // recovery
        }
    }
}
~~~
- Multiple catch blocks must be ordered from smallest to biggest (most specific to least specific), siblings (exceptions on the same level of the heirarchy tree) can be in any order

### Polymorphism + Exceptions
- You can declare exceptions using a superclass of the exceptions you throw:
    ~~~java
    public void doLaundry() throws ClothingException {}
    ~~~
- You can catch exceptions using a superclass of the exception thown:
    ~~~ java
    try {
        laundry.doLaundry();
    } catch (ClothingException cex) {}
    ~~~
- Not always the clearest, so writing each exception out can be good for clarity and so you can handle them uniquely
- Can also write a few subclass exceptions from a superclass that you want to handle seperatly, and then use the superclass to handle the rest of the subclass exceptions

### Throw Your Own Exceptions
- The risky method always throws the exception, the code that calls the risky method catches it
~~~ java
public void takeRisk() throws BadException { //Has to declare that it might throw an exception
    if (abandonAllHope) {
        throw new BadException(); // Creates a new Exception object and throws it
    }
}
~~~
- Code that calls the risky method:
~~~ java
public void crossFingers() {
    try {
        anObject.takeRisk();
    } catch (BadException e) {
        System.out.println("Aaargh!");
        e.printStackTrace(); // If you can't recover from the exception, at LEAST get a stack trace with this method that all exceptions inherit, just prints exception and the location/details
    }
}
~~~

### Ducking Exceptions
- If you don't want to handle an exception, you can duck it by declaring it
- declare that you throw the exceptions (in the method that calls the rsiky method) to satisfy the compiler
- In the event that a ducking happens, when a method throws an exception, that method is popped off the stack and exception is thrown to next method in stack (the caller), put if that method ducks, the exception keeps going, until it gets to main, and if main ducks, then the JVM shuts down :D
- Ducking only delays the inevitable though, at some point you have to deal with the exception, the first method that is not a ducker down the stack 