package structures.elements;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigDecimalElement extends Element<BigDecimal> {
    private final static BigDecimalElement ZERO = new BigDecimalElement(BigDecimal.ZERO);
    private final static BigDecimalElement ONE = new BigDecimalElement(BigDecimal.ONE);

    private final BigDecimal value;

    public BigDecimalElement(BigDecimal value) {
        this.value = value;
    }

    @Override
    public BigDecimal get() {
        return value;
    }

    @Override
    public Element<BigDecimal> getZero() {
        return ZERO;
    }

    @Override
    public Element<BigDecimal> getOne() {
        return ONE;
    }

    @Override
    protected Element<BigDecimal> pack(BigDecimal value) {
        return new BigDecimalElement(value);
    }

    @Override
    protected BigDecimal divideImpl(BigDecimal value) {
        return this.value.divide(value, RoundingMode.HALF_UP);
    }

    @Override
    protected BigDecimal multiplyImpl(BigDecimal value) {
        return this.value.multiply(value);
    }

    @Override
    protected BigDecimal addImpl(BigDecimal value) {
        return this.value.add(value);
    }

    @Override
    protected BigDecimal subtractImpl(BigDecimal value) {
        return this.value.subtract(value);
    }

    @Override
    protected BigDecimal sqrtImpl() {
        return value.sqrt(MathContext.UNLIMITED);
    }

    @Override
    protected BigDecimal negateImpl() {
        return value.negate();
    }

    @Override
    protected BigDecimal minImpl(BigDecimal value) {
        return this.value.min(value);
    }

    @Override
    protected BigDecimal maxImpl(BigDecimal value) {
        return this.value.max(value);
    }

    @Override
    public int compareTo(Element<BigDecimal> o) {
        return value.compareTo(o.get());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return value.equals(obj);
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
