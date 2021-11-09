import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositProcessorTest {

    CreateProcessor createProcessor;
    DepositProcessor depositProcessor;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        createProcessor = new CreateProcessor(bank);
        depositProcessor = new DepositProcessor(bank);
    }

    @Test
    void deposit_into_checking_account() {
        createProcessor.execute("create checking 12345678 2.0");
        depositProcessor.execute("deposit 12345678 500");
        assertEquals(500, bank.getAccounts().get("12345678").getBalance());
    }

    @Test
    void deposit_into_savings_account() {
        createProcessor.execute("create savings 98765432 5.0");
        depositProcessor.execute("deposit 98765432 1500");
        assertEquals(1500, bank.getAccounts().get("98765432").getBalance());
    }

    @Test
    void deposit_twice_into_checking_account() {
        createProcessor.execute("create checking 12345678 2.0");
        depositProcessor.execute("deposit 12345678 700");
        depositProcessor.execute("deposit 12345678 900");
        assertEquals(1600, bank.getAccounts().get("12345678").getBalance());
    }

    @Test
    void deposit_twice_into_savings_account() {
        createProcessor.execute("create savings 98765432 5.0");
        depositProcessor.execute("deposit 98765432 500");
        depositProcessor.execute("deposit 98765432 2000");
        assertEquals(2500, bank.getAccounts().get("98765432").getBalance());
    }

    @Test
    void deposit_zero_into_checking() {
        createProcessor.execute("create checking 12345678 2.0");
        depositProcessor.execute("deposit 12345678 0");
        assertEquals(0, bank.getAccounts().get("12345678").getBalance());
    }

}
