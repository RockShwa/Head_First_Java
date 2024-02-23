package ComponentExamples;
import javax.swing.JCheckBox;
import java.awt.event.*;

public class JCheckBoxExample {
    private JCheckBox check;
    public static void main(String[] args) {
        JCheckBoxExample gui = new JCheckBoxExample();
        gui.go();
    }

    public void go() {
        // Constructor:
        check = new JCheckBox("Goes to 11");
        // How to use it:
        // 1) Listen for an item event (when it's selected or deselected)
        // check.addItemListener(this);

        // can deselect/select in code
        // check.setSelected(true);
        // check.setSelected(false);

    }

    // 2) Handle the event (and find out whether or not it's selected)
    public void itemStateChanged(ItemEvent e) {
        String onOrOff = "off";
        if (check.isSelected()) {
            onOrOff = "on";
        }
        System.out.println("Check box is " + onOrOff); 
    }


}
