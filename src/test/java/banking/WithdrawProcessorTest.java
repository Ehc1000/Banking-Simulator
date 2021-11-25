package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WithdrawProcessorTest {

    CreateProcessor createProcessor;
    DepositProcessor depositProcessor;
    WithdrawProcessor withdrawProcessor;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        createProcessor = new CreateProcessor(bank);
        depositProcessor = new DepositProcessor(bank);
        withdrawProcessor = new WithdrawProcessor(bank);
    }

    @Test
    void withdraw_from_checking() {
        createProcessor.execute("create checking 12345678 3.0");
        depositProcessor.execute("deposit 12345678 500");
        withdrawProcessor.execute("withdraw 12345678 200");
        assertEquals(300, bank.getAccounts().get("12345678").getBalance());
    }

    @Test
    void withdraw_twice_from_checking() {
        createProcessor.execute("create checking 12345678 3.0");
        depositProcessor.execute("deposit 12345678 500");
        withdrawProcessor.execute("withdraw 12345678 200");
        withdrawProcessor.execute("withdraw 12345678 200");
        assertEquals(100, bank.getAccounts().get("12345678").getBalance());
    }

    @Test
    void withdraw_from_savings() {
        createProcessor.execute("create savings 98765432 5.0");
        depositProcessor.execute("deposit 98765432 2000");
        withdrawProcessor.execute("withdraw 98765432 800");
        assertEquals(1200, bank.getAccounts().get("98765432").getBalance());
    }

    @Test
    void withdraw_full_amount_from_cd() {
        createProcessor.execute("create cd 12345679 3.0 7000");
        withdrawProcessor.execute("withdraw 12345679 7000");
        assertEquals(0, bank.getAccounts().get("12345679").getBalance());
    }
}
