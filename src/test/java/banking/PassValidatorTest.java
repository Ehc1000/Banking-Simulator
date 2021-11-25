package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassValidatorTest {

    Bank bank;
    PassValidator passValidator;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        passValidator = new PassValidator(bank);
    }

    @Test
    void valid_pass_time_command() {
        boolean actual = passValidator.validate("pass 10");
        assertTrue(actual);
    }

    @Test
    void pass_case_sensitive() {
        boolean actual = passValidator.validate("PASS 10");
        assertTrue(actual);
    }

    @Test
    void typo_in_pass() {
        boolean actual = passValidator.validate("pas 10");
        assertFalse(actual);
    }

    @Test
    void pass_missing_from_command() {
        boolean actual = passValidator.validate("10");
        assertFalse(actual);
    }

    @Test
    void month_missing_from_command() {
        boolean actual = passValidator.validate("pass");
        assertFalse(actual);
    }

    @Test
    void month_is_a_decimal() {
        boolean actual = passValidator.validate("pass 10.0");
        assertFalse(actual);
    }

    @Test
    void month_is_a_word() {
        boolean actual = passValidator.validate("pass abc");
        assertFalse(actual);
    }

    @Test
    void month_on_boundaries() {
        boolean actual = passValidator.validate("pass 1");
        boolean actual_two = passValidator.validate("pass 60");
        assertTrue(actual);
        assertTrue(actual_two);
    }

    @Test
    void month_is_negative() {
        boolean actual = passValidator.validate("pass -5");
        assertFalse(actual);
    }

    @Test
    void month_is_too_large() {
        boolean actual = passValidator.validate("pass 100");
        assertFalse(actual);
    }

    @Test
    void pass_zero_months() {
        boolean actual = passValidator.validate("pass 0");
        assertFalse(actual);
    }
}
