public class CommandValidator {

    private Bank bank;


    public CommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        String commands[] = command.split(" ");
        String commandType = commands[0];
        String accountType = commands[1];
        if (commandType.equalsIgnoreCase("deposit")) {
            if (commands.length == 3) {
                if (validateDepositCommand(command)) {
                    return true;
                }
                return false;
            }
            return false;
        }
        if (commandType.equalsIgnoreCase("create")) {
            if (commands.length == 4) {
                if (accountType.equalsIgnoreCase("checking")
                        || accountType.equalsIgnoreCase("savings")) {
                    if (validateCreationForSavingsOrChecking(command)) {
                        return true;
                    }
                    return false;
                }
                return false;
            }
            if (commands.length == 5) {
                if (accountType.equalsIgnoreCase("cd")) {
                    if (validateCreationForCD(command)) {
                        return true;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private boolean validateCreationForCD(String command) {
        String commands[] = command.split(" ");
        String commandType = commands[0];
        String accountType = commands[1];
        String id = commands[2];
        String apr = commands[3];
        String amount = commands[4];

        boolean validCommand = validateCommand(commandType);
        boolean validAccountType = validateAccountType(accountType);
        boolean validId = validateId(commandType, id);
        boolean validApr = validateApr(apr);
        boolean validAmount = validateAmountForCreationCd(amount);

        if (validCommand && validAccountType && validId && validApr && validAmount) {
            return true;
        }
        return false;
    }

    private boolean validateAmountForCreationCd(String amount) {
        if (amount.matches("[a-zA-Z]+")) {
            return false;
        }
        Double value = Double.parseDouble(amount);
        if (value >= 1000.00 && value <= 10000.00) {
            return true;
        }
        return false;
    }

    private boolean validateCreationForSavingsOrChecking(String command) {
        String commands[] = command.split(" ");
        String commandType = commands[0];
        String accountType = commands[1];
        String id = commands[2];
        String apr = commands[3];

        boolean validCommand = validateCommand(commandType);
        boolean validAccountType = validateAccountType(accountType);
        boolean validId = validateId(commandType, id);
        boolean validApr = validateApr(apr);

        if (validCommand && validAccountType && validId && validApr) {
            return true;
        }
        return false;
    }

    private boolean validateDepositCommand(String command) {
        String commands[] = command.split(" ");
        String commandType = commands[0];
        String id = commands[1];
        String amount = commands[2];

        boolean validCommand = validateCommand(commandType);
        boolean validId = validateId(commandType, id);
        boolean validAmount = validateAmountForDeposit(id, amount);
        if (validCommand && validId && validAmount) {
            return true;
        }
        return false;
    }

    private boolean validateAmountForDeposit(String id, String amount) {
        if (amount.matches("[a-zA-Z]+")) {
            return false;
        }
        Double value = Double.parseDouble(amount);
        if (bank.accountExistsByID(id)) {
            if (bank.checkAmount(id, value)) {
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean validateCommand(String commandType) {
        if (commandType.equalsIgnoreCase("create")
                || commandType.equalsIgnoreCase("deposit")) {
            return true;
        }
        return false;
    }

    private boolean validateAccountType(String accountType) {
        if (accountType.equalsIgnoreCase("checking")
                || accountType.equalsIgnoreCase("savings")
                || accountType.equalsIgnoreCase("cd")) {
            return true;
        }
        return false;
    }

    private boolean validateId(String commandType, String id) {
        boolean validId = validateNumericAndEightInLength(id);
        if (commandType.equalsIgnoreCase("create")) {
            if (validateCreation(id) && validId) {
                return true;
            }
            return false;
        }
        if (commandType.equalsIgnoreCase("deposit")) {
            if (validateDeposit(id) && validId) {
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

    private boolean validateCreation(String id) {
        if (bank.accountExistsByID(id)) {
            return false;
        }
        return true;
    }

    private boolean validateDeposit(String id) {
        if (bank.accountExistsByID(id)) {
            return true;
        }
        return false;
    }


    private boolean validateApr(String apr) {
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
