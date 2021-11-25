package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferProcessorTest {

    CreateProcessor createProcessor;
    DepositProcessor depositProcessor;
    TransferProcessor transferProcessor;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        createProcessor = new CreateProcessor(bank);
        depositProcessor = new DepositProcessor(bank);
        transferProcessor = new TransferProcessor(bank);
    }

    @Test
    void transfer_from_checking_to_savings() {
        createProcessor.execute("create checking 12345678 5.0");
        createProcessor.execute("create savings 98765432 6.0");
        depositProcessor.execute("deposit 12345678 900");
        transferProcessor.execute("transfer 12345678 98765432 300");
        assertEquals(600, bank.getAccounts().get("12345678").getBalance());
        assertEquals(300, bank.getAccounts().get("98765432").getBalance());
    }

    @Test
    void transfer_twice_from_checking_to_savings() {
        createProcessor.execute("create checking 12345678 5.0");
        createProcessor.execute("create savings 98765432 6.0");
        depositProcessor.execute("deposit 12345678 900");
        transferProcessor.execute("transfer 12345678 98765432 300");
        transferProcessor.execute("transfer 12345678 98765432 200");
        assertEquals(400, bank.getAccounts().get("12345678").getBalance());
        assertEquals(500, bank.getAccounts().get("98765432").getBalance());
    }

    @Test
    void transfer_from_savings_to_checking() {
        createProcessor.execute("create checking 12345678 5.0");
        createProcessor.execute("create savings 98765432 6.0");
        depositProcessor.execute("deposit 98765432 2000");
        transferProcessor.execute("transfer 98765432 12345678 700");
        assertEquals(1300, bank.getAccounts().get("98765432").getBalance());
        assertEquals(700, bank.getAccounts().get("12345678").getBalance());
    }
}
