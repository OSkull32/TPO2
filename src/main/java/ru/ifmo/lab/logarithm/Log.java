package ru.ifmo.lab.logarithm;

import ru.ifmo.lab.functions.ExpandableFunction;

import java.math.BigDecimal;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_EVEN;

public class Log extends ExpandableFunction {
    private final Ln ln;
    private final int base;

    public Log(final int base) {
        super();
        this.ln = new Ln();
        this.base = base;
    }

    public Log(final Ln ln, final int base) {
        super();
        this.ln = ln;
        this.base = base;
    }

    @Override
    public BigDecimal calculate(final BigDecimal x, final BigDecimal precision)
            throws ArithmeticException {
        checkValidity(x, precision);

        if (x.compareTo(ZERO) <= 0) {
            throw new ArithmeticException(format("Значение функции для аргумента %s не существует", x));
        }

        final BigDecimal result =
                ln.calculate(x, precision)
                        .divide(
                                ln.calculate(new BigDecimal(base), precision),
                                DECIMAL128.getPrecision(),
                                HALF_EVEN
                        );

        return result.setScale(precision.scale(), HALF_EVEN);
    }
}
