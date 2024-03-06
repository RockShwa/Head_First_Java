class RyanAndMonicaJob implements Runnable{
    private final String name;
    private final BankAccount account;
    private final int amountToSpend;
    
    RyanAndMonicaJob(String name, BankAccount account, int amountToSpend) {
        this.name = name;
        this.account = account;
        this.amountToSpend = amountToSpend;
    }

    public void run() {
        goShopping(amountToSpend);
    }

    private void goShopping(int amount) {
        // This syncronized means that we put a lock on the bank account inside the method that does
        // the banking transaction. This way, one thread gets to complete the whole transaction, start 
        // to finish, even if that thread is tkaen out of "running" state or another thread is
        // trying to make changes at exactly the same time
        // synchronized (account) {
            if (account.getBalance() >= amount) {
                System.out.println(name + " is about to spend");
                account.spend(amount);
                System.out.println(name + " finishes spending");
            } else {
                System.out.println("Sorry, not enough for " + name);
            }
        // }
    }
}
