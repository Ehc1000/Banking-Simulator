package banking;

public class DepositProcessor extends CommandProcessor {

    public DepositProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public void execute(String command) {
        String commands[] = command.split(" ");
        String id = commands[1];
        Double amount = Double.parseDouble(commands[2]);
        depositIntoAccount(id, amount);
    }

    private void depositIntoAccount(String id, Double amount) {
        bank.getAccounts().get(id).deposit(amount);
    }

}
