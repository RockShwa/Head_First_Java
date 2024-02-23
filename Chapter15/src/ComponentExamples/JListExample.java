package ComponentExamples;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

public class JListExample {
    JList<String> list;
    public static void main(String[] args) {
        JListExample gui = new JListExample();
        gui.go();
    }
    
    public void go() {
        JPanel panel = new JPanel();
        // Constructor:
        // takes an array of any object type, don't have to be Strings, but a String 
        // representation will appear on the list GUI
        String[] listEntries = {"alpha", "beta", "gamma", "delta", 
                                "epsilon", "zeta", "eta", "theta"};
        list = new JList<>(listEntries);

        // 1) Make it have a vertical scrollbar
        JScrollPane scroller = new JScrollPane(list);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(scroller);

        // 2) Set number of lines to show before scrolling
        list.setVisibleRowCount(4);

        // 3) Restrict user to selecting only ONE thing at a time
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 4) Register for list selection events
        //list.addListSelectionListener(this);
    }

    // 5) Handle events (find out which thing in the list was selected)
    // If you don't put the if statement, you'll get the event twice
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            String selection = list.getSelectedValue();
            System.out.println(selection);
        }
    }
}
