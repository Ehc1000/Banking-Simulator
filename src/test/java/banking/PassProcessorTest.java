package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassProcessorTest {

    Bank bank;
    CreateProcessor createProcessor;
    DepositProcessor depositProcessor;
    PassProcessor passProcessor;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        createProcessor = new CreateProcessor(bank);
        depositProcessor = new DepositProcessor(bank);
        passProcessor = new PassProcessor(bank);
    }

    @Test
    void pass_one_month() {
        createProcessor.execute("create checking 12345678 6.0");
        createProcessor.execute("create savings 98765432 3.0");
        depositProcessor.execute("deposit 12345678 1000");
        depositProcessor.execute("deposit 98765432 2000");
        passProcessor.execute("pass 1");
        assertEquals(1005, bank.getAccounts().get("12345678").getBalance());
        assertEquals(2005, bank.getAccounts().get("98765432").getBalance());
    }

    @Test
    void pass_two_months() {
        createProcessor.execute("create checking 12345678 6.0");
        createProcessor.execute("create savings 98765432 3.0");
        depositProcessor.execute("deposit 12345678 1000");
        depositProcessor.execute("deposit 98765432 2000");
        passProcessor.execute("pass 2");
        assertEquals(1010.025, bank.getAccounts().get("12345678").getBalance());
        assertEquals(2010.0125, bank.getAccounts().get("98765432").getBalance());
    }

    @Test
    void pass_one_month_for_cd() {
        createProcessor.execute("create cd 12345679 9.0 5000");
        passProcessor.execute("pass 1");
        assertEquals(5151.695953320313, bank.getAccounts().get("12345679").getBalance());
    }

    @Test
    void deduct_twenty_five_if_balance_less_than_one_hundred() {
        createProcessor.execute("create checking 12345678 6.0");
        depositProcessor.execute("deposit 12345678 75");
        passProcessor.execute("pass 1");
        assertEquals(50.25, bank.getAccounts().get("12345678").getBalance());
    }

    @Test
    void close_single_empty_account() {
        createProcessor.execute("create checking 12345678 6.0");
        passProcessor.execute("pass 1");
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void close_empty_balance_account_with_multiple_accounts() {
        createProcessor.execute("create checking 12345678 6.0");
        createProcessor.execute("create savings 12567890 4.0");
        createProcessor.execute("create savings 98765432 7.0");
        depositProcessor.execute("deposit 98765432 800");
        passProcessor.execute("pass 1");
        assertEquals(1, bank.getAccounts().size());
    }

}
