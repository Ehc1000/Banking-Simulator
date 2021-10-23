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
        accounts.put(id, new Account(name, id, apr, balance));
    }

    public void addAccount(String id, String name, double apr) {
        accounts.put(id, new Account(name, id, apr, 0));
    }
}