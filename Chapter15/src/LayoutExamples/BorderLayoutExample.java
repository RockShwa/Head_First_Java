package LayoutExamples;
import javax.swing.*;
import java.awt.*;
public class BorderLayoutExample {
    public static void main(String[] args) {
        BorderLayoutExample gui = new BorderLayoutExample();
        gui.go();
    }

    public void go() {
        JFrame frame = new JFrame();
        JButton button1 = new JButton("click me");
        frame.getContentPane().add(BorderLayout.EAST, button1);
        frame.setSize(200, 200);
        frame.setVisible(true);

        JButton button2 = new JButton("Click this!");
        frame.getContentPane().add(BorderLayout.NORTH, button2);
        // bigger font means the frame will allocate more space for the button's height
        Font bigFont = new Font("serif", Font.BOLD, 28); 
        button2.setFont(bigFont);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }
}