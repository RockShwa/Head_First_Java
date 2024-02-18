# Getting GUI

## GUI Terms
- GUI: Graphical User Interface
- JFrame: the object that represents a window on the screen, where you put all the interface things ("widgets") like buttons, check boxes, text fields, etc.
    - Uses the Swing package: javax.swing
- User event: a response in the GUI when the user does something
- Event handling: the process of getting and handling a user event
- Listener Interface: the brisge between the listener/user (you) and the event source (the button)
    - When you implement a listener interface, you give the button a way to call you back, the interface is where the call-back method is declared
- Event source: an object that can turn user actions into events, lots in the java.awt.event package
    - Creates an event object when the user does something that matters, every event type has a matching listener interface
    - Know in API if it's an event source if a method that starts with "add" and ends with "Listener" and takes a listener interface argument

## How Events Work
- **The Listener**: If your class wants to know a button's ActionEvents, you implement the ActionEvents interface. The button needs to know you're interested, so you register with the button by calling its addActionListener(this) and passing an ActionListener reference to it
    - **entire job is to implement the interface, register with the button, and provide the event handling**
- **The Event Source**: a button is a source of ActionEvents, so it has to know which objects are interested listeners. The button has an addActionListener() method to give interested objects (listeners) a way to tell the button that they're interested. When the button's addActionListener() runs (bc the potential listener invoked it), button takes the parameter (reference to the listener object) and stores it in a list. When the user clicks the buttinm the button "fires" the event by calling ActionPerformed() on each listener in the list
    - **entire job is to accept registrations (from listeners), get events from user, and call listener's event-handling method when user interacts (clicks) it**
- **Event Object**: the argument of the event call-back method(from the interface) -> job is **to carry data about the event back to the listener**
    - for example, if you have a mousePressed() call-back method, you might want to know the x-y coordinates of where the mouse was pressed, which the Event Object can carry back to the listener

## Putting things in GUI
- Three ways to put things on the screen:
    1) Put widgets on the frame (buttons, menus, radio buttons, etc.) using javax.swing
    2) Draw 2D graphics on a widget like shapes or more complex shapes/art
    3) Put a JPEG on a widget using graphics.drawImage(myPic, 10, 10, this); (can also do a gif)

## Making your own drawing widget
- Make a subclass of JPanel and override one method, paintComponent()
    - paintComponent() is where all the graphics code goes, anytime the JVM thinks the display needs refreshing, it will call this method. You NEVER call this method directly yourself, the system handles it for you
    - paintComponent() takes a Graphics object parameter, usually called g. g is actually a reference to a Graphics2D object (Graphics2D can do more); if you need to use a Graphics2D method, you have to cast it as a Graphics2D object first because the parameter of paintComponent() is a Graphics object, which is the superclass
    - see MyDrawingWidget class in src
