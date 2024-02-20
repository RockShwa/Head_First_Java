package MiniMusicPlayer;
import javax.sound.midi.*;
import static javax.sound.midi.ShortMessage.*;
import java.util.Random;
import java.awt.*;
import javax.swing.*;


public class MiniMusicPlayerV1 {
    private MyDrawPanel panel;
    private Random random = new Random();
    
    public static void main(String[] args) {
        MiniMusicPlayerV1 mini = new MiniMusicPlayerV1();
        mini.go();
    }

    public void setUpGUI() {
        JFrame frame = new JFrame("My First Music Video");
        panel = new MyDrawPanel();
        frame.setContentPane(panel);
        frame.setBounds(30, 30, 300, 300);
        frame.setVisible(true);
    }

    public void go() {
        setUpGUI();

        try {
            Sequencer sequencer = MidiSystem.getSequencer(); // Make and open a Sequencer
            sequencer.open();

            // registers for events with the sequencer, array represents the list of controller events, we care about event #127
            sequencer.addControllerEventListener(panel, new int[] {127});

            Sequence seq = new Sequence(Sequence.PPQ, 4); // Make a sequence and a track
            Track track = seq.createTrack(); 

            // Makes a bunch of events to make the notes keep going up (from oiano note 5 to piano note 60)
            for (int i = 5; i < 61; i += 4) {
                track.add(makeEvent(NOTE_ON, 1, i, 100, i));
                /* Here's how we pick up the beat:
                 * we insert our own ControllerEvent (CONTROL_CHANGE) with an argument for event
                 * number 127. This event will do NOTHING, we put it in just so that we can get an event
                 * each time a note is played. "Fires" at the same time as the NOTE_ON beat (same tick)
                 */
                track.add(makeEvent(CONTROL_CHANGE, 1, 127, 0, i));
                track.add(makeEvent(NOTE_OFF, 1, i, 100, i + 2));
            }

            sequencer.setSequence(seq);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // tick = when this message should happen
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

    // Build a frame, add a drawing panel, and each time we get an event, we redraw a rectange
    // make this implement ControolerEventListener rather than the program itself, so when drawing
    // panel gets the event, it knows how to tkae care of itself by drawing the rectangle
    public class MyDrawPanel extends JPanel implements ControllerEventListener {
        private boolean msg = false;

        public void controlChange(ShortMessage event) {
            msg = true; 
            repaint();
        }

        public void paintComponent(Graphics g) {
            if (msg) { // only want ControllerEvents to repaint
                int r = random.nextInt(250);
                int gr = random.nextInt(250);
                int b = random.nextInt(250);

                g.setColor(new Color(r, gr, b));

                int height = random.nextInt(120) + 10;
                int width = random.nextInt(120) + 10;

                int xPos = random.nextInt(40) + 10;
                int yPos = random.nextInt(40) + 10;

                g.fillRect(xPos, yPos, width, height);
                msg = false;
            }
        }
    }
    
}
