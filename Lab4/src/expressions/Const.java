package expressions;

import structures.matrices.Vector;

public class Const implements Expression {
    public static final Const ONE = new Const(1.);
    public static final Const ZERO = new Const(0.);
    public static final Const E = new Const(Math.E);

    private final Double value;

    public Const(Double value) {
        this.value = value;
    }

    public Const(Integer value) {
        this.value = value.doubleValue();
    }

    @Override
    public Double evaluate(Vector v) {
        return value;
    }

    @Override
    public Expression diff(Variable v) {
        return ZERO;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        return value.equals(((Const) obj).getValue());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    private Double getValue() {
        return value;
    }
}
