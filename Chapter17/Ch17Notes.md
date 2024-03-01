# Networking and Threads

## Chat Program Overview
- Each client has to know about the server
- The server has to know about ALL the clients
- How it works (Assuming there are 3 clients in the chat server):
    1) Client connects to the server (server is waiting for client requests) 
    2) The server makes a connection and adds the client to the list of participants
    3) Another client connects (server makes a connection and adds it to list of participants)
    4) Client A sends a message to the chat service
    5) Server distributes the message to ALL participants (including original sender)

### Connect
- To make a connection, you need to know two things about the server, where it is and which port it's running on (IP address and TCP port number)
- A connection is a relationship between two machines, where two pieces of software know about each other (know how to communicate with each other/send bits to each other)
- Networking Stack: it's just a way of looking at the layers that information (bits) must travel through to get from a Java program in a JVM on some OS, to physical hardware and back to another machine (this is low level stuff that the API takes care of, don't worry about this too much)
~~~ java
// Represents full address of the machine we want to connect to (IP address, TCP port number)
InetSocketAddress serverAddress = new InetSocketAddress("196.164.1.103", 5000);
// SocketChannel is what we use to talk to another machine
SocketChannel socketChannel = SocketChannel.open(serverAddress);
~~~
- TCP port: a 16-bit number that identifies a specific program on the server, they're just numbers that represent applications, they're not actual ports
    - A server can have up to 65,536 different server apps running, one per port
    - Common TCP Port numbers:
        - FTP = 20
        - Telnet = 23
        - SMTP = 25
        - Time = 37
        - HTTPS = 443
        - POP3 = 110
        - HTTP = 80 (Internet web)
    - These numbers are from 0-1023 and reserved for well-known services, don't use these for your own servers (yours can be any number from 1024-65535)

### Recieve
- To communicate over a remote connection, you can use I/O streams
- Reading from the network with a Buffered Reader
1) Make a connection to the server
~~~ java
// This IP address is the address for the localhost, the one the code is running on
// This is good to use when your're testing your code on a single machine
SocketAddress serverAddr = new InetSocketAddress("127.0.0.1", 5000);
SocketChannel socketChannel = SocketChannel.open(serverAddr);
~~~
2) Create or get a Reader from the connection 
~~~ java
// The Reader is the bridge between a low-level byte dtream (like the one comming from the channel), and a high-level character stream (like BufferedReader)
// You need to say which Charset to use for reading values from the network, UTF_8 is common
Reader reader = Channels.newReader(socketChannel, StandardCharsets.UTF_8);
~~~
3) Make a BufferedReader and read!
~~~ java
BufferedReader bufferedReader = new BufferedReader(reader);
String message = bufferedReader.readLine();
~~~

### Send
- Can use BufferedWriter (when writing lots of Strings) or PrintWriter (when just one String)
- Writing to the network with PrintWriter
1) Make a connection to the server
~~~ java
SocketAddress serverAddr = new InetSocketAddress("127.0.0.1", 5000);
SocketChannel socketChannel = SocketChannel.open(serverAddr);
~~~
2) Create or get a Writer from the connection
~~~ java
Writer writer = Channels.newWriter(socketChannel, StandardCharsets.UTF_8);
// Should use the same Charset for reading and writing
// writer acts as a bridge between the bytes and the characters
~~~
3) Make a PrintWriter and write (print) something
~~~ java
PrintWriter printWriter = new PrintWriter(writer);
writer.println("message to send");
writer.print("another message");
~~~

## Using Sockets (Alternative to Channel) 
- java.net.Socket
- Channels are better sometimes if working with lots of network connections or there is lots of data comming over those connections, Sockets are simpler
- Using a Socket:
    - You can get an InputStream or an OutputStream from a Socket, and read and write from it in a similar way to Channels
    ~~~ java
    // Instead of using an InetSocketAddress and opening a SocketChannel, you can create a Socket with a host and port number
    Socket chatSocket = new Socket("127.0.0.1", 5000);

    // To read from the Socket, we need to get an InputStream from the Socket
    InputStreamReader in = new InputStreamReader(chatSocket.getInputStream());

    BufferedReader reader = new BufferedReader(in);
    String message = reader.readLine();

    // To write to the socket, we need to get an OutputStream from the Socket, which we can chain to the PrintWriter
    PrintWriter writer = new PrintWriter(chatSocket.getOutputStream());

    writer.println("message to send");
    writer.print("another message");
    ~~~

## Writing a Simple Server Application
- ServerSocketChannel: waits for client requests (when client connects)
- SocketChannel: use for communication with client
1) Server application makes a ServerSocketChannel and binds it to a specific port
~~~ java
// This starts the server application listening for client requests coming in for port 5000
ServerSocketChannel serverChannel = ServerSocketChannel.open();
serverChannel.bind(new InetSocketAddress(5000));
~~~
2) Client makes a SocketChannel connected to server application
~~~ java
// Client knows IP address and port number
SocketChannel svr = SocketChannel.open(new InetSocketAddress("190.165.1.103", 5000));
~~~
3) Server makes a new SocketChannel to communicate with this client
~~~ java
// accept() method blocks (just sits there) while it's waiting for a client connection. When a client finally connects, the method returns a SocketChannel that knows how to communicate with this client
// the ServerSocketChannel can go back to waiting for other clients. The server has just one ServerSocketChannel, and a SocketChannel per client 
SocketChannel clientChannel = serverChannel.accept()
~~~