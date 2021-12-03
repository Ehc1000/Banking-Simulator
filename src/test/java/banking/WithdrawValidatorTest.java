package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WithdrawValidatorTest {

    WithdrawValidator withdrawValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        withdrawValidator = new WithdrawValidator(bank);
    }

    @Test
    void id_exists_for_withdrawal() {
        bank.addAccount("12345678", "checking", 5.0);
        bank.getAccounts().get("12345678").deposit(500);
        boolean actual = withdrawValidator.validate("withdraw 12345678 200");
        assertTrue(actual);
    }

    @Test
    void withdraw_with_nonexistent_id() {
        boolean actual = withdrawValidator.validate("withdraw 12345678 200");
        assertFalse(actual);
    }

    @Test
    void withdraw_with_an_invalid_id() {
        bank.addAccount("12345678", "checking", 5.0);
        bank.getAccounts().get("12345678").deposit(500);
        boolean actual = withdrawValidator.validate("withdraw 12345679 200");
        assertFalse(actual);
    }

    @Test
    void id_is_missing_from_withdrawal_command() {
        bank.addAccount("12345678", "checking", 5.0);
        bank.getAccounts().get("12345678").deposit(500);
        boolean actual = withdrawValidator.validate("withdraw 200");
        assertFalse(actual);
    }

    @Test
    void withdraw_case_sensitive() {
        bank.addAccount("12345678", "checking", 5.0);
        bank.getAccounts().get("12345678").deposit(500);
        boolean actual = withdrawValidator.validate("WITHDRAW 12345678 200");
        assertTrue(actual);
    }

    @Test
    void typo_in_withdraw() {
        bank.addAccount("12345678", "checking", 5.0);
        bank.getAccounts().get("12345678").deposit(500);
        boolean actual = withdrawValidator.validate("wthdraw 12345678 200");
        assertFalse(actual);
    }

    @Test
    void withdraw_is_missing() {
        bank.addAccount("12345678", "checking", 5.0);
        bank.getAccounts().get("12345678").deposit(500);
        boolean actual = withdrawValidator.validate("12345678 200");
        assertFalse(actual);
    }

    @Test
    void amount_is_missing() {
        bank.addAccount("12345678", "checking", 5.0);
        bank.getAccounts().get("12345678").deposit(500);
        boolean actual = withdrawValidator.validate("withdraw 12345678");
        assertFalse(actual);
    }

    @Test
    void withdraw_valid_amount_from_checking() {
        bank.addAccount("12345678", "checking", 5.0);
        bank.getAccounts().get("12345678").deposit(500);
        boolean actual = withdrawValidator.validate("withdraw 12345678 100");
        assertTrue(actual);
    }

    @Test
    void withdraw_amount_on_boundaries_for_checking() {
        bank.addAccount("12345678", "checking", 5.0);
        bank.getAccounts().get("12345678").deposit(500);
        boolean actual = withdrawValidator.validate("withdraw 12345678 0");
        boolean actual_two = withdrawValidator.validate("withdraw 12345678 400");
        assertTrue(actual);
        assertTrue(actual_two);
    }

    @Test
    void withdraw_negative_amount_from_checking() {
        bank.addAccount("12345678", "checking", 5.0);
        bank.getAccounts().get("12345678").deposit(500);
        boolean actual = withdrawValidator.validate("withdraw 12345678 -200");
        assertFalse(actual);
    }

    @Test
    void withdrawal_amount_is_too_large_for_checking() {
        bank.addAccount("12345678", "checking", 5.0);
        bank.getAccounts().get("12345678").deposit(500);
        boolean actual = withdrawValidator.validate("withdraw 12345678 600");
        assertFalse(actual);
    }

    @Test
    void withdraw_with_a_word_as_amount() {
        bank.addAccount("12345678", "checking", 5.0);
        bank.getAccounts().get("12345678").deposit(500);
        boolean actual = withdrawValidator.validate("withdraw 12345678 abc");
        assertFalse(actual);
    }

    @Test
    void withdraw_valid_amount_from_savings() {
        bank.addAccount("98765432", "savings", 6.0);
        bank.getAccounts().get("98765432").deposit(2000);
        boolean actual = withdrawValidator.validate("withdraw 98765432 500");
        assertTrue(actual);
    }

    @Test
    void withdraw_amount_on_boundaries_from_savings() {
        bank.addAccount("98765432", "savings", 6.0);
        bank.getAccounts().get("98765432").deposit(2000);
        boolean actual = withdrawValidator.validate("withdraw 98765432 0");
        boolean actual_two = withdrawValidator.validate("withdraw 98765432 1000");
        assertTrue(actual);
        assertTrue(actual_two);
    }

    @Test
    void withdraw_negative_amount_from_savings() {
        bank.addAccount("98765432", "savings", 6.0);
        bank.getAccounts().get("98765432").deposit(2000);
        boolean actual = withdrawValidator.validate("withdraw 98765432 -500");
        assertFalse(actual);
    }

    @Test
    void withdrawal_amount_is_too_large_for_savings() {
        bank.addAccount("98765432", "savings", 6.0);
        bank.getAccounts().get("98765432").deposit(2000);
        boolean actual = withdrawValidator.validate("withdraw 98765432 1500");
        assertFalse(actual);
    }

    @Test
    void withdrawing_twice_from_savings_in_the_same_month() {
        bank.addAccount("98765432", "savings", 6.0);
        bank.getAccounts().get("98765432").deposit(2000);
        bank.getAccounts().get("98765432").withdraw(500);
        boolean actual = withdrawValidator.validate("withdraw 98765432 500");
        assertFalse(actual);
    }

    @Test
    void successful_withdrawal_from_savings_after_pass_time() {
        bank.addAccount("98765432", "savings", 6.0);
        bank.getAccounts().get("98765432").deposit(2000);
        bank.getAccounts().get("98765432").withdraw(500);
        bank.passTime(1);
        boolean actual = withdrawValidator.validate("withdraw 98765432 500");
        assertTrue(actual);
    }

    @Test
    void try_withdrawing_from_cd_right_after_creation() {
        bank.addAccount("12345679", "cd", 8.0, 5000);
        boolean actual = withdrawValidator.validate("withdraw 12345679 5000");
        assertFalse(actual);
    }

    @Test
    void try_withdrawing_from_cd_before_twelve_months_passed() {
        bank.addAccount("12345679", "cd", 8.0, 5000);
        bank.passTime(11);
        boolean actual = withdrawValidator.validate("withdraw 12345679 6878.330502168971");
        assertFalse(actual);
    }

    @Test
    void valid_full_withdrawal_from_cd_after_twelve_months() {
        bank.addAccount("12345679", "cd", 8.0, 5000);
        bank.passTime(12);
        boolean actual = withdrawValidator.validate("withdraw 12345679 6878.330502168971");
        assertTrue(actual);
    }

    @Test
    void withdrawal_is_not_full_for_cd() {
        bank.addAccount("12345679", "cd", 6.0, 5000);
        bank.passTime(12);
        boolean actual = withdrawValidator.validate("withdraw 12345679 1000");
        assertFalse(actual);
    }
}
