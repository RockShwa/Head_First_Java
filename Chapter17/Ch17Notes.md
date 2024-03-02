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

## Multithreading & Threads
- Java has support for multiple threads built right into the fabric of the language (this just means you can have seperate processes going on at the same time)
    - Like how the client can scroll and write messages, and at the same time the server is reading and distributing messages
- Threads:
~~~ java
// This launches a seperate thread of execution with it's own call stack
// However, this Thread doesn't do anything, so it's stack dies, and so does the Thread object
Thread t = new Thread();
t.start();
~~~
- To use multithreading in Java, we need to look at both the thread and the job (lots of different ways to run multiple jobs in Java)
- thread: seperate thread of execution (different call stack); Thread: object that represents a thread of execution
- Every Java application starts up a main thread, the thread that puts the main() method on the bottom of the stack. You can start up different threads of your own for the JVM to execute
- Example:
~~~ java
// 1) The JVM calls the main() method
public static void main (Sting[] args) {
    // the active thread is main
}
// 2) main() starts a new thread. The main thread may be temporarily frozen while new thread starts running
Runnable r = new MyThreadJob();
Thread t = new Thread(r);
t.start();
Dog d = new Dog();
// t.start() is on the main thread, but then the user thread "t" is started and becomes the active thread
// 3) The JVM switches between the new thread (user thread A) and the original main thread, until both threads complete
// main() and Dog() on main thread
// run() and x.go() on user thread "t"
~~~
### The Runnable Interface
- Runnable is to a thread what a job is to a worker. Runnable is the job a thread is supposed to run
- A Runnable holds the method that goes on the bottom of the new call stack: run()
- To start a new call stack the thread needs a job, a job the thread will run once it's started
- Runnable has only one method (run()) which means you can use a lambda
- Making a whole new implements Runnable class is good for more complex tasks, while lambdas are good for simple, tiny jobs

## ExecutorService
- Implementations of this interface will execute jobs (Runnables)
- Behind the scenes, the ExecutorService will create, reuse, and kill threads in order to run these jobs
- Uses **factory methods** to create the ExecutorService instances we need
- Static factory methods can be used instead of constructors, they return exactly the implementation of an interface that we need, we don't need to know the concrete classes or how to create them

### Some Executor Methods
- **ExecutorService newCachedThreadPool()** - Creates a thread pool that creates new threads as needed, but will reuse previously constructed threads when they are available
- **ExecutorService newFixedThreadPool(int nThreads)** - Creates a thread pool that reuses a fixed number of threads operating off a shared unbounded queue.
- **ScheduledExecutorService newScheduledThreadPool(int corePoolSize)** - Creates a thread pool that can schedule commands to run after a given delay, or to execute periodically
- **ExecutorService newSingleThreadExecutor()** - Creates an Executor that uses a single worker thread operating off an unbounded queue
- **ScehduledExecutorService newSingleThreadScheduledExecutor()** - Creates a single-threaded executor that can schedule commands to run after a given delay, or to execute periodically
- **ExecutorService newWorkStealingPool()** - Creates a work-stealing thread pool using the number of available processors at its target parallelism level
- All of these methods use some form of ThreadPool

### ThreadPools
- This is a collection of Thread instanced that can be used (and reused) to perform jobs
- How many threads are in the pool, and what to do if there are more jobs to run than threads available, depends on the ExecutorService implementation
- When you create an ExecutorService, its pool may be started with some threads to begin with, or the pool may be empty
- You can use the pool's threads to run your job by giving the job to the ExecutorService, which then figures out if theres a free Thread to run the job (this means an ExecutorService can reuse threads)
- As you give the ExecutorService more jobs to run, it **may** create and start new Threads to handle the jobs. It **may** store the jobs in a queue if there are more jobs than Threads
- The ExecutorService may also terminate Threads that have been idle for some period of time, which helps minimize the amount of hardware resources (CPU, memory) your application needs

#### Closing time at the Thread Pool
- Although thread pools will take care of our individual Threads, we do need to responsiblu close the pool when we're finished so the pool can empty its job queue and shut down all its threads to free up system resources
1) ExecutorService.shutdown() - this asks the ExecutorService if it wouldn't mind wrapping things up so everyone can go home; all Threads that are currently running jobs are allowed to finish those jobs and any new jobs waiting in the queue will also be finished off. Any new jobs are rejected
    - You can also use awaitTermination() to sit and wait unitl your code is finished, you give it a max amount of time to wait for everything to end
2) ExecutorService.shutdownNow() - ExecutorService will try to stop any Threads that are running, will not run any waiting jobs, and won't let anyone else into the pool; sometimes called after shutdown() to give jobs the chance finish before pulling the plug

## Thread States
- A Thread has three states (and a kind of fourth), whether you create a new Thread and pass it a Runnable or use an Executor to execute a Runnable
1) **New** - A thread instance has been created, but not started; there is a thread object, but no thread of execution
2) **Runnable** - Moves to this state when you start the thread. This state means that the thread is ready to run and just waiting for it to be selected for execution. At this point, there is a new call stack for this thread
3) **Running** - This is where the Thread wants to be, and only the JVM thread scheduler can make the decision to run a thread; you can influence the JVM's decision to move a Thread from Runnable to Running, but you can not force it. In this state, a thread (and ONLY this thread) has an active call stack, and the method on top of the stack is executing
4) **Temporarily Not Runnable** - Once a thread is runnable, it can move back and forth between runnable, running, and temporarily nor runnable. The thread scheduler can move a running thread into a blocked state for a variety of reasons. This coudl happen becuse it's sleeping (waiting for another thread to finish), waiting for data to be available on a stream, waiting for an object's lock, etc.

## The Thread Scheduler
- The Thread Schediler makes all the decision about who runs and who doesen't. It usually makes the threads take turns, but there is no gaurantee. It might let on thread run as much as it wants while the other threads starve.
- You can not control the scheduler, so **do not base your program's correctness on the scheduler working in a particular way**
- This means you must write platform independent Java code, your multithreaded program must work no matter how the thread scheduler behaves
- Multithread programs are not deterministic, they don't run the same way every thime; even if the new thread is tiny, if it only has one line of code to run (like a lambda), it can still be interrupted by the thread scheduler

## Threads & Sleep
- One way to help your threads take turns is to put them to sleep periodically -> call static sleep(#ofMillisecs)
- This knocks the thread out of running state into runnable for the specificed milliseconds
- sleep() throws an InterupptedException, a checked exception, so all calls to sleep must be wrapped in try/catch (or declared)
- A thread will not wake up before timer, but it could wake up after the timer, becuase it's at the mercy of the thread scheduler
- Can use TimeUnit.MINUTES.sleep(2) instead of Thread.sleep(120000) -> both need try/catch
- Downsides of Sleep:
1) The program has to wait for at least that amount of time, which slows down the application a LOT if you use a lot of sleeps. And since when the thread wakes up it's at the mercy of the thread scheduler, our program will probably be hanging around for 2 seconds or more doing nothing
2) How do you know the other job will finish in that time?
- To address these downsides, to coordinate events happening on multiple threads, one thread may need to wait for a specific signal from anotehr before it can continue

### CountDownLatch, Barriers, CyclicBarrier and Phaser
- You can make threads count down when significant events have happened. A thread (or threads) can wait for all these events to complete before continuing
- CountDownLatch sets a number to count down from, then any thread can tell the latch to count down when a relevant event has happened
