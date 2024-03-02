package ThreadExamples;

import java.util.concurrent.*;

public class ExecutorTester {
    public static void main(String[] args) {
        Runnable job = new MyRunnable();

        // Instead of creating a Thread instance, use a method on the Executor class to 
        // create an ExecutorService
        // This enables us to start one job
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(job);

        System.out.println(Thread.currentThread().getName() + ":back in main");
        Thread.dumpStack();
        executor.shutdown();
        // Make sure to shutdown the executor, otherwise the program will hand around waiting for
        // more jobs
    }
}
