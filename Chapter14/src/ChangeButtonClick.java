import javax.swing.*;
import java.awt.*;
// import java.awt.event.*; //uncomment to use inner classes

public class ChangeButtonClick { // Doesn't have to implement ActionListener because the inner classes/lambdas do!
    private JFrame frame;
    private JLabel label;
    
    public static void main(String[] args) {
        ChangeButtonClick gui = new ChangeButtonClick();
        gui.go();
    }

    public void go() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton labelButton = new JButton("Change Label");
        //labelButton.addActionListener(new LabelListener()); // inner class; pass a new instance of appropriate listener class
        labelButton.addActionListener(event -> label.setText("Ouch!"));
        // Since ActionListener is a Functional Interface, a lambda can implement the interface's one and only abstract method

        JButton colorButton = new JButton("Change Circle");
        //colorButton.addActionListener(new ColorListener()); //inner class

        label = new JLabel("I'm a label");
        ButtonClickDrawPanel drawPanel = new ButtonClickDrawPanel();

        frame.getContentPane().add(BorderLayout.SOUTH, colorButton);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.getContentPane().add(BorderLayout.EAST, labelButton);
        frame.getContentPane().add(BorderLayout.WEST, label);

        frame.setSize(500, 400);
        frame.setVisible(true);
    }

    // Can use inner classes, but lambdas are clearer
    // class LabelListener implements ActionListener {
    //     public void actionPerformed(ActionEvent event) {
    //         label.setText("Ouch"); // inner class knows about label
    //     }
    // }

    // class ColorListener implements ActionListener {
    //     public void actionPerformed(ActionEvent event) {
    //         frame.repaint(); 
    //         // when user clicks the button, tells frame to repaint itself (new random color)
    //         // calls it on every widget in the frame
    //     }
    // }
}
