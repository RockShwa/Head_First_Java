package ChatServer;

public class MyRunnable implements Runnable{
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
}
