# Numbers & Statics

## MATH Class
- The closest you will ever get to a global method, it does not depend on on object or instance variables
- You can't even make an object of the Math class, its constructor is private
- All of its methods are static, meaning you just need the class, not an object
### Math Methods
- Math.abs()
    - Returns a double that is the absolute value of the argument, if you pass it an int though, it will return an int
- Math.random()
    - Returns a double between (including) 0, and (not including) 1
- Math.round()
    - Returns an int or a long (if given a float, it returns an int. If given a double, returns a long) rounded to nearest integer value
- Math.min()
    - Returns a value that is the minimum of the two arguments (can take ints, longs, floats, or doubles)
- Math.max()
    - Returns a value that is the maximum of the two arguments (can take ints, longs, floats, or doubles)
- Math.sqrt()
    - Returns positive square root of the argument (takes a double, but you can input anything that fits into a double)

## Static
- Static lets a method run without an instance of the class because its behavior is not dependent on an instance variable
- Call a static method with the CLASS name, not an object name
- Static methods can not use nonstatic instance variables, even if its the only static method
- Static methods can not use nonstatic methods either
- The values of a static variable is the same for ALL instances of the class
    - For example, good for counting the number of objects created
- Static variables are initalized when the class is first loaded & before any instances of that class are created (get default values too if no value is assigned)
- Nonstatic methods can access a static variable

### Static Imports
import static java.lang.Math.*;
import static java.lang.System.out;
- Allows you to do this:
- out.println("sqrt " + sqrt(2.0));
- out.println("tan " + tan(60));
- Can be really confusing, not recommended :D

## Static Final 
- A variable marked final can never change its value once initalized
- Naming Convention: Constant (final) variables are usually in all caps & use _ to seperate words
- Static initalizer is a block of code that runs when class is loaded, good place to init static final variables:
    - static() { //code here};
- Final methods mean you can't override that method
- A final class mean you can't extend that class

## Wrapping a Primitive
- A wrapper class: helps you treat a primitive like a double
- A wrapper class name for primitives is the same as the primitive, but with a capital letter (and Integer and Character are spelled out)
- Wrapping/Unwrapping examples in src
- Thankfully, Java Autoboxes (wraps and unwraps) primitives automatically (also allows you to use a primitve or its wrapper class basically anywhere interchangeably)
- <WrapperClass>.Parse<primitive>(<String>) -> Parse methods take a String and parse it into a primitive value (String must represent the primitive, so for parseInt you must pass in "2", not "two")
- Turn primitives into a String: 
    1) String doubleString = "" + 42.5;
    2) String doubleString = Double.toString(42.5);
    3) String doubleString = String.valueOf(42.5);

## Number Formating
- Uses java.util.Formatter
- A quick shortcut:
    - long easyToRead = 1_000_000_000; //The same to compiler as 1000000000
- Can use String.format("%,d", 1_000_000_000) to insert commas where appropriate
    - "%,d" are the instructions on the argument (1_000_000_000) - % represents the variable that is the second argument, which is going to be formatted with the instructions after %
- %,d = "insert commas and format the number as a decimal integer"
- %.2f = "format the number as a float with the precision of 2 decimal places"
- %,.2f = "insert commas and format the number as a floating point with the precision of 2 decimal places"

### The Format Specifier
- Can have 5 different parts, everything in brackets is optional, but the order is not
- "% [argumentNnumber] [flags] [width] [.precision] type"
    1) Allows you to say which argument if there is more than one
    2) For special formatting options, like inserting commas, putting negatives in parentheses, or making the numbers left justified
    3) Defines MINIMUM number of characters that will be used (if it's less, it will be padded with zeros)
    4) Defines the precision or decimal places
    5) Mandatory; usually the "f" or "d"
- Type options:
    1) %d - decimal, argument must be compatible with an int or anything that fits into an int (its a decimal INTEGER)
    2) %f - floating point, only accepts floats or doubles
    3) %x - hexadecimal, argument must be a byte, short, int, or long
    4) %c - character, argument must be a byte, short, char, or int
- Multiple arguments are fine, the format is inserted in the order that you pass them:
    - int one = 20456654;
    - int two = 100567890.248907;
    - String.format("The rank is %,d out of %,.2f", one, two);