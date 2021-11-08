import java.util.ArrayList;

public class CommandStorage {
    ArrayList<String> accounts = new ArrayList<String>();


    public void addInvalidCommand(String command) {
        accounts.add(command);
    }

    public ArrayList<String> getInvalidCommands() {
        return accounts;
    }
}
