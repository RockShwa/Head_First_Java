import java.util.concurrent.*;

public class RyanAndMonicaTest {
    // The problem: Ryan and Monica are a couple with one bank account; they both agree to check their
    // balance before spending money. Lets say the balance is 100, and Ryan wants to spend 50, he checks
    // the balance, and sees he's all good to go, and proceeds to checkout. Monica, at the same time, 
    // wants to spend 100, checks the balance (before Ryan's pushed pay) and sees she's good too. At the 
    // end of the day though, they're over the amount, and VERY angry with each other (no NVC going on)

    public static void main(String[] args) {
        // Only ONE instance of bank account, so both threads have access to the same account
        BankAccount account = new BankAccount();
        RyanAndMonicaJob ryan = new RyanAndMonicaJob("Ryan", account, 50);
        RyanAndMonicaJob monica = new RyanAndMonicaJob("Monica", account, 100);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(ryan);
        executor.execute(monica);
        executor.shutdown();
    }
}
