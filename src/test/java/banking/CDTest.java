package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CDTest {
    CDAccount account;

    @BeforeEach
    void setUp() {
        account = new CDAccount("CD", "12345679", 4.0, 5000);
    }

    @Test
    void cd_account_has_a_name() {
        assertEquals("CD", account.getName());
    }

    @Test
    void cd_account_has_an_amount() {
        assertEquals(5000, account.getBalance());
    }

    @Test
    void withdraw_from_cd_account() {
        account.withdraw(3000);
        assertEquals(2000, account.getBalance());
    }

    @Test
    void withdraw_more_than_cd_account_balance() {
        account.withdraw(10000);
        assertEquals(0, account.getBalance());
    }

}
