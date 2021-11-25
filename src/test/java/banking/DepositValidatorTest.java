package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepositValidatorTest {

    DepositValidator depositValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        depositValidator = new DepositValidator(bank);
    }

    @Test
    void id_exists_for_deposit() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = depositValidator.validate("deposit 12345678 300");
        assertTrue(actual);
    }

    @Test
    void deposit_with_nonexistent_id() {
        boolean actual = depositValidator.validate("deposit 12345678 300");
        assertFalse(actual);
    }

    @Test
    void deposit_with_an_invalid_id() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = depositValidator.validate("deposit 12345679 500");
        assertFalse(actual);
    }

    @Test
    void id_is_missing_from_command_for_deposit() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = depositValidator.validate("deposit 600");
        assertFalse(actual);
    }

    @Test
    void deposit_case_sensitive() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = depositValidator.validate("DEPOSIT 12345678 900");
        assertTrue(actual);
    }

    @Test
    void deposit_is_missing() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = depositValidator.validate("12345678 500");
        assertFalse(actual);
    }

    @Test
    void depositing_into_account_without_specifying_amount() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = depositValidator.validate("deposit 12345678");
        assertFalse(actual);
    }

    @Test
    void deposit_valid_amount_into_checking() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = depositValidator.validate("deposit 12345678 500");
        assertTrue(actual);
    }

    @Test
    void deposit_amount_on_boundaries_into_checking() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = depositValidator.validate("deposit 12345678 0");
        boolean actual_two = depositValidator.validate("deposit 12345678 1000");
        assertTrue(actual);
        assertTrue(actual_two);
    }

    @Test
    void deposit_negative_amount_into_checking() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = depositValidator.validate("deposit 12345678 -300");
        assertFalse(actual);
    }

    @Test
    void deposit_amount_out_of_range_into_checking() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = depositValidator.validate("deposit 12345678 2000");
        assertFalse(actual);
    }

    @Test
    void deposit_amount_not_a_number_into_checking() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = depositValidator.validate("deposit 12345678 abc");
        assertFalse(actual);
    }

    @Test
    void deposit_valid_amount_into_savings() {
        bank.addAccount("98765432", "savings", 7.0);
        boolean actual = depositValidator.validate("deposit 98765432 500");
        assertTrue(actual);
    }

    @Test
    void deposit_amount_on_boundaries_into_savings() {
        bank.addAccount("98765432", "savings", 7.0);
        boolean actual = depositValidator.validate("deposit 98765432 0");
        boolean actual_two = depositValidator.validate("deposit 98765432 2500");
        assertTrue(actual);
        assertTrue(actual_two);
    }

    @Test
    void deposit_negative_amount_into_savings() {
        bank.addAccount("98765432", "savings", 7.0);
        boolean actual = depositValidator.validate("deposit 98765432 -400");
        assertFalse(actual);
    }

    @Test
    void deposit_amount_out_of_range_into_savings() {
        bank.addAccount("98765432", "savings", 7.0);
        boolean actual = depositValidator.validate("deposit 98765432 4000");
        assertFalse(actual);
    }

    @Test
    void try_depositing_into_a_cd() {
        bank.addAccount("12345678", "cd", 5.0, 2000);
        boolean actual = depositValidator.validate("deposit 12345678 5000");
        assertFalse(actual);
    }

}
