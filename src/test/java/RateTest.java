import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RateTest {
    RateValidator rateValidator;

    @BeforeEach
    void setUp() {
        rateValidator = new RateValidator();
    }

    @Test
    void apr_is_valid() {
        boolean actual = rateValidator.validateApr("create savings 98765432 5.0");
        assertTrue(actual);
    }

    @Test
    void apr_is_at_the_boundaries() {
        boolean actual = rateValidator.validateApr("create cd 12345679 0.0 2000");
        boolean actual_two = rateValidator.validateApr("create checking 12345678 10.0");
        assertTrue(actual);
        assertTrue(actual_two);
    }

    @Test
    void apr_is_just_inside_boundaries() {
        boolean actual = rateValidator.validateApr("create savings 98765432 0.01");
        boolean actual_two = rateValidator.validateApr("create checking 12345678 9.99");
        assertTrue(actual);
        assertTrue(actual_two);
    }

    @Test
    void apr_is_out_of_range() {
        boolean actual = rateValidator.validateApr("create checking 12345678 11.0");
        assertFalse(actual);
    }

    @Test
    void apr_is_negative() {
        boolean actual = rateValidator.validateApr("create checking 12345678 -1.0");
        assertFalse(actual);
    }

    @Test
    void apr_is_extremely_large() {
        boolean actual = rateValidator.validateApr("create savings 98765432 1000.0");
        assertFalse(actual);
    }

    @Test
    void apr_is_extremely_small() {
        boolean actual = rateValidator.validateApr("create savings 98765432 -500.0");
        assertFalse(actual);
    }

    @Test
    void apr_is_missing() {
        boolean actual = rateValidator.validateApr("create checking 12345678");
        assertFalse(actual);
    }

    @Test
    void apr_is_not_a_double() {
        boolean actual = rateValidator.validateApr("create checking 12345678 abc");
        assertFalse(actual);
    }
}
