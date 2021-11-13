package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateProcessorTest {
    CreateProcessor createProcessor;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        createProcessor = new CreateProcessor(bank);
    }

    @Test
    void create_valid_checking_account() {
        createProcessor.execute("create checking 12345678 2.0");
        assertEquals("checking", bank.getAccounts().get("12345678").getName());
        assertEquals(2.0, bank.getAccounts().get("12345678").getRate());
    }

    @Test
    void create_valid_savings_account() {
        createProcessor.execute("create savings 98765432 5.0");
        assertEquals("savings", bank.getAccounts().get("98765432").getName());
        assertEquals(5.0, bank.getAccounts().get("98765432").getRate());
    }

    @Test
    void create_valid_cd_account() {
        createProcessor.execute("create cd 12345679 7.0 3000");
        assertEquals(7.0, bank.getAccounts().get("12345679").getRate());
        assertEquals(3000, bank.getAccounts().get("12345679").getBalance());
    }

}
