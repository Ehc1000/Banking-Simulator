public class CreateProcessor extends CommandProcessor {

    public CreateProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public void execute(String command) {
        String commands[] = command.split(" ");
        String accountType = commands[1];
        if (accountType.equalsIgnoreCase("cd")) {
            String id = commands[2];
            Double apr = Double.parseDouble(commands[3]);
            Double amount = Double.parseDouble(commands[4]);
            createCd(accountType, id, apr, amount);
        }
        String id = commands[2];
        Double apr = Double.parseDouble(commands[3]);
        createCheckingOrSavings(accountType, id, apr);
    }

    private void createCheckingOrSavings(String accountType, String id, Double apr) {
        bank.addAccount(id, accountType, apr);
    }

    private void createCd(String accountType, String id, Double apr, Double amount) {
        bank.addAccount(id, accountType, apr, amount);
    }

}
