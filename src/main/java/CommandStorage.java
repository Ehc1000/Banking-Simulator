import java.util.ArrayList;

public class CommandStorage {
    private ArrayList<String> commandList;

    public CommandStorage() {
        commandList = new ArrayList<>();
    }

    public void addInvalidCommand(String command) {
        commandList.add(command);
    }

    public ArrayList<String> getInvalidCommands() {
        return commandList;
    }
}
