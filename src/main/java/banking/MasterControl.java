package banking;

import java.util.List;

public class MasterControl {
    Bank bank;
    CreateValidator createValidator;
    DepositValidator depositValidator;
    CreateProcessor createProcessor;
    DepositProcessor depositProcessor;
    CommandStorage commandStorage;

    public MasterControl(Bank bank, CreateValidator createValidator, DepositValidator depositValidator,
                         CreateProcessor createProcessor, DepositProcessor depositProcessor,
                         CommandStorage commandStorage) {
        this.bank = bank;
        this.createValidator = createValidator;
        this.depositValidator = depositValidator;
        this.createProcessor = createProcessor;
        this.depositProcessor = depositProcessor;
        this.commandStorage = commandStorage;
    }

    public List<String> start(List<String> input) {
        for (String command : input) {
            if (createValidator.validate(command)) {
                createProcessor.execute(command);
            } else if (depositValidator.validate(command)) {
                depositProcessor.execute(command);
            } else {
                commandStorage.addInvalidCommand(command);
            }
        }
        return commandStorage.getInvalidCommands();
    }


}
