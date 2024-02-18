import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChangeButtonClick implements ActionListener {
    private JFrame frame;
    
    public static void main(String[] args) {
        ChangeButtonClick gui = new ChangeButtonClick();
        gui.go();
    }

    public void go() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button = new JButton("Change colors");
        button.addActionListener(this);

        ButtonClickDrawPanel drawPanel = new ButtonClickDrawPanel();

        frame.getContentPane().add(BorderLayout.SOUTH, button);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        frame.repaint(); 
        // when user clicks the button, tells frame to repaint itself (new random color)
        // calls it on every widget in the frame
    }
}
