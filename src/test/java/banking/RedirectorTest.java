package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RedirectorTest {

    Redirector redirector;
    Bank bank;
    CreateValidator createValidator;
    DepositValidator depositValidator;
    WithdrawValidator withdrawValidator;
    TransferValidator transferValidator;
    PassValidator passValidator;
    CreateProcessor createProcessor;
    DepositProcessor depositProcessor;
    WithdrawProcessor withdrawProcessor;
    TransferProcessor transferProcessor;
    PassProcessor passProcessor;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        createValidator = new CreateValidator(bank);
        depositValidator = new DepositValidator(bank);
        withdrawValidator = new WithdrawValidator(bank);
        transferValidator = new TransferValidator(bank);
        passValidator = new PassValidator(bank);
        createProcessor = new CreateProcessor(bank);
        depositProcessor = new DepositProcessor(bank);
        withdrawProcessor = new WithdrawProcessor(bank);
        transferProcessor = new TransferProcessor(bank);
        passProcessor = new PassProcessor(bank);
        redirector = new Redirector(createValidator, depositValidator, withdrawValidator,
                transferValidator, passValidator, createProcessor, depositProcessor,
                withdrawProcessor, transferProcessor, passProcessor);
    }

    @Test
    void create_command() {
        assertTrue(redirector.validate("create checking 12345678 4.0"));
        redirector.execute("create checking 12345678 4.0");
        assertEquals("checking", bank.getAccounts().get("12345678").getName());
    }

    @Test
    void deposit_command() {
        redirector.execute("create checking 12345678 4.0");
        assertTrue(redirector.validate("deposit 12345678 700"));
        redirector.execute("deposit 12345678 700");
        assertEquals(700, bank.getAccounts().get("12345678").getBalance());
    }

    @Test
    void withdraw_command() {
        redirector.execute("create checking 12345678 4.0");
        redirector.execute("deposit 12345678 700");
        assertTrue(redirector.validate("withdraw 12345678 300"));
        redirector.execute("withdraw 12345678 300");
        assertEquals(400, bank.getAccounts().get("12345678").getBalance());
    }

    @Test
    void transfer_command() {
        redirector.execute("create checking 12345678 4.0");
        redirector.execute("create savings 98765432 6.0");
        redirector.execute("deposit 12345678 700");
        redirector.execute("deposit 98765432 1000");
        assertTrue(redirector.validate("transfer 12345678 98765432 200"));
        redirector.execute("transfer 12345678 98765432 200");
        assertEquals(1200, bank.getAccounts().get("98765432").getBalance());
    }

    @Test
    void pass_command() {
        redirector.execute("create checking 12345678 6.0");
        redirector.execute("deposit 12345678 1000");
        assertTrue(redirector.validate("pass 1"));
        redirector.execute("pass 1");
        assertEquals(1005, bank.getAccounts().get("12345678").getBalance());
    }
}
