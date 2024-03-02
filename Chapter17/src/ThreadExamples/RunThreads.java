package ThreadExamples;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunThreads {
    // We do not know when the threads will run (bc JVM schedules it), the two jobs will likely run 
    // parallel, but no garauntee that this means they will complete in the same amount of time or
    // output values at the same rate
    public static void main(String[] args) {
        // Creates an ExecutorService with a fixed-size thread pool 
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        threadPool.execute(() -> runJob("Job 1"));
        threadPool.execute(() -> runJob("Job 2"));
        threadPool.shutdown();
    }

    public static void runJob(String jobName) {
        for (int i = 0; i < 25; i++) {
            String threadName = Thread.currentThread().getName();
            System.out.println(jobName + " is running on " + threadName);
        }
    }
}
