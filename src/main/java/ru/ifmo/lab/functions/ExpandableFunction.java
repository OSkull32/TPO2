package ru.ifmo.lab.functions;

import java.math.BigDecimal;
import java.util.Objects;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

public abstract class ExpandableFunction implements BasicFunction{
    private static final int ITERATIONS = 1000;

    protected final int maxIterations;

    protected ExpandableFunction() {
        this.maxIterations = ITERATIONS;
    }

    protected void checkValidity(final BigDecimal x, final BigDecimal precision) {
        Objects.requireNonNull(x, "Аргумент функции не может быть null");
        Objects.requireNonNull(precision, "Точность не может быть null");
        if (precision.compareTo(ZERO) <= 0 || precision.compareTo(ONE) >= 0) {
            throw new ArithmeticException("Точность должна быть больше нуля и меньше единицы");
        }
    }
}
