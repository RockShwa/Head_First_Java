import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class DailyAdviceClient {
    // This program makes a SocketChannel, makes a BufferedReader (with help of the channel's Reader), 
    // and reads a single line from the server application (whatever's running at port 5000)

    public void go() {
        InetSocketAddress serverAddr = new InetSocketAddress("localhost", 5000);
        // uses try-with resources to close SocketChannel when complete
        try (SocketChannel socketChannel = SocketChannel.open(serverAddr)) {
            Reader channelReader = Channels.newReader(socketChannel, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(channelReader);

            // readLine() is the EXACT same as if you were using a BufferedReader chained to a file
            // in other words, by the time you call a BufferedReader method, the reader does not care where
            // the characters came from
            String advice = reader.readLine();
            System.out.println("Today you should: " + advice);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new DailyAdviceClient().go();
    }

}
