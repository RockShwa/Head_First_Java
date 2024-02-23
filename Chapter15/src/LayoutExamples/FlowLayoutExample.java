package LayoutExamples;
import javax.swing.*;
import java.awt.*;

public class FlowLayoutExample {
    public static void main(String[] args) {
        FlowLayoutExample gui = new FlowLayoutExample();
        gui.go();
    }

    public void go() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);

        // Button will get preferred size in BOTH dimensions, because panel uses flow layout, 
        // and button is part of panel, not frame
        JButton button1 = new JButton("shock me");
        panel.add(button1);
        JButton button2 = new JButton("bliss");
        panel.add(button2);
        JButton button3 = new JButton("huh?");
        panel.add(button3);

        frame.getContentPane().add(BorderLayout.EAST, panel);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }
}
