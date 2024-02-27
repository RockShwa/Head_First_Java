package QuizCard;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class QuizCardPlayer {
    // Has a File menu with a "Load" option for loading a set of cards from a text file
    private ArrayList<QuizCard> cardList;
    private int currentCardIndex;
    private QuizCard currentCard;
    private JTextArea display;
    private JFrame frame;
    private JButton nextButton;
    private boolean isShowAnswer; // I think this just means if nextButton is displaying Show Answer

    public static void main(String[] args) {
        QuizCardPlayer reader = new QuizCardPlayer();
        reader.go();
    }

    public void go() {
        frame = new JFrame("Quiz Card Player");
        JPanel mainPanel = new JPanel();
        Font bigFont = new Font("sanserif", Font.BOLD, 24);

        display = new JTextArea(10, 20);
        display.setFont(bigFont);
        display.setLineWrap(true);
        display.setEditable(false);

        JScrollPane scroller = new JScrollPane(display);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(scroller);

        JButton nextButton = new JButton("Show Question");
        nextButton.addActionListener(e -> nextCard());
        mainPanel.add(nextButton);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadMenuItem = new JMenuItem("Load Card Set");
        loadMenuItem.addActionListener(e -> open()); // menu items can fire ActionListeners
        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);

        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(500, 400);
        frame.setVisible(true);
    }

    public void nextCard() {
        if (isShowAnswer) {
            //show the answer because they've seen the question
            display.setText(currentCard.getAnswer());
            nextButton.setText("Next Card");
            isShowAnswer = false;
        } else { // show next question
            if (currentCardIndex < cardList.size()) {
                showNextCard();
            } else {
                // there are no more cards!
                display.setText("That was the last card");
                nextButton.setEnabled(false);
            }
        }
    }

    private void open() {
        // Bring up file dialog box and let user chose file
        JFileChooser fileOpen = new JFileChooser();
        fileOpen.showOpenDialog(frame);
        loadFile(fileOpen.getSelectedFile());
    }

    private void loadFile(File file) {
        cardList = new ArrayList<>();
        currentCardIndex = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            // read one line at a time, passing the line to the makeCard() method that parses it
            // and turns it into a read QuizCard and adds it to the ArrayList
            while ((line = reader.readLine()) != null) {
                makeCard(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Couldn't write the cardList out: " + e.getMessage());
        }
        showNextCard(); // time to start, show next card!
    }

    private void makeCard(String lineToParse) {
        // Each line of text corresponds to a single flashcard, but we have to parse out the 
        // question and answer as seperate pieces. split() method breaks the line into two tokens, 
        // one for the question and one for the answer
        String[] result = lineToParse.split("/");
        QuizCard card = new QuizCard(result[0], result[1]);
        cardList.add(card);
        System.out.println("Made a Card");
    }

    private void showNextCard() {
        currentCard = cardList.get(currentCardIndex);
        currentCardIndex ++;
        display.setText(currentCard.getQuestion());
        nextButton.setText("Show Answer");
        isShowAnswer = true;
    }
}
