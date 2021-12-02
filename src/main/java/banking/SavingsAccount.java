package banking;

public class SavingsAccount extends Account {
    private int month;
    private boolean withdrawAvailability = true;

    public SavingsAccount(String name, String id, double apr) {
        super(name, id, apr);
    }

    @Override
    public void setWithdrawAvailability(boolean flag) {
        withdrawAvailability = flag;
    }

    @Override
    public void withdraw(double value) {
        balance -= value;
        if (balance < 0) {
            balance = 0;
        }
        withdrawAvailability = false;
    }

    @Override
    public boolean checkDepositAmount(double value) {
        if (value >= 0.0 && value <= 2500.00) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkWithdrawalAmount(double value) {
        if (value >= 0.0 && value <= 1000.00) {
            if (withdrawAvailability) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public void incrementMonth(int month) {
        this.month += 1;
    }
}
