package structures.elements;

public class DoubleElement extends Element<Double> {
    private final static double EPS = 1e-14;

    private final static DoubleElement ZERO = new DoubleElement(0d);
    private final static DoubleElement ONE = new DoubleElement(1d);

    private final Double value;

    public DoubleElement(Double value) {
        this.value = value;
    }

    @Override
    public Double get() {
        return value;
    }

    @Override
    public Element<Double> getZero() {
        return ZERO;
    }

    @Override
    public Element<Double> getOne() {
        return ONE;
    }

    @Override
    protected Element<Double> pack(Double value) {
        return new DoubleElement(value);
    }

    @Override
    protected Double divideImpl(Double value) {
        return this.value / value;
    }

    @Override
    protected Double multiplyImpl(Double value) {
        return this.value * value;
    }

    @Override
    protected Double addImpl(Double value) {
        return this.value + value;
    }

    @Override
    protected Double subtractImpl(Double value) {
        return this.value - value;
    }

    @Override
    protected Double sqrtImpl() {
        return Math.sqrt(value);
    }

    @Override
    protected Double negateImpl() {
        return -value;
    }

    @Override
    protected Double minImpl(Double value) {
        return Math.min(this.value, value);
    }

    @Override
    protected Double maxImpl(Double value) {
        return Math.max(this.value, value);
    }

    @Override
    public int compareTo(Element<Double> o) {
        if (Math.abs(value - o.get()) < EPS) {
            return 0;
        }
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
