import javax.swing.*;
import java.awt.*;
public class BorderLayoutExample {
    public static void main(String[] args) {
        BorderLayoutExample gui = new BorderLayoutExample();
        gui.go();
    }

    public void go() {
        JFrame frame = new JFrame();
        JButton button = new JButton("click me");
        frame.getContentPane().add(BorderLayout.EAST, button);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }
}