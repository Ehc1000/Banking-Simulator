package banking;

public abstract class CommandValidator {

    protected Bank bank;


    public CommandValidator(Bank bank) {
        this.bank = bank;
    }

    public abstract boolean validate(String command);

    public boolean validateCommand(String commandType) {
        if (commandType.equalsIgnoreCase("create")
                || commandType.equalsIgnoreCase("deposit")
                || commandType.equalsIgnoreCase("withdraw")
                || commandType.equalsIgnoreCase("transfer")
                || commandType.equalsIgnoreCase("pass")) {
            return true;
        }
        return false;
    }

    public boolean validateAccountType(String accountType) {
        if (accountType.equalsIgnoreCase("checking")
                || accountType.equalsIgnoreCase("savings")
                || accountType.equalsIgnoreCase("cd")) {
            return true;
        }
        return false;
    }

    public boolean validateId(String commandType, String id) {
        boolean validId = validateNumericAndEightInLength(id);
        if (commandType.equalsIgnoreCase("create")) {
            if (AccountDoesNotExist(id) && validId) {
                return true;
            }
            return false;
        }
        if (commandType.equalsIgnoreCase("deposit")) {
            if (AccountExists(id) && validId) {
                return true;
            }
            return false;
        }
        if (commandType.equalsIgnoreCase("withdraw")) {
            if (AccountExists(id) && validId) {
                return true;
            }
            return false;
        }
        if (commandType.equalsIgnoreCase("transfer")) {
            if (AccountExists(id) && validId) {
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean validateNumericAndEightInLength(String id) {
        if (id.matches("[0-9]+")) {
            if (id.length() == 8) {
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean AccountDoesNotExist(String id) {
        if (bank.accountExistsByID(id)) {
            return false;
        }
        return true;
    }

    private boolean AccountExists(String id) {
        if (bank.accountExistsByID(id)) {
            return true;
        }
        return false;
    }

    public boolean validateApr(String apr) {
        if (apr.matches("[a-zA-Z]+")) {
            return false;
        }
        Double rate = Double.parseDouble(apr);
        if (rate >= 0.0 && rate <= 10.0) {
            return true;
        }
        return false;
    }
}
