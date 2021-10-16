public class Account {
    private String name;
    private double apr;
    private double balance;

    public Account(String name, double apr, double balance) {
        this.name = name;
        this.apr = apr;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return apr;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double value) {
        balance += value;
    }

    public void withdraw(double value) {
        balance -= value;
        if (balance < 0) {
            balance = 0;
        }
    }
}

