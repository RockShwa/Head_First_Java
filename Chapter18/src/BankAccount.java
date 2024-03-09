import java.util.concurrent.atomic.AtomicInteger;;
public class BankAccount {
    private final AtomicInteger balance = new AtomicInteger(100);

    public int getBalance() {
        return balance.get(); // Use get() method for AtomicInteger
    }

    // not synchronized
    public void spend(String name, int amount) {
        int initialBalance = balance.get();
        if (initialBalance >= amount) {
            // This balance will not be changed if the initial balance does not match the actual balance right now
            boolean success = balance.compareAndSet(initialBalance, initialBalance - amount);
            if (!success) {
                // If success was false, tell Ryan and Monica it didn't work and they can decide what to do
                System.out.println("Sorry " + name + ", you haven't spent the money.");
            }
        } else {
            System.out.println("Sorry, not enough for " + name);
        }
    }
    // Synchronized way:
    // The syncronized spend method ensures that the SAME instance of BankAccount that Ryan
    // and Monica are fighting over does not get overdrawn. Becuase we know spend is synchronized,
    // we don't need to synhcronize the data in goShopping() in RyanAndMonicaJob.java
    // private int balance = 100;
    
    // public int getBalance() {
    //     return balance;
    // }

    // public synchronized void spend(int amount) {
    //     balance -= amount;
    //     if (balance < 0) {
    //         System.out.println("Overdrawn!");
    //     }
    // }
}
