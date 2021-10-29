import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IdTest {
    IdValidator idValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        idValidator = new IdValidator(bank);
    }

    @Test
    void id_is_valid_for_creation() {
        boolean actual = idValidator.validateCreation("create checking 12345678 5.0");
        assertTrue(actual);
    }

    @Test
    void duplicate_id_is_not_unique_for_creation() {
        bank.addAccount("12345678", "Checking", 5.0);
        boolean actual = idValidator.validateCreation("create checking 12345678 5.0");
        assertFalse(actual);
    }

    @Test
    void id_exists_for_deposit() {
        bank.addAccount("12345678", "Checking", 5.0);
        boolean actual = idValidator.validateDeposit("deposit checking 12345678 5.0 300");
        assertTrue(actual);
    }

    @Test
    void deposit_with_nonexistent_id() {
        boolean actual = idValidator.validateDeposit("deposit checking 12345678 5.0 300");
        assertFalse(actual);
    }

    @Test
    void id_is_not_all_numbers() {
        boolean actual = idValidator.validateId("create checking l2e45678 5.0");
        assertFalse(actual);
    }

    @Test
    void id_is_not_exactly_eight_numbers() {
        boolean actual = idValidator.validateId("create checking 123456789 5.0");
        assertFalse(actual);
    }

    @Test
    void id_is_missing_from_command() {
        boolean actual = idValidator.validateId("create checking 5.0");
        assertFalse(actual);
    }

}
