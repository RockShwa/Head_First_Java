package LayoutExamples;
import javax.swing.*;
import java.awt.*;

public class BoxLayoutExample {
    // since FlowLayout is default of panel, you have to change it to BoxLayout
    public static void main(String[] args) {
        BoxLayoutExample gui = new BoxLayoutExample();
        gui.go();
    }

    public void go() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);

        // Use Y_AXIS for vertical stack and X_AXIS for horizontal stack (but just use FlowLayout )
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JButton button1 = new JButton();
        JButton button2 = new JButton();
        panel.add(button1);
        panel.add(button2);
        // have to use content pane because JFrame connects to underlying OS, 
        // and the content pane acts as a pane, or 100% java frame on top of the JFrame
        // can change default contentPane to a panel you created with:
            // myFrame.setContentPane(myPanel);
        frame.getContentPane().add(BorderLayout.EAST, panel);
        frame.setSize(250, 200);
        frame.setVisible(true);


    }
}
