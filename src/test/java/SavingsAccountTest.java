import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavingsAccountTest {
    SavingsAccount account;

    @BeforeEach
    void setUp() {
        account = new SavingsAccount("Savings", "98765432", 7.0);
    }

    @Test
    void savings_account_has_a_name() {
        assertEquals("Savings", account.getName());
    }

    @Test
    void savings_account_has_no_money_initially() {
        assertEquals(0.0, account.getBalance());
    }

    @Test
    void deposit_into_savings_account() {
        account.deposit(900);
        assertEquals(900, account.getBalance());
    }

    @Test
    void withdraw_from_savings_account() {
        account.deposit(900);
        account.withdraw(100);
        assertEquals(800, account.getBalance());
    }

    @Test
    void withdraw_more_than_savings_account_balance() {
        account.deposit(900);
        account.withdraw(1500);
        assertEquals(0, account.getBalance());
    }
}

