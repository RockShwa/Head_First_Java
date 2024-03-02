package ThreadExamples;
import java.util.concurrent.*;

public class PredictableLatch {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // Creates a new CountDownLatch, this lets us "wait for the signal." We have one event we 
        // want to wait for (the maiin thread prints its message), so we set this with the value of 1 
        CountDownLatch latch = new CountDownLatch(1);

        executor.execute(() -> waitForLatchThenPrint(latch));

        System.out.println("back in main");
        latch.countDown();

        executor.shutdown();
    }

    private static void waitForLatchThenPrint(CountDownLatch latch) {
        // wait for main thread to print out its message
        // this thread will be in a non-runnable state while it's waiting
        // this is WAY more efficient and is very accurate 
        try {
            latch.await(); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("top o' the stack");
    }
}
