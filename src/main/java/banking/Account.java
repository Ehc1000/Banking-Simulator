package banking;

public abstract class Account {
    private String name;
    private String id;
    private double apr;
    private double balance;

    public Account(String name, String id, double apr, double balance) {
        this.name = name;
        this.id = id;
        this.apr = apr;
        this.balance = balance;
    }

    public Account(String name, String id, double apr) {
        this.name = name;
        this.id = id;
        this.apr = apr;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
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

    public abstract boolean checkDepositAmount(double value);
}

