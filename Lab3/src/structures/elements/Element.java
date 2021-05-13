package structures.elements;

import java.util.function.Consumer;

public abstract class Element<T extends Number> {
    public abstract T get();

    public abstract void set(T value);

    public abstract Element<T> copy();

    public abstract Element<T> getZero();

    public abstract Element<T> getOne();

    protected abstract void divImpl(T value);

    protected abstract void mulImpl(T value);

    protected abstract void addImpl(T value);

    protected abstract void subImpl(T value);

    protected abstract void sqrtImpl();

    protected abstract void negateImpl();

    private Element<T> applyBinaryOperation(Consumer<T> operation, Element<T> element) {
        operation.accept(element.get());
        return this;
    }

    private Element<T> applyUnaryOperation(Runnable operation) {
        operation.run();
        return this;
    }

    public Element<T> div(Element<T> element) {
        return applyBinaryOperation(this::divImpl, element);
    }

    public Element<T> mul(Element<T> element) {
        return applyBinaryOperation(this::mulImpl, element);
    }

    public Element<T> add(Element<T> element) {
        return applyBinaryOperation(this::addImpl, element);
    }

    public Element<T> sub(Element<T> element) {
        return applyBinaryOperation(this::subImpl, element);
    }

    public Element<T> sqrt() {
        return applyUnaryOperation(this::sqrtImpl);
    }

    public Element<T> negate() {
        return applyUnaryOperation(this::negateImpl);
    }
}
