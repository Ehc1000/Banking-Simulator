import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckingAccountTest {
    CheckingAccount account;

    @BeforeEach
    void setUp() {
        account = new CheckingAccount("Checking", 5.0, 0);
    }

    @Test
    void checking_account_has_a_name() {
        assertEquals("Checking", account.getName());
    }

    @Test
    void checking_account_has_no_money_initially() {
        assertEquals(0.0, account.getBalance());
    }

    @Test
    void deposit_into_checking_account() {
        account.deposit(500);
        assertEquals(500, account.getBalance());
    }

    @Test
    void deposit_twice_into_checking_account() {
        account.deposit(1000);
        account.deposit(450.50);
        assertEquals(1450.50, account.getBalance());
    }

    @Test
    void withdraw_from_checking_account() {
        account.deposit(500);
        account.withdraw(300);
        assertEquals(200, account.getBalance());
    }

    @Test
    void withdraw_twice_from_checking_account() {
        account.deposit(1500);
        account.withdraw(600);
        account.withdraw(100);
        assertEquals(800, account.getBalance());
    }

    @Test
    void withdraw_more_than_checking_account_balance() {
        account.deposit(500);
        account.withdraw(1000);
        assertEquals(0, account.getBalance());
    }
}
