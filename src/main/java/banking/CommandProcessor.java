package banking;

public abstract class CommandProcessor {

    public Bank bank;

    public CommandProcessor(Bank bank) {
        this.bank = bank;
    }

    public abstract void execute(String command);
}
