package structures.elements;

public class DoubleElement extends Element<Double> {
    private double value;

    DoubleElement(double value) {
        this.value = value;
    }

    @Override
    public Element<Double> copy() {
        return new DoubleElement(value);
    }

    @Override
    public Element<Double> getZero() {
        return new DoubleElement(0);
    }

    @Override
    public Element<Double> getOne() {
        return new DoubleElement(1);
    }

    @Override
    public Double get() {
        return value;
    }

    @Override
    public void set(Double value) {
        this.value = value;
    }

    @Override
    protected void divImpl(Double value) {
        this.value /= value;
    }

    @Override
    protected void mulImpl(Double value) {
        this.value *= value;
    }

    @Override
    protected void addImpl(Double value) {
        this.value += value;
    }

    @Override
    protected void subImpl(Double value) {
        this.value -= value;
    }

    @Override
    protected void sqrtImpl() {
        value = Math.sqrt(value);
    }

    @Override
    protected void negateImpl() {
        value = -value;
    }

    @Override
    protected Element<Double> minImpl(Element<Double> element) {
        if (value >= element.get()) {
            return element;
        }
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
