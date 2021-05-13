package structures.elements;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Element<T> implements Comparable<Element<T>> {
    public abstract T get();

    public abstract Element<T> getZero();

    public abstract Element<T> getOne();

    protected abstract Element<T> pack(T value);

    protected abstract T divideImpl(T value);

    protected abstract T multiplyImpl(T value);

    protected abstract T addImpl(T value);

    protected abstract T subtractImpl(T value);

    protected abstract T sqrtImpl();

    protected abstract T negateImpl();

    protected abstract T minImpl(T value);

    protected abstract T maxImpl(T value);

    private Element<T> binaryOperation(Function<T, T> operation, Element<T> element) {
        return pack(operation.apply(element.get()));
    }

    private Element<T> unaryOperation(Supplier<T> operation) {
        return pack(operation.get());
    }

    public Element<T> divide(Element<T> element) {
        return binaryOperation(this::divideImpl, element);
    }

    public Element<T> multiply(Element<T> element) {
        return binaryOperation(this::multiplyImpl, element);
    }

    public Element<T> add(Element<T> element) {
        return binaryOperation(this::addImpl, element);
    }

    public Element<T> subtract(Element<T> element) {
        return binaryOperation(this::subtractImpl, element);
    }

    public Element<T> sqrt() {
        return unaryOperation(this::sqrtImpl);
    }

    public Element<T> negate() {
        return unaryOperation(this::negateImpl);
    }

    public Element<T> min(Element<T> element) {
        return binaryOperation(this::minImpl, element);
    }

    public Element<T> max(Element<T> element) {
        return binaryOperation(this::maxImpl, element);
    }

}
