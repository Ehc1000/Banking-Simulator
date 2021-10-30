import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandValidatorTest {
    CommandValidator commandValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandValidator = new CommandValidator(bank);
    }

    @Test
    void id_is_valid_for_creation() {
        boolean actual = commandValidator.validate("create checking 12345678 5.0");
        assertTrue(actual);
    }

    @Test
    void duplicate_id_is_not_unique_for_creation() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = commandValidator.validate("create checking 12345678 5.0");
        assertFalse(actual);
    }

    @Test
    void id_exists_for_deposit() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = commandValidator.validate("deposit 12345678 300");
        assertTrue(actual);
    }

    @Test
    void deposit_with_nonexistent_id() {
        boolean actual = commandValidator.validate("deposit 12345678 5.0 300");
        assertFalse(actual);
    }

    @Test
    void id_is_not_all_numbers() {
        boolean actual = commandValidator.validate("create checking l2e45678 5.0");
        assertFalse(actual);
    }

    @Test
    void id_is_not_exactly_eight_numbers() {
        boolean actual = commandValidator.validate("create checking 123456789 5.0");
        assertFalse(actual);
    }

    @Test
    void id_is_missing_from_command() {
        boolean actual = commandValidator.validate("create checking 5.0");
        assertFalse(actual);
    }

    @Test
    void account_type_is_valid() {
        boolean actual = commandValidator.validate("create savings 98765432 9.0");
        assertTrue(actual);
    }

    @Test
    void account_type_case_sensitive_is_valid() {
        boolean actual = commandValidator.validate("create CHECKING 12345678 8.0");
        assertTrue(actual);
    }

    @Test
    void account_type_has_typo() {
        boolean actual = commandValidator.validate("create svings 98765432 1.5");
        assertFalse(actual);
    }

    @Test
    void account_type_is_neither_checking_savings_nor_cd() {
        boolean actual = commandValidator.validate("create account 12345678 2.5");
        assertFalse(actual);
    }

    @Test
    void account_type_is_missing() {
        boolean actual = commandValidator.validate("create 98765432 3.5");
        assertFalse(actual);
    }

    @Test
    void apr_is_valid() {
        boolean actual = commandValidator.validate("create savings 98765432 5.0");
        assertTrue(actual);
    }

    @Test
    void apr_is_at_the_boundaries() {
        boolean actual = commandValidator.validate("create checking 12345679 0.0");
        boolean actual_two = commandValidator.validate("create checking 12345678 10.0");
        assertTrue(actual);
        assertTrue(actual_two);
    }

    @Test
    void apr_is_just_inside_boundaries() {
        boolean actual = commandValidator.validate("create savings 98765432 0.01");
        boolean actual_two = commandValidator.validate("create checking 12345678 9.99");
        assertTrue(actual);
        assertTrue(actual_two);
    }

    @Test
    void apr_is_out_of_range() {
        boolean actual = commandValidator.validate("create checking 12345678 11.0");
        assertFalse(actual);
    }

    @Test
    void apr_is_negative() {
        boolean actual = commandValidator.validate("create checking 12345678 -1.0");
        assertFalse(actual);
    }

    @Test
    void apr_is_extremely_large() {
        boolean actual = commandValidator.validate("create savings 98765432 1000.0");
        assertFalse(actual);
    }

    @Test
    void apr_is_extremely_small() {
        boolean actual = commandValidator.validate("create savings 98765432 -500.0");
        assertFalse(actual);
    }

    @Test
    void apr_is_missing() {
        boolean actual = commandValidator.validate("create checking 12345678");
        assertFalse(actual);
    }

    @Test
    void apr_is_not_a_number() {
        boolean actual = commandValidator.validate("create checking 12345678 abc");
        assertFalse(actual);
    }

    @Test
    void typo_in_create() {
        boolean actual = commandValidator.validate("crete checking 12345678 5.0");
        assertFalse(actual);
    }

    @Test
    void typo_in_deposit() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = commandValidator.validate("depost 12345678 500");
        assertFalse(actual);
    }

    @Test
    void create_case_sensitive() {
        boolean actual = commandValidator.validate("CREATE checking 12345678 5.0");
        assertTrue(actual);
    }

    @Test
    void deposit_case_sensitive() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = commandValidator.validate("DEPOSIT 12345678 900");
        assertTrue(actual);
    }

    @Test
    void create_is_missing() {
        boolean actual = commandValidator.validate("checking 12345678 5.0");
        assertFalse(actual);
    }

    @Test
    void deposit_is_missing() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = commandValidator.validate("12345678 500");
    }
}
