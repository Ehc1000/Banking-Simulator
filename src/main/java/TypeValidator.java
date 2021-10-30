public class TypeValidator {
    public boolean validateType(String command) {
        String commands[] = command.split(" ");
        String accountType = commands[1];
        if (accountType.equalsIgnoreCase("checking")) {
            return true;
        } else if (accountType.equalsIgnoreCase("savings")) {
            return true;
        } else if (accountType.equalsIgnoreCase("cd")) {
            return true;
        } else {
            return false;
        }
    }
}

