package ComponentExamples;
import javax.swing.*;
import java.awt.*;

public class JTextAreaExample {
    public static void main(String[] args) {
        JPanel panel = new JPanel();
        JFrame frame = new JFrame();

        JButton button = new JButton("Just Click It");

        // Can have more than one line of text, have to stick it inside of a scrollPane to get it to scroll
        // Constructor:
        JTextArea text = new JTextArea(10, 20);
        text.setLineWrap(true);
        button.addActionListener(e -> text.append("button clicked \n"));
        
        // How to use:
        // 1) Make it a vertical scrollbar only
        JScrollPane scroller = new JScrollPane(text);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(scroller);

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.SOUTH, button);

        frame.setSize(350, 300);
        frame.setVisible(true);

        // 2) Replace the text that's in it
        // text.setText("Not all who are lost wandering");

        // 3) Append to the text that's in it
        // text.append("button clicked");

        // 4) Select/Highlight text in the field
        // text.selectAll();

        // 5) Put the cursor back in the field (so the user can just start typing)
        // text.requestFocus();
    }
}
