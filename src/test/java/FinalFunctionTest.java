import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.ifmo.lab.functions.FinalFunction;

class FinalFunctionTest {

    private static final BigDecimal DEFAULT_PRECISION = new BigDecimal("0.00000001");

    @Test
    void testNotAcceptNullArgument() {
        final FinalFunction system = new FinalFunction();
        assertThrows(NullPointerException.class, () -> system.calculate(null, DEFAULT_PRECISION));
    }

    @Test
    void testNotAcceptNullPrecision() {
        final FinalFunction system = new FinalFunction();
        assertThrows(NullPointerException.class, () -> system.calculate(new BigDecimal(-2), null));
    }

    @ParameterizedTest
    @MethodSource("illegalPrecisions")
    void testNotAcceptIncorrectPrecisions(final BigDecimal precision) {
        final FinalFunction system = new FinalFunction();
        assertThrows(ArithmeticException.class, () -> system.calculate(new BigDecimal(-2), precision));
    }

    @Test
    void testNotAcceptZeroArgument() {
        final FinalFunction system = new FinalFunction();
        assertThrows(ArithmeticException.class, () -> system.calculate(ZERO, DEFAULT_PRECISION));
    }

    @Test
    void testNotAcceptOneArgument() {
        final FinalFunction system = new FinalFunction();
        assertThrows(ArithmeticException.class, () -> system.calculate(ONE, DEFAULT_PRECISION));
    }

    @Test
    void testCalculateForPositiveValue() {
        final FinalFunction system = new FinalFunction();
        final BigDecimal expected = new BigDecimal("-0.03876866");
        assertEquals(expected, system.calculate(new BigDecimal(5), DEFAULT_PRECISION));
    }

    @Test
    void testCalculateForNegativeValue() {
        final FinalFunction system = new FinalFunction();
        final BigDecimal expected = new BigDecimal("-107684.50173849");
        assertEquals(expected, system.calculate(new BigDecimal(-1), DEFAULT_PRECISION));
    }

    private static Stream<Arguments> illegalPrecisions() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(1)),
                Arguments.of(BigDecimal.valueOf(0)),
                Arguments.of(BigDecimal.valueOf(1.01)),
                Arguments.of(BigDecimal.valueOf(-0.01)));
    }
}