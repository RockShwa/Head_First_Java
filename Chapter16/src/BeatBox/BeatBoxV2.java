package BeatBox;
import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import static javax.sound.midi.ShortMessage.*;

public class BeatBoxV2 {
    private ArrayList<JCheckBox> checkBoxList; // store all checkboxes in an ArrayList
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
        new BeatBoxV2().buildGUI();
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

        JButton upTempo = new JButton("Tempo Up");
        // default tempo is 1, so this adjusts it +3% per click
        upTempo.addActionListener(e -> changeTempo(1.03f));
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("Tempo Down");
        // default tempo is 1, so this adjusts it -3% per click
        downTempo.addActionListener(e -> changeTempo(0.97f));
        buttonBox.add(downTempo);

        // These buttons don't make any visible change, not sure how to save/restore
        JButton savePattern = new JButton("Serialize Pattern");
        savePattern.addActionListener(e -> writeFile());
        buttonBox.add(savePattern);

        JButton loadFile = new JButton("Restore Pattern");
        loadFile.addActionListener(e -> readFile());
        buttonBox.add(loadFile);

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
}
