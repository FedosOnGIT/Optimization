package structures.elements;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigDecimalElement extends Element<BigDecimal> {
    private BigDecimal value;

    BigDecimalElement(BigDecimal value) {
        this.value = value;
    }

    @Override
    public Element<BigDecimal> copy() {
        return new BigDecimalElement(value);
    }

    @Override
    public Element<BigDecimal> getZero() {
        return new BigDecimalElement(BigDecimal.ZERO);
    }

    @Override
    public Element<BigDecimal> getOne() {
        return new BigDecimalElement(BigDecimal.ONE);
    }

    @Override
    public BigDecimal get() {
        return value;
    }

    @Override
    public void set(BigDecimal value) {
        this.value = value;
    }

    @Override
    protected void divImpl(BigDecimal value) {
        this.value = value.divide(value, RoundingMode.HALF_UP);
    }

    @Override
    protected void mulImpl(BigDecimal value) {
        this.value = value.multiply(value);
    }

    @Override
    protected void addImpl(BigDecimal value) {
        this.value = value.add(value);
    }

    @Override
    protected void subImpl(BigDecimal value) {
        this.value = value.subtract(value);
    }

    @Override
    protected void sqrtImpl() {
        value = value.sqrt(MathContext.UNLIMITED);
    }

    @Override
    protected void negateImpl() {
        value = value.negate();
    }

    @Override
    protected Element<BigDecimal> minImpl(Element<BigDecimal> element) {
        if (value.compareTo(element.get()) >= 0) {
            return element;
        }
        return this;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
