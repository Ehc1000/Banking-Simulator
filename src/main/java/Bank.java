import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts;
    private String Id;

    Bank() {
        accounts = new HashMap<>();
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void addAccount(String Id, String name, Double apr, Double balance) {
        accounts.put(Id, new Account(name, apr, balance));
    }

}