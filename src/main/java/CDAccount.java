public class CDAccount extends Account {
    public CDAccount(String name, String id, double apr, double balance) {
        super(name, id, apr, balance);
    }

    @Override
    public boolean checkDepositAmount(double value) {
        return false;
    }
}

