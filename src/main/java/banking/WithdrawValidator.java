package banking;

public class WithdrawValidator extends CommandValidator {
    public WithdrawValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String command) {
        String commands[] = command.split(" ");
        if (commands.length == 3) {
            if (validateWithdrawCommand(command)) {
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean validateWithdrawCommand(String command) {
        String commands[] = command.split(" ");
        String commandType = commands[0];
        String id = commands[1];
        String amount = commands[2];

        boolean validCommand = validateCommand(commandType);
        boolean validId = validateId(commandType, id);
        boolean validAmount = validateAmountForWithdraw(id, amount);

        if (validCommand && validId && validAmount) {
            return true;
        }
        return false;
    }

    private boolean validateAmountForWithdraw(String id, String amount) {
        if (amount.matches("[a-zA-Z]+")) {
            return false;
        }
        Double value = Double.parseDouble(amount);
        if (bank.accountExistsByID(id)) {
            if (bank.checkWithdrawAmount(id, value)) {
                return true;
            }
            return false;
        }
        return false;
    }
}
