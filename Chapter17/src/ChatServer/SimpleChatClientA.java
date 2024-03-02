package ChatServer;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;

public class SimpleChatClientA {
    // Type a messge, then press Send to send it to the server. This version can only send, and
    // can not recieve other messages from the server (from other clients)
    private JTextField outgoing;
    private PrintWriter writer;

    public void go() {
        // Call setUpNetworking method, set up GUI, and register a listener with send button
        setUpNetworking();

        outgoing = new JTextField(20);

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> sendMessage());

        JPanel mainPanel = new JPanel();
        mainPanel.add(outgoing);
        mainPanel.add(sendButton);
        JFrame frame = new JFrame("Ludicrously Simple Chat Client");
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(400, 100);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void setUpNetworking() {
        // open a SocketChannel to the server
        // make a PrintWriter and assign to writer instance variable
        try {
            InetSocketAddress serverAddress = new InetSocketAddress("localhost", 5000);

            SocketChannel socketChannel = SocketChannel.open(serverAddress);
            writer = new PrintWriter(Channels.newWriter(socketChannel, StandardCharsets.UTF_8.name()));
            System.out.println("Networking established.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        // get the text from the text field and send it to the server using the writer (PrintWriter)
        writer.println(outgoing.getText());
        writer.flush();
        outgoing.setText("");
        outgoing.requestFocus();
    }

    public static void main(String[] args) {
        new SimpleChatClientA().go();
    }
}
