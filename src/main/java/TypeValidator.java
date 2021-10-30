public class TypeValidator {
    public boolean validateType(String command) {
        String commands[] = command.split(" ");
        if (commands[1].equalsIgnoreCase("checking")) {
            return true;
        } else if (commands[1].equalsIgnoreCase("savings")) {
            return true;
        } else if (commands[1].equalsIgnoreCase("cd")) {
            return true;
        } else {
            return false;
        }
    }
}

