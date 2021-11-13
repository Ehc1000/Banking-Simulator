package banking;

public class SavingsAccount extends Account {
    public SavingsAccount(String name, String id, double apr) {
        super(name, id, apr);
    }

    @Override
    public boolean checkDepositAmount(double value) {
        if (value >= 0.0 && value <= 2500.00) {
            return true;
        }
        return false;
    }
}
