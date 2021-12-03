package banking;

public class Redirector {

    private CreateValidator createValidator;
    private DepositValidator depositValidator;
    private WithdrawValidator withdrawValidator;
    private TransferValidator transferValidator;
    private PassValidator passValidator;
    private CreateProcessor createProcessor;
    private DepositProcessor depositProcessor;
    private WithdrawProcessor withdrawProcessor;
    private TransferProcessor transferProcessor;
    private PassProcessor passProcessor;


    public Redirector(CreateValidator createValidator, DepositValidator depositValidator,
                      WithdrawValidator withdrawValidator, TransferValidator transferValidator,
                      PassValidator passValidator, CreateProcessor createProcessor,
                      DepositProcessor depositProcessor, WithdrawProcessor withdrawProcessor,
                      TransferProcessor transferProcessor, PassProcessor passProcessor) {
        this.createValidator = createValidator;
        this.depositValidator = depositValidator;
        this.withdrawValidator = withdrawValidator;
        this.transferValidator = transferValidator;
        this.passValidator = passValidator;
        this.createProcessor = createProcessor;
        this.depositProcessor = depositProcessor;
        this.withdrawProcessor = withdrawProcessor;
        this.transferProcessor = transferProcessor;
        this.passProcessor = passProcessor;
    }


    public boolean validate(String command) {
        String commands[] = command.split(" ");
        switch (commands[0].toLowerCase()) {
            case "create":
                if (createValidator.validate(command)) {
                    return true;
                }
                return false;
            case "deposit":
                if (depositValidator.validate(command)) {
                    return true;
                }
                return false;
            case "withdraw":
                if (withdrawValidator.validate(command)) {
                    return true;
                }
                return false;
            case "transfer":
                if (transferValidator.validate(command)) {
                    return true;
                }
                return false;
            case "pass":
                if (passValidator.validate(command)) {
                    return true;
                }
                return false;
        }
        return false;
    }

    public void execute(String command) {
        String commands[] = command.split(" ");
        switch (commands[0].toLowerCase()) {
            case "create":
                createProcessor.execute(command);
                break;
            case "deposit":
                depositProcessor.execute(command);
                break;
            case "withdraw":
                withdrawProcessor.execute(command);
                break;
            case "transfer":
                transferProcessor.execute(command);
                break;
            case "pass":
                passProcessor.execute(command);
                break;
        }
    }
}
