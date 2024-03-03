package ChatServer;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.*;
import java.nio.charset.StandardCharsets;

// TODO This is SUPER fragile code, make sure to come back and make it more robust

public class SimpleChatServer {
    private final List<PrintWriter> clientWriters = new ArrayList<>();

    public static void main(String[] args) {
        new SimpleChatServer().go();
    }

    public void go() {
        // Creates a thread pool, new threads created as needed, but reused when available
        ExecutorService threadPool = Executors.newCachedThreadPool();
        try {
            // Binds server to a specific port
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(5000));

            while (serverSocketChannel.isOpen()) {
                // Accept client requests to connect
                SocketChannel clientSocket = serverSocketChannel.accept();
                PrintWriter writer = new PrintWriter(Channels.newWriter(clientSocket, StandardCharsets.UTF_8.name()));
                clientWriters.add(writer);
                // returns whether or not the task was completed I think
                threadPool.submit(new ClientHandler(clientSocket));
                System.out.println("got a connection");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void tellEveryone(String message) {
        // Add the message to each client's writer to be sent to the server
        for (PrintWriter writer : clientWriters) {
            writer.println(message);
            writer.flush();
        }
    }

    public class ClientHandler implements Runnable{
        BufferedReader reader;
        SocketChannel socket;
    
        // Creates a reader to send messages back to client,
        public ClientHandler(SocketChannel clientSocket) {
            socket = clientSocket;
            reader = new BufferedReader(Channels.newReader(clientSocket, StandardCharsets.UTF_8.name()));
        }
    
        // seperate thread stack that reads the message line and sends it to the server (which distributes
        // it to the other clients)
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println("read " + message);
                    tellEveryone(message);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }    
}
