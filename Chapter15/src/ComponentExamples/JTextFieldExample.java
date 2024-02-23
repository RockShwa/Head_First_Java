package ComponentExamples;
import javax.swing.JTextField;
// import javafx.event.ActionEvent;


public class JTextFieldExample {
    public static void main(String[] args) {
        // Constructors:
    JTextField field1 = new JTextField("Your name");
    JTextField field2 = new JTextField(20); // means 20 columns (not pixels), preferred width

    // How to Use:
    // 1) Get text out
    System.out.println(field1.getText());

    // 2) Put text in 

    field2.setText("whatever");
    field2.setText("");

    // 3) Get an ActionEvent when the user presses return or enter
    // field1.addActionListener(myActionListener);

    // 4) Select/Highlight the text in the field
    field1.selectAll();

    // 5) Put cursor back in the field (so the user can just start typing)
    field1.requestFocus();
    }
}
