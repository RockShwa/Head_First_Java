package BeatBox;
import javax.sound.midi.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;
import static javax.sound.midi.ShortMessage.*;

public class BeatBoxV3 {
    private JList<String> incomingList;
    private JTextArea userMessage;
    private ArrayList<JCheckBox> checkBoxList; // store all checkboxes in an ArrayList

    private Vector<String> listVector = new Vector<>(); // This uses object locks
    private HashMap<String, boolean[]> otherSeqsMap = new HashMap<>();

    private String userName;
    private int nextNum;

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;

    private Sequencer sequencer;
    private Sequence sequence;
    private Track track;
    private JFrame frame;

    // String array of all instrument names for the labels
    String[] instrumentNames = {"Bass Drum", "Closed Hi-Hat", 
            "Open Hi-Hat", "Acoustic Snare", "Crash Cymbal", "Hand Clap", 
            "High Tom", "Hi-Bongo", "Maracas", "Whistle", " Low Conga", 
            "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo", "Open Hi Conga"};
    // These are the actual drum keys
    int[] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("You didn't pass anything to the command line!");
        } else {
            new BeatBoxV3().startUp(args[0]); // command line argument for screen name
        }
    }

    public void startUp(String name) {
        userName = name;
        // open up connection to server (Using Sockets because they work better with I/O Streams)
        try {
            socket = new Socket("127.0.0.1", 4242);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(new RemoteReader()); 
        } catch (Exception ex) {
            System.out.println("Couldn't connect-you'll have to play alone.");
        }
        setUpMidi();
        buildGUI();
    }

    public void buildGUI() {
        frame = new JFrame("Cyber BeatBox");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        // Gives us a margin between the edges of the panel and where the components are placed
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Box buttonBox = new Box(BoxLayout.Y_AXIS);
        JButton start = new JButton("Start");
        start.addActionListener(e -> buildTrackAndStart());
        buttonBox.add(start);

        JButton stop = new JButton("Stop");
        stop.addActionListener(e -> sequencer.stop());
        buttonBox.add(stop);

        JButton clear = new JButton("Clear All");
        clear.addActionListener(e -> clearAll());
        buttonBox.add(clear);

        JButton upTempo = new JButton("Tempo Up");
        // default tempo is 1, so this adjusts it +3% per click
        upTempo.addActionListener(e -> changeTempo(1.03f));
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("Tempo Down");
        // default tempo is 1, so this adjusts it -3% per click
        downTempo.addActionListener(e -> changeTempo(0.97f));
        buttonBox.add(downTempo);

        JButton randomPattern = new JButton("Random Pattern");
        randomPattern.addActionListener(e -> generateRandomPattern());
        buttonBox.add(randomPattern);

        // These buttons don't make any visible change, not sure how to save/restore
        JButton savePattern = new JButton("Serialize Pattern");
        savePattern.addActionListener(e -> writeFile());
        buttonBox.add(savePattern);

        JButton loadFile = new JButton("Restore Pattern");
        loadFile.addActionListener(e -> readFile());
        buttonBox.add(loadFile);

        JButton sentIt = new JButton("SendIt");
        sentIt.addActionListener(e -> sendMessageAndTracks()); // send message/current beat to server
        buttonBox.add(sentIt);

        userMessage = new JTextArea();
        userMessage.setLineWrap(true);
        userMessage.setWrapStyleWord(true);
        JScrollPane messageScroller = new JScrollPane(userMessage);
        buttonBox.add(messageScroller);

        // This is where the incoming messages are displayed, lets you look and select messages
        incomingList = new JList<>();
        incomingList.addListSelectionListener(new MyListSelectionListener());
        incomingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane theList = new JScrollPane(incomingList);
        buttonBox.add(theList);
        incomingList.setListData(listVector);

        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (String instrumentName : instrumentNames) {
            JLabel instrumentLabel = new JLabel(instrumentName);
            // border on each instrument name helps line them up with checkboxes
            instrumentLabel.setBorder(BorderFactory.createEmptyBorder(4, 1, 4, 1));
            nameBox.add(instrumentLabel);
        }

        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);

        frame.getContentPane().add(background);

        // Another layout manager; lets you put components in a grid with rows and columns
        GridLayout grid = new GridLayout(16, 16); 
        grid.setVgap(1);
        grid.setHgap(2);

        JPanel mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);

        checkBoxList = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            // Add to ArrayList AND GUI Panel
            checkBoxList.add(c);
            mainPanel.add(c);
        }

        setUpMidi();

        frame.setBounds(50, 50, 300, 300);
        frame.pack();
        frame.setVisible(true);
    }

    private void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildTrackAndStart() {
        // we'll make a 16 element array to hold the values for one instrument, across all 16 beats
        // if the instrument is sipposed to play on that beat, the value at that element will be the 
        // key. If instrument not supposed to play on that beat, put in a zero
        int[] trackList;
        
        // Make a fresh, new track
        sequence.deleteTrack(track);
        track = sequence.createTrack();

        // Do this for each of the 16 rows (i.e Bass, Congo, etc.)
        for (int i = 0; i < 16; i++) {
            trackList = new int[16];
            int key = instruments[i]; // hold the key that represents which instrument this is (actual MIDI number)

            // Do this for each of the BEATS in this row
            for (int j = 0; j < 16; j ++) {
                JCheckBox jc = checkBoxList.get(j + 16 * i);
                if (jc.isSelected()) {
                    trackList[j] = key;
                } else {
                    trackList[j] = 0;
                }
            }

            // for this instrument and all 16 beats, make events and add them to the track
            makeTracks(trackList);
            track.add(makeEvent(CONTROL_CHANGE, 1, 127, 0, 16));
        }

        // we always want to make sure that there is an event at beat 16 (it goes 0-15). Otherwise, 
        // the BeatBox may not go to the full 16 beats before it starts over
        track.add(makeEvent(PROGRAM_CHANGE, 9, 1, 0, 15));

        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.setTempoInBPM(120);
            sequencer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // tempoFactor scale sthe sequencer's tempo by the facor provided, slowing or speeding up beat
    private void changeTempo(float tempoMultiplier) {
        float tempoFactor = sequencer.getTempoFactor();
        sequencer.setTempoFactor(tempoFactor * tempoMultiplier);
    }

    private void sendMessageAndTracks() {
        boolean[] checkBoxState = new boolean[256];
        for (int i = 0; i < 256; i++) {
            JCheckBox check = checkBoxList.get(i);
            if (check.isSelected()) {
                checkBoxState[i] = true;
            }
        }
        try {
            out.writeObject(userName + nextNum++ + ": " + userMessage.getText());
            out.writeObject(checkBoxState);
        } catch (IOException e) {
            System.out.println("Terribly sorry. Could not send it to the server.");
        }
        userMessage.setText("");
    }

    private void saveDialogBox() {
        String[] saveOptions = {"Save", "Don't Save"};
        int selection = JOptionPane.showOptionDialog(null, null, "Do you want to save your current pattern?", 0, 2, null, saveOptions, saveOptions[0]);
        if (selection == 0) {
            writeFile();
        }
    }

    // This tells us when the user made a selection from the list of messages. We immediately load
    // the associated pattern (in the HashMap otherSeqMap) and start playing it
    public class MyListSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent lse) {
            saveDialogBox();
            String selected = incomingList.getSelectedValue();
            if (selected != null) {
                // now go to the map, and change the sequence
                boolean[] selectedState = otherSeqsMap.get(selected);
                changeSequence(selectedState);
                sequencer.stop();
                buildTrackAndStart();
            }
        }
    }

    // This method is called when the user selects something from the list. We immediatley change the
    // pattern to the one the selected
    private void changeSequence(boolean[] checkBoxState) {
        for (int i = 0; i < 256; i++) {
            JCheckBox check = checkBoxList.get(i);
            check.setSelected(checkBoxState[i]);
        }
    }

    private void generateRandomPattern() {
        for (JCheckBox check : checkBoxList) {
            check.setSelected(false);
        }
        for (int i = 0; i < 15; i++) {
            int r = (int) (Math.random() * 256);
            JCheckBox check = checkBoxList.get(r);
            check.setSelected(true);
        }
    }

    private void clearAll() {
        for (JCheckBox check : checkBoxList) {
            check.setSelected(false);
        }
    }
    // This makes events for one instument at a tiem, for all 16 beats. So it might get an int[]
    // for the Bass drum, and each index in the array will hold either the key of that instrument 
    // or a zero. If it's a zero, the instrument is not supposed to play at that beat. Otherwise, 
    // make an event and add it to the track
    private void makeTracks(int[] list) {
        for (int i = 0; i < 16; i++) {
            int key = list[i];

            if (key != 0) {
                track.add(makeEvent(NOTE_ON, 9, key, 100, i));
                track.add(makeEvent(NOTE_OFF, 9, key, 100, i + 1));
            }
        }
    }

    public static MidiEvent makeEvent(int command, int channel, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage msg = new ShortMessage();
            msg.setMessage(command, channel, one, two);
            event = new MidiEvent(msg, tick);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    // Save a Beatbox pattern
    private void writeFile() {
        boolean[] checkboxState = new boolean[256];

        for (int i = 0; i < 256; i++) {
            JCheckBox check = checkBoxList.get(i);
            if (check.isSelected()) {
                checkboxState[i] = true;
            }
        }

        JFileChooser fileSave = new JFileChooser();
        fileSave.showSaveDialog(frame);
        File saveFile = fileSave.getSelectedFile();

        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(saveFile))) {
            os.writeObject(checkboxState); //write/serialize the one boolean array
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    // Restore a Beatbox pattern
    private void readFile() {

        JFileChooser fileOpen = new JFileChooser();
        fileOpen.showOpenDialog(frame);
        File loadFile = fileOpen.getSelectedFile();

        boolean[] checkboxState = null;
        try(ObjectInputStream is = new ObjectInputStream(new FileInputStream(loadFile))) {
            checkboxState = (boolean[]) is.readObject(); // read the one object and return the one bool array
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 256; i++) {
            JCheckBox check = checkBoxList.get(i);
            check.setSelected(checkboxState[i]);
        }

        // Stop whatever is currently playing and rebuild the sequence 
        // using the new state of the chcekboxes in the Arraylist
        sequencer.stop();
        buildTrackAndStart();
    }

    public class RemoteReader implements Runnable {
        // This reads the data from the server (data is the name and checkbox state)
        public void run() {
            try {
                Object obj;
                while ((obj = in.readObject()) != null) {
                    System.out.println("got an object from the server");
                    System.out.println(obj.getClass());

                    // When message comes in, we deserialize the two objects which we want to add to
                    // the JList component
                    String nameToShow = (String) obj;
                    boolean[] checkBoxState = (boolean[]) in.readObject();
                    otherSeqsMap.put(nameToShow, checkBoxState);

                    // Adding to a JList is a two step thing: you keep a Vector of the lists data
                    // (Vector is an old fashioned ArrayList) and then tell the JList to use that 
                    // Vector as it's source for what to display in the list
                    listVector.add(nameToShow);
                    incomingList.setListData(listVector);
                }
            // Multi-catch: If you want to catch multiple exceptions but do the same thing with them
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
