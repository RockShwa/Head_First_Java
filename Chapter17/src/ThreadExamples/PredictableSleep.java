package ThreadExamples;

import java.util.concurrent.*;

public class PredictableSleep {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> sleepThenPrint());
        System.out.println("back in main");
        executor.shutdown();
    }

    private static void sleepThenPrint() {
        // This helps us have a more reliable output, where back in main is generally printed first,
        // and then top o' the stack
        try {
        // Calling sleep here will force the new thread to leave the currently running state. 
        // The main thread will get the chance to print out "back in main"
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // ~2 seconds before we get to this line
        System.out.println("top o'the stack");
    }
    
}
