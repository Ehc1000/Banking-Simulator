package banking;

import java.util.ArrayList;
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

    public boolean checkDepAmount(String id, Double value) {
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

    public void passTime(int months) {
        ArrayList<String> CloseAccount = new ArrayList<String>();
        for (int i = 0; i < months; i++) {
            for (Account account : accounts.values()) {
                account.incrementMonth(1);
                if (account.getBalance() == 0.0) {
                    CloseAccount.add(account.getId());
                    continue;
                }
                if (account.getBalance() < 100) {
                    account.withdraw(25);
                }
                if (account.getName().equalsIgnoreCase("cd")) {
                    for (i = 0; i < 4; i++) {
                        applyApr(account);

                    }
                } else {
                    applyApr(account);
                }
                account.setWithdrawAvailability(true);
            }
            for (String id : CloseAccount) {
                accounts.remove(id);
            }
        }
    }


    private void applyApr(Account account) {
        double bonus = account.getBalance() * (account.getRate() / 100 / 12);
        account.deposit(bonus);
    }
}
