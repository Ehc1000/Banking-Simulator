package banking;

public class TransferValidator extends CommandValidator {
    public TransferValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String command) {
        String commands[] = command.split(" ");
        if (commands.length == 4) {
            if (validateTransferCommand(command)) {
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean validateTransferCommand(String command) {
        String commands[] = command.split(" ");
        String commandType = commands[0];
        String fromId = commands[1];
        String toId = commands[2];
        String amount = commands[3];

        boolean validCommand = validateCommand(commandType);
        boolean validFromId = validateId(commandType, fromId);
        boolean validToId = validateId(commandType, toId);
        boolean validFromAmount = validateFromAmount(fromId, amount);
        boolean validToAmount = validateToAmount(toId, amount);

        if (validCommand && validFromId && validToId && validFromAmount && validToAmount) {
            return true;
        }
        return false;
    }

    private boolean validateFromAmount(String fromId, String amount) {
        if (amount.matches("[a-zA-Z]+")) {
            return false;
        }
        Double value = Double.parseDouble(amount);
        if (bank.accountExistsByID(fromId)) {
            if (bank.checkWithdrawAmount(fromId, value)) {
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean validateToAmount(String toId, String amount) {
        if (amount.matches("[a-zA-Z]+")) {
            return false;
        }
        Double value = Double.parseDouble(amount);
        if (bank.accountExistsByID(toId)) {
            if (bank.checkDepAmount(toId, value)) {
                return true;
            }
            return false;
        }
        return false;
    }
}
