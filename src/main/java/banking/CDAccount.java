package banking;

public class CDAccount extends Account {
    private int month;

    public CDAccount(String name, String id, double apr, double balance) {
        super(name, id, apr, balance);
    }

    @Override
    public boolean checkDepositAmount(double value) {
        return false;
    }

    @Override
    public boolean checkWithdrawalAmount(double value) {
        if (month >= 12) {
            if (value == getBalance()) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public void incrementMonth(int month) {
        this.month += month;
    }

}

