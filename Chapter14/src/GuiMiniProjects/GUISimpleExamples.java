package GuiMiniProjects;
import javax.swing.*;

public class GUISimpleExamples {
    public static void main(String[] args) {
        // 1) Make a new JFrame
        JFrame frame = new JFrame();

        // 2) Make a widget
        JButton button = new JButton("click me");

        // 3) Add widget to frame; you don't add things to the frame directly, think of the frame as
        // the trim around the window, and you add things to the window pane
        frame.getContentPane().add(button); // Button fills the whole space here

        // 4) Display it(give it a size and make it visible)
        frame.setSize(300, 300);
        frame.setVisible(true);
        
    }

    public void changeIt(JButton button) {
        button.setText("I've been clicked!");
    }
}