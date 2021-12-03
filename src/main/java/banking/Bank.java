package banking;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Bank {
    public ArrayList<String> transactionHistory = new ArrayList<>();
    DecimalFormat decimalFormat = new DecimalFormat("0.00");
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
        ArrayList<String> CloseAccount = new ArrayList<>();
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
                    int j;
                    for (j = 0; j < 4; j++) {
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

    public List<String> getTransactionHistory(ArrayList<String> invalidCommands) {
        for (Account account : accounts.values()) {
            String type = account.getName().toLowerCase();
            String accountType = type.substring(0, 1).toUpperCase() + type.substring(1);
            transactionHistory.add(String.format("%s %s %s %s", accountType, account.getId(),
                    decimalFormat.format(account.getBalance()), decimalFormat.format(account.getRate())));
            if (account.getTransactionHistory().size() != 0) {
                transactionHistory.addAll(account.getTransactionHistory());
            }
        }
        transactionHistory.addAll(invalidCommands);
        return transactionHistory;
    }

    public void updateTransaction(String id, String command) {
        accounts.get(id).updateTransaction(command);
    }

}
