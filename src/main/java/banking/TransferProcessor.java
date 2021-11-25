package banking;

public class TransferProcessor extends CommandProcessor {
    public TransferProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public void execute(String command) {
        String commands[] = command.split(" ");
        String fromId = commands[1];
        String toId = commands[2];
        Double amount = Double.parseDouble(commands[3]);
        transferBetweenAccounts(fromId, toId, amount);
    }

    private void transferBetweenAccounts(String fromId, String toId, Double amount) {
        bank.getAccounts().get(fromId).withdraw(amount);
        bank.getAccounts().get(toId).deposit(amount);
    }
}
