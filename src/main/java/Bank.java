import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts;

    Bank() {
        accounts = new HashMap<>();
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void addAccount(String id, String name, double apr, double balance) {
        accounts.put(id, new CDAccount(name, id, apr, balance));
    }

    public void addAccount(String id, String name, double apr) {
        if (name.equalsIgnoreCase("checking")) {
            accounts.put(id, new CheckingAccount(name, id, apr));
        }
        if (name.equalsIgnoreCase("savings")) {
            accounts.put(id, new SavingsAccount(name, id, apr));
        }
    }

    public boolean accountExistsByID(String Id) {
        if (accounts.get(Id) != null) {
            return true;
        }
        return false;
    }

    public boolean checkAmount(String id, Double value) {
        if (accounts.get(id).getName().equalsIgnoreCase("checking")) {
            if (value >= 0.0 && value <= 1000.00) {
                return true;
            }
            return false;
        }
        if (accounts.get(id).getName().equalsIgnoreCase("savings")) {
            if (value >= 0.0 && value <= 2500.00) {
                return true;
            }
            return false;
        }
        return false;
    }
}