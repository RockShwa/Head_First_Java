public class BankAccount {
    // The syncronized spend method ensures that the SAME instance of BankAccount that Ryan
    // and Monica are fighting over does not get overdrawn. Becuase we know spend is synchronized,
    // we don't need to synhcronize the data in goShopping() in RyanAndMonicaJob.java
    private int balance = 100;
    
    public int getBalance() {
        return balance;
    }

    public synchronized void spend(int amount) {
        balance -= amount;
        if (balance < 0) {
            System.out.println("Overdrawn!");
        }
    }
}
