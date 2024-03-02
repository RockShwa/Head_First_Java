package ThreadExamples;

public class ThreadTester {
    public static void main(String[] args) {
        // Pass the new Runnable instance to the new Thread constructor. This tells the thread what
        // job to run 
        Runnable threadJob = new MyRunnable();
        Thread myThread = new Thread(threadJob);

        // This is how you create the new thread stack
        myThread.start();

        System.out.println(Thread.currentThread().getName() + ": back in main");
        Thread.dumpStack();
    }
}
