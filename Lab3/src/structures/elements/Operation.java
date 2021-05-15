package structures.elements;

import java.util.function.Function;

public class Operation<T extends Number> {

    public Element<T> plus(final Element<T> first, final Element<T> second) {
        return first.copy().add(second);
    }

    public Element<T> minus(final Element<T> first, final Element<T> second) {
        return first.copy().sub(second);
    }

    public Element<T> multiply(final Element<T> first, final Element<T> second) {
        return first.copy().mul(second);
    }

    public Element<T> divide(final Element<T> first, final Element<T> second) {
        return first.copy().div(second);
    }
}
