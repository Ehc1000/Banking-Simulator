package banking;

public class PassProcessor extends CommandProcessor {

    public PassProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public void execute(String command) {
        String commands[] = command.split(" ");
        int months = Integer.parseInt(commands[1]);
        pass(months);
    }

    private void pass(int months) {
        bank.passTime(months);
    }
}
