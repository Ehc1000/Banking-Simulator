public class CheckingAccount extends Account {
    public CheckingAccount(String name, String id, double apr) {
        super(name, id, apr);
    }

    @Override
    public boolean checkDepositAmount(double value) {
        if (value >= 0.0 && value <= 1000.00) {
            return true;
        }
        return false;
    }


}

