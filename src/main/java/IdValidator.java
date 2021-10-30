public class IdValidator {

    private Bank bank;

    public IdValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validateCreation(String command) {
        if (bank.accountExistsByID("12345678")) {
            return false;
        }
        return true;
    }

    public boolean validateDeposit(String command) {
        if (bank.accountExistsByID("12345678")) {
            return true;
        }
        return false;
    }

    public boolean validateId(String command) {
        String commands[] = command.split(" ");
        String id = commands[2];
        if (id.matches("[0-9]+")) {
            if (id.length() != 8) {
                return false;
            }
            return true;
        }
        return false;
    }

}
