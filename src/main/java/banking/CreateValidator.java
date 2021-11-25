package banking;

public class CreateValidator extends CommandValidator {

    public CreateValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String command) {
        String commands[] = command.split(" ");
        String accountType = commands[1];
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
}

