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
