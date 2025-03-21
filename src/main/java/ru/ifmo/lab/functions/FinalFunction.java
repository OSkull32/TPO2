package ru.ifmo.lab.functions;

import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_EVEN;

import ch.obermuhlner.math.big.BigDecimalMath;
import ru.ifmo.lab.logarithm.Ln;
import ru.ifmo.lab.logarithm.Log;
import ru.ifmo.lab.trigonometry.Cos;
import ru.ifmo.lab.trigonometry.Cot;
import ru.ifmo.lab.trigonometry.Csc;
import ru.ifmo.lab.trigonometry.Sec;
import ru.ifmo.lab.trigonometry.Sin;
import ru.ifmo.lab.trigonometry.Tan;

import java.math.BigDecimal;
import java.math.MathContext;

public class FinalFunction implements BasicFunction {

    private final Sin sin;
    private final Tan tan;
    private final Cos cos;
    private final Cot cot;
    private final Csc csc;
    private final Sec sec;
    private final Ln ln;
    private final Log log2;
    private final Log log3;

    public FinalFunction() {
        this.sin = new Sin();
        this.tan = new Tan();
        this.cos = new Cos();
        this.cot = new Cot();
        this.csc = new Csc();
        this.sec = new Sec();
        this.ln = new Ln();
        this.log2 = new Log(2);
        this.log3 = new Log(3);
    }

    @Override
    public BigDecimal calculate(final BigDecimal x, final BigDecimal precision) {
        final MathContext mc = new MathContext(DECIMAL128.getPrecision(), HALF_EVEN);
        final BigDecimal correctedX = x.remainder(BigDecimalMath.pi(mc).multiply(new BigDecimal(2)));

        if (x.compareTo(ZERO) <= 0) {
            BigDecimal cscX = csc.calculate(correctedX, precision);
            BigDecimal cotX = cot.calculate(correctedX, precision);
            BigDecimal cosX = cos.calculate(correctedX, precision);
            BigDecimal sinX = sin.calculate(correctedX, precision);
            BigDecimal tanX = tan.calculate(correctedX, precision);
            BigDecimal secX = sec.calculate(correctedX, precision);

            // Вычисление числителя
            BigDecimal numeratorPart1 = cscX.multiply(cotX).pow(3);
            BigDecimal numeratorPart2 = cosX.divide(tanX, mc);
            BigDecimal numeratorPart3 = numeratorPart1.divide(numeratorPart2, mc);
            BigDecimal numeratorPart4 = numeratorPart3.multiply(tanX).add(cotX);
            BigDecimal numeratorPart5 = numeratorPart4.divide(tanX.subtract(cotX), mc);
            BigDecimal numeratorPart6 = numeratorPart5.pow(3).add(cosX);
            BigDecimal numeratorPart7 = numeratorPart6.multiply(cscX.multiply(cotX));
            BigDecimal numeratorPart8 = numeratorPart7.pow(2).pow(3).pow(3);

            // Вычисление знаменателя
            BigDecimal denominatorPart1 = cotX.subtract(secX.add(tanX));
            BigDecimal denominatorPart2 = denominatorPart1.divide(cotX, mc);
            BigDecimal denominatorPart3 = secX.subtract(cosX);
            BigDecimal denominatorPart4 = sinX.add(secX);
            BigDecimal denominatorPart5 = sinX.pow(2);
            BigDecimal denominatorPart6 = denominatorPart4.add(denominatorPart5);
            BigDecimal denominatorPart7 = tanX.subtract(cscX);
            BigDecimal denominatorPart8 = denominatorPart7.subtract(tanX);
            BigDecimal denominatorPart9 = denominatorPart8.subtract(tanX.subtract(sinX));
            BigDecimal denominatorPart10 = denominatorPart6.divide(sinX.add(denominatorPart9), mc);
            BigDecimal denominatorPart11 = denominatorPart10.divide(cotX.pow(2), mc);
            BigDecimal denominatorPart12 = denominatorPart3.add(denominatorPart11);
            BigDecimal denominator = denominatorPart2.subtract(denominatorPart12);

            return numeratorPart8.divide(denominator, mc).setScale(precision.scale(), HALF_EVEN);
        } else {
            BigDecimal log2X = log2.calculate(correctedX, precision);
            BigDecimal lnX = ln.calculate(correctedX, precision);
            BigDecimal log3X = log3.calculate(correctedX, precision);

            BigDecimal part1 = log2X.multiply(lnX).multiply(lnX);
            BigDecimal part2 = part1.divide(log2X, mc);
            BigDecimal part3 = part2.subtract(log2X);
            BigDecimal part4 = log3X.subtract(lnX);

            return part3.multiply(part4).setScale(precision.scale(), HALF_EVEN);
        }
    }
}