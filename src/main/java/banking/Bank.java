package banking;

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

    public boolean accountExistsByID(String id) {
        if (accounts.get(id) != null) {
            return true;
        }
        return false;
    }

    public boolean checkAmount(String id, Double value) {
        if (accounts.get(id).checkDepositAmount(value)) {
            return true;
        }
        return false;
    }

    public boolean checkWithdrawAmount(String id, Double value) {
        if (accounts.get(id).checkWithdrawalAmount(value)) {
            return true;
        }
        return false;
    }
}