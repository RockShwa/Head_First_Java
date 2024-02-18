import javax.swing.*;
import java.awt.event.*;

public class GUIListenerAndEvent implements ActionListener{
    private JButton button;

    public static void main(String[] args) {
        GUIListenerAndEvent gui = new GUIListenerAndEvent();
        gui.go();
    }

    public void go() {
        JFrame frame = new JFrame();
        button = new JButton("click me");

        // Registers interest with button, object you pass MUST be from a class that implements ActionListener
        button.addActionListener(this); 

        frame.getContentPane().add(button);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        button.setText("I've been clicked");
    }
    
}
