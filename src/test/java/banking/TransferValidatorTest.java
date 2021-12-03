package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransferValidatorTest {

    TransferValidator transferValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        transferValidator = new TransferValidator(bank);
        bank.addAccount("12345678", "checking", 5.0);
        bank.addAccount("98765432", "savings", 6.0);
    }

    @Test
    void id_exists_for_transfer() {
        boolean actual = transferValidator.validate("transfer 98765432 12345678 0");
        assertTrue(actual);
    }

    @Test
    void transfer_with_invalid_from_id() {
        boolean actual = transferValidator.validate("transfer 98765431 12345678 0");
        assertFalse(actual);
    }

    @Test
    void transfer_with_invalid_to_id() {
        boolean actual = transferValidator.validate("transfer 98765432 12345679 0");
        assertFalse(actual);
    }

    @Test
    void transfer_missing_from_command() {
        boolean actual = transferValidator.validate("98765432 12345678 0");
        assertFalse(actual);
    }

    @Test
    void transfer_case_sensitive() {
        boolean actual = transferValidator.validate("TRANSFER 98765432 12345678 0");
        assertTrue(actual);
    }

    @Test
    void typo_in_transfer() {
        boolean actual = transferValidator.validate("transfe 98765432 12345678 0");
        assertFalse(actual);
    }

    @Test
    void from_id_missing() {
        boolean actual = transferValidator.validate("transfer 12345678 0");
        assertFalse(actual);
    }

    @Test
    void to_id_missing() {
        boolean actual = transferValidator.validate("transfer 98765432 0");
        assertFalse(actual);
    }

    @Test
    void amount_is_missing() {
        boolean actual = transferValidator.validate("transfer 98765432 12345678");
        assertFalse(actual);
    }

    @Test
    void transfer_valid_amount_between_savings_and_checking() {
        bank.getAccounts().get("98765432").deposit(2000);
        boolean actual = transferValidator.validate("transfer 98765432 12345678 600");
        assertTrue(actual);
    }

    @Test
    void transfer_negative_amount_from_savings() {
        bank.getAccounts().get("98765432").deposit(2000);
        boolean actual = transferValidator.validate("transfer 98765432 12345678 -500");
        assertFalse(actual);
    }

    @Test
    void transfer_very_large_amount_from_savings() {
        bank.getAccounts().get("98765432").deposit(2000);
        boolean actual = transferValidator.validate("transfer 98765432 12345678 1500");
        assertFalse(actual);
    }

    @Test
    void transfer_amount_on_boundaries_from_savings() {
        bank.getAccounts().get("98765432").deposit(2000);
        boolean actual = transferValidator.validate("transfer 98765432 12345678 0");
        boolean actual_two = transferValidator.validate("transfer 98765432 12345678 1000");
        assertTrue(actual);
        assertTrue(actual_two);
    }

    @Test
    void transfer_more_than_amount_from_account() {
        bank.getAccounts().get("98765432").deposit(600);
        boolean actual = transferValidator.validate("transfer 98765432 12345678 800");
        assertTrue(actual);
    }

    @Test
    void transfer_with_amount_that_is_a_word() {
        bank.getAccounts().get("98765432").deposit(2000);
        boolean actual = transferValidator.validate("transfer 98765432 12345678 abc");
        assertFalse(actual);
    }

    @Test
    void transfer_valid_amount_between_checking_and_savings() {
        bank.getAccounts().get("12345678").deposit(800);
        boolean actual = transferValidator.validate("transfer 12345678 98765432 200");
        assertTrue(actual);
    }

    @Test
    void transfer_negative_amount_from_checking() {
        bank.getAccounts().get("12345678").deposit(800);
        boolean actual = transferValidator.validate("transfer 12345678 98765432 -200");
        assertFalse(actual);
    }

    @Test
    void transfer_very_large_amount_from_checking() {
        bank.getAccounts().get("12345678").deposit(800);
        boolean actual = transferValidator.validate("transfer 12345678 98765432 600");
        assertFalse(actual);
    }

    @Test
    void from_id_is_a_word() {
        bank.getAccounts().get("12345678").deposit(800);
        boolean actual = transferValidator.validate("transfer abc 98765432 200");
        assertFalse(actual);
    }

    @Test
    void to_id_is_a_word() {
        bank.getAccounts().get("12345678").deposit(800);
        boolean actual = transferValidator.validate("transfer 12345678 abc 200");
        assertFalse(actual);
    }

    @Test
    void transfer_amount_on_boundaries_from_checking() {
        bank.getAccounts().get("12345678").deposit(800);
        boolean actual = transferValidator.validate("transfer 12345678 98765432 0");
        boolean actual_two = transferValidator.validate("transfer 12345678 98765432 400");
        assertTrue(actual);
        assertTrue(actual_two);
    }

    @Test
    void try_transferring_to_a_cd() {
        bank.addAccount("12345679", "cd", 9.0, 5000);
        bank.getAccounts().get("98765432").deposit(2000);
        boolean actual = transferValidator.validate("transfer 98765432 12345679 800");
        assertFalse(actual);
    }
}
