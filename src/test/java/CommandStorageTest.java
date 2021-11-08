import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandStorageTest {
    CommandStorage commandStorage;

    @BeforeEach
    void setUp() {
        commandStorage = new CommandStorage();
    }

    @Test
    void add_one_invalid_command() {
        commandStorage.addInvalidCommand("crete checking 12345678 4.0");
        assertEquals("crete checking 12345678 4.0", commandStorage.getInvalidCommands().get(0));
    }

    @Test
    void add_multiple_invalid_commands() {
        commandStorage.addInvalidCommand("create cheking 12345678 4.0");
        commandStorage.addInvalidCommand("depost 12345678 800");
        assertEquals("create cheking 12345678 4.0", commandStorage.getInvalidCommands().get(0));
        assertEquals("depost 12345678 800", commandStorage.getInvalidCommands().get(1));
    }
}
