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
        if (account.getBalance() >= amount) {
            System.out.println(name + " is about to spend");
            account.spend(amount);
            System.out.println(name + " finishes spending");
        } else {
            System.out.println("Sorry, not enough for " + name);
        }
    }
}
