package banking;

import java.util.ArrayList;
import java.util.List;

public class MasterControl {
    Bank bank;
    Redirector redirector;
    CommandStorage commandStorage;

    public MasterControl(Bank bank, Redirector redirector,
                         CommandStorage commandStorage) {
        this.bank = bank;
        this.redirector = redirector;
        this.commandStorage = commandStorage;
    }

    public List<String> start(List<String> input) {
        for (String command : input) {
            if (redirector.validate(command)) {
                redirector.execute(command);
            } else {
                commandStorage.addInvalidCommand(command);
            }
        }
        return DisplayOutput(commandStorage.getInvalidCommands());
    }

    private List<String> DisplayOutput(ArrayList<String> invalidCommands) {
        return bank.getTransactionHistory(invalidCommands);
    }

}
