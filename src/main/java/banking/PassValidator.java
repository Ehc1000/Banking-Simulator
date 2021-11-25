package banking;

public class PassValidator extends CommandValidator {
    public PassValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String command) {
        String commands[] = command.split(" ");
        if (commands.length == 2) {
            if (validatePassCommand(command)) {
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean validatePassCommand(String command) {
        String commands[] = command.split(" ");
        String commandType = commands[0];
        String month = commands[1];

        boolean validCommand = validateCommand(commandType);
        boolean validMonth = validateMonths(month);

        if (validCommand && validMonth) {
            return true;
        }
        return false;
    }

    private boolean validateMonths(String month) {
        if (month.matches("[a-zA-Z]+")) {
            return false;
        }
        if (month.contains(".")) {
            return false;
        }
        int time = Integer.parseInt(month);
        if (time >= 1 && time <= 60) {
            return true;
        }
        return false;
    }
}
