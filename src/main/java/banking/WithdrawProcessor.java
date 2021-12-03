package banking;

public class WithdrawProcessor extends CommandProcessor {

    public WithdrawProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public void execute(String command) {
        String commands[] = command.split(" ");
        String id = commands[1];
        Double amount = Double.parseDouble(commands[2]);
        withdrawFromAccount(id, amount);
        bank.updateTransaction(id, command);
    }

    private void withdrawFromAccount(String id, Double amount) {
        bank.getAccounts().get(id).withdraw(amount);
    }
}
