public class RateValidator {
    public boolean validateApr(String command) {
        String commands[] = command.split(" ");
        if (commands.length < 4) {
            return false;
        }
        if (commands[3].matches("[a-zA-Z]+")) {
            return false;
        }
        Double apr = Double.parseDouble(commands[3]);
        if (apr >= 0.0 && apr <= 10.0) {
            return true;
        }
        return false;
    }
}
