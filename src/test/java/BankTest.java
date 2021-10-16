import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankTest {
    Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
    }

    @Test
    void bank_has_no_accounts_initially() {
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void add_account_to_bank() {
        bank.addAccount("12345678", "Checking", 5.0, 0.0);
        assertEquals(5.0, bank.getAccounts().get("12345678").getRate());
    }

    @Test
    void add_multiple_accounts_to_bank() {
        bank.addAccount("12345678", "Checking", 5.0, 0.0);
        bank.addAccount("23456789", "Savings", 1.5, 0.0);
        assertEquals(1.5, bank.getAccounts().get("23456789").getRate());
    }

    @Test
    void deposit_into_account_in_bank() {
        bank.addAccount("12345678", "Checking", 7.5, 0.0);
        bank.getAccounts().get("12345678").deposit(2000);
        assertEquals(2000, bank.getAccounts().get("12345678").getBalance());
    }

    @Test
    void withdraw_from_account_in_bank() {
        bank.addAccount("34567890", "CD", 9.5, 6000.00);
        bank.getAccounts().get("34567890").withdraw(4000);
        assertEquals(2000.00, bank.getAccounts().get("34567890").getBalance());
    }
}
