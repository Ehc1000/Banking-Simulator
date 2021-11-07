import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandValidatorTest {
    CreateValidator createValidator;
    DepositValidator depositValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        depositValidator = new DepositValidator(bank);
        createValidator = new CreateValidator(bank);

    }

    @Test
    void id_is_valid_for_creation() {
        boolean actual = createValidator.validate("create checking 12345678 5.0");
        assertTrue(actual);
    }

    @Test
    void duplicate_id_is_not_unique_for_creation() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = createValidator.validate("create checking 12345678 5.0");
        assertFalse(actual);
    }

    @Test
    void id_exists_for_deposit() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = depositValidator.validate("deposit 12345678 300");
        assertTrue(actual);
    }

    @Test
    void deposit_with_nonexistent_id() {
        boolean actual = depositValidator.validate("deposit 12345678 5.0 300");
        assertFalse(actual);
    }

    @Test
    void id_is_not_all_numbers() {
        boolean actual = createValidator.validate("create checking l2e45678 5.0");
        assertFalse(actual);
    }

    @Test
    void id_is_not_exactly_eight_numbers() {
        boolean actual = createValidator.validate("create checking 123456789 5.0");
        assertFalse(actual);
    }

    @Test
    void id_is_missing_from_command_for_create() {
        boolean actual = createValidator.validate("create checking 5.0");
        assertFalse(actual);
    }

    @Test
    void id_is_missing_from_command_for_deposit() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = depositValidator.validate("deposit 600");
        assertFalse(actual);
    }

    @Test
    void account_type_is_valid() {
        boolean actual = createValidator.validate("create savings 98765432 9.0");
        assertTrue(actual);
    }

    @Test
    void account_type_case_sensitive_is_valid() {
        boolean actual = createValidator.validate("create CHECKING 12345678 8.0");
        assertTrue(actual);
    }

    @Test
    void account_type_has_typo() {
        boolean actual = createValidator.validate("create svings 98765432 1.5");
        assertFalse(actual);
    }

    @Test
    void account_type_is_neither_checking_savings_nor_cd() {
        boolean actual = createValidator.validate("create account 12345678 2.5");
        assertFalse(actual);
    }

    @Test
    void account_type_is_missing() {
        boolean actual = createValidator.validate("create 98765432 3.5");
        assertFalse(actual);
    }

    @Test
    void apr_is_valid() {
        boolean actual = createValidator.validate("create savings 98765432 5.0");
        assertTrue(actual);
    }

    @Test
    void apr_is_at_the_boundaries() {
        boolean actual = createValidator.validate("create checking 12345679 0.0");
        boolean actual_two = createValidator.validate("create checking 12345678 10.0");
        assertTrue(actual);
        assertTrue(actual_two);
    }

    @Test
    void apr_is_just_inside_boundaries() {
        boolean actual = createValidator.validate("create savings 98765432 0.01");
        boolean actual_two = createValidator.validate("create checking 12345678 9.99");
        assertTrue(actual);
        assertTrue(actual_two);
    }

    @Test
    void apr_is_out_of_range() {
        boolean actual = createValidator.validate("create checking 12345678 11.0");
        assertFalse(actual);
    }

    @Test
    void apr_is_negative() {
        boolean actual = createValidator.validate("create checking 12345678 -1.0");
        assertFalse(actual);
    }

    @Test
    void apr_is_extremely_large() {
        boolean actual = createValidator.validate("create savings 98765432 1000.0");
        assertFalse(actual);
    }

    @Test
    void apr_is_extremely_small() {
        boolean actual = createValidator.validate("create savings 98765432 -500.0");
        assertFalse(actual);
    }

    @Test
    void apr_is_missing() {
        boolean actual = createValidator.validate("create checking 12345678");
        assertFalse(actual);
    }

    @Test
    void apr_is_not_a_number() {
        boolean actual = createValidator.validate("create checking 12345678 abc");
        assertFalse(actual);
    }

    @Test
    void typo_in_create() {
        boolean actual = createValidator.validate("crete checking 12345678 5.0");
        assertFalse(actual);
    }

    @Test
    void typo_in_deposit() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = createValidator.validate("depost 12345678 500");
        assertFalse(actual);
    }

    @Test
    void create_case_sensitive() {
        boolean actual = createValidator.validate("CREATE checking 12345678 5.0");
        assertTrue(actual);
    }

    @Test
    void deposit_case_sensitive() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = depositValidator.validate("DEPOSIT 12345678 900");
        assertTrue(actual);
    }

    @Test
    void create_is_missing() {
        boolean actual = createValidator.validate("checking 12345678 5.0");
        assertFalse(actual);
    }

    @Test
    void deposit_is_missing() {
        bank.addAccount("12345678", "checking", 5.0);
        boolean actual = depositValidator.validate("12345678 500");
        assertFalse(actual);
    }

    @Test
    void create_cd_is_valid() {
        boolean actual = createValidator.validate("create cd 12345678 5.0 5000");
        assertTrue(actual);
    }

    @Test
    void create_cd_with_amount_on_boundaries() {
        boolean actual = createValidator.validate("create cd 12345678 5.0 1000");
        boolean actual_two = createValidator.validate("create cd 12345678 5.0 10000");
        assertTrue(actual);
        assertTrue(actual_two);
    }

    @Test
    void create_cd_with_amount_that_is_out_of_range() {
        boolean actual = createValidator.validate("create cd 12345678 5.0 15000");
        assertFalse(actual);
    }

    @Test
    void create_cd_with_negative_amount() {
        boolean actual = createValidator.validate("create cd 12345678 5.0 -500");
        assertFalse(actual);
    }

    @Test
    void create_cd_where_amount_is_not_a_number() {
        boolean actual = createValidator.validate("create cd 12345678 5.0 abc");
        assertFalse(actual);
    }

    @Test
    void create_cd_without_specifying_amount() {
        boolean actual = createValidator.validate("create cd 12345678 5.0");
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
