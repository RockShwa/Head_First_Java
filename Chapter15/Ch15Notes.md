# Using Swing

## Swing Terms
- Layout Manager: controls size and location of the widgets in the Java GUI
- Components: the more correct term for widget, they're the things the user sees and interacts with (javax.swing.JComponent)
    - Most componenets are capable of holding other components, usually you add interactive components (buttons, lists, etc.) into background components (often called containers, like frames and panels)
        - the distinction between background and interactive components is artificial (except JFrame), you can technically interact with JPanel

## Layout Manager
- A Java object associated with a particular component, almost always a background component; it controls components contained within the component the layout manager is associated with
    - If a frame holds a panel, and the panel holds a button, the panel's layout manager controls size and placement of the button, while the frame's layout manager controls the size and placement of the panel, and the button does not need a layout manager, because it's not holding components
- Example of Nested Layout:
~~~ java
// panelA's layout manager has nothing to say about the three buttons
JPanel panelA = new JPanel();
JPanel panelB = new JPanel();
panelB.add(new JButton("button 1"));
panelB.add(new JButton("button 2"));
panelB.add(new JButton("button 2"));
panelA.add(panelB);
// Creates:
//////////////////
// PanelA       //
// ///////////  //
// / Panel B /  // 
// / Button1 /  //
// / Button2 /  //
// / Button3 /  //
// ///////////  //    
//////////////////
~~~
- The Layout Manager's Process:
1) Make a panel and add three buttons to it
2) The panel's layout manager asks each button how big that button prefers to be
3) The panel's layout manager uses its layout policies to decide whether it should respect all, part of, or none of the buttons's preferences
4) Add the panel to a frame
5) The frame's layout manager asks the panel how big the panel prefers to be 
6) The frame's layout manager uses its layout policies to decide whether it should respect all, part of, or none of the panel's preferences

### Types of Layer Managers
1) BorderLayout: 
    - Divides a background component into five regions (east, west, north, south, and center). You can only add one component per region to a background controlled by BorderLayout manager 
    - Components laid out by this manager usually don't get to have their preferred size
    - In the java.awt.*; package
    - **This is the default layout manager for a frame**
2) FlowLayout: 
    - Each component is the size it wants to be, and they're laid out left to right in the order that they're added, with "word wrap" turned on, so when a component won't fit horizontally, it drops the next "line" in the layout
    - **This is the default layout manager for a panel**
3) BoxLayout:
    - Each component is the size it wants to be, and components are placed in the order they were added in
    - However, unlike FlowLayout, components can be stacked horizontally or vertically
    - Instead of having a "word wrap," you can insert a "component return key" that forces the components to start a new line