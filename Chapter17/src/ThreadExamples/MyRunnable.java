package ThreadExamples;

public class MyRunnable implements Runnable{
    // The only method you have to implement, this where you put the thread job
    public void run() {
        go();
    }

    public void go() {
        doMore();
    }

    public void doMore() {
        System.out.println(Thread.currentThread().getName() + ": top o' the stack");
        // dumpStack will output the current call stack, just like an Exceptions stack trace. Using
        // it here will show us the current stack, but only use for debugging (slows code down)
        Thread.dumpStack();
    }

    // DO NOT DO THIS:
    // This is not enough to create a new call stack, since the run() method is called from main(),
    // so when run() is called, it joins the main() stack, not a new stack!
    // public static void main(String[] args) {
    //     MyRunnable runnable = new MyRunnable();
    //     runnable.run();
    //     System.out.println(Thread.currentThread().getName() + ": back in main");
    //     Thread.dumpStack();
    // }
}
