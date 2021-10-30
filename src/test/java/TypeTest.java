import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TypeTest {
    TypeValidator typeValidator;

    @BeforeEach
    void setUp() {
        typeValidator = new TypeValidator();
    }

    @Test
    void account_type_is_valid() {
        boolean actual = typeValidator.validateType("create cd 12345678 7.0 5000");
        boolean actual_two = typeValidator.validateType("create savings 98765432 9.0");
        assertTrue(actual);
        assertTrue(actual_two);
    }

    @Test
    void account_type_case_sensitive_is_valid() {
        boolean actual = typeValidator.validateType("create CHECKING 12345678 8.0");
        assertTrue(actual);
    }

    @Test
    void account_type_has_typo() {
        boolean actual = typeValidator.validateType("create svings 98765432 1.5");
        assertFalse(actual);
    }

    @Test
    void account_type_is_neither_checking_savings_nor_cd() {
        boolean actual = typeValidator.validateType("create account 12345678 2.5");
        assertFalse(actual);
    }

    @Test
    void account_type_is_missing() {
        boolean actual = typeValidator.validateType("create 98765432 3.5");
        assertFalse(actual);
    }
}
