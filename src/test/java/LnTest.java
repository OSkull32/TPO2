import static java.math.BigDecimal.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import ru.ifmo.lab.logarithm.Ln;

class LnTest {

    private static final BigDecimal DEFAULT_PRECISION = new BigDecimal("0.00001");

    @Test
    void testNotPrecisionMoreThanOne() {
        final Ln ln = new Ln();
        assertThrows(ArithmeticException.class, () -> ln.calculate(ONE, TWO));
    }

    @Test
    void testNotPrecisionLessThanMinusOne() {
        final Ln ln = new Ln();
        assertThrows(ArithmeticException.class, () -> ln.calculate(ONE, new BigDecimal(-2)));
    }


    @Test
    void testNotCalculateForZero() {
        final Ln ln = new Ln();
        assertThrows(ArithmeticException.class, () -> ln.calculate(ZERO, DEFAULT_PRECISION));
    }

    @Test
    void testCalculateForOne() {
        final Ln ln = new Ln();
        assertEquals(ZERO, ln.calculate(ONE, DEFAULT_PRECISION));
    }

    @Test
    void testCalculateForPositive() {
        final Ln ln = new Ln();
        final BigDecimal expected = new BigDecimal("1.38629");
        assertEquals(expected, ln.calculate(new BigDecimal(4), DEFAULT_PRECISION));
    }
}