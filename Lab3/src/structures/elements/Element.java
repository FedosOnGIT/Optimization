package structures.elements;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Element<T extends Number> {
    public abstract T get();

    public abstract void set(T value);

    public abstract Element<T> copy();

    public abstract Element<T> getZero();

    public abstract Element<T> getOne();

    private Element<T> applyBinaryOperation(Consumer<T> operation, Element<T> element) {
        operation.accept(element.get());
        return this;
    }

    private Element<T> applyUnaryOperation(Runnable operation) {
        operation.run();
        return this;
    }

    private Element<T> applyBinaryOperation(Function<Element<T>, Element<T>> operation, Element<T> element) {
        return operation.apply(element);
    }

    protected abstract void divImpl(T value);

    public Element<T> div(Element<T> element) {
        return applyBinaryOperation(this::divImpl, element);
    }

    protected abstract void mulImpl(T value);

    public Element<T> mul(Element<T> element) {
        return applyBinaryOperation(this::mulImpl, element);
    }

    protected abstract void addImpl(T value);

    public Element<T> add(Element<T> element) {
        return applyBinaryOperation(this::addImpl, element);
    }

    protected abstract void subImpl(T value);

    public Element<T> sub(Element<T> element) {
        return applyBinaryOperation(this::subImpl, element);
    }

    protected abstract void sqrtImpl();

    public Element<T> sqrt() {
        return applyUnaryOperation(this::sqrtImpl);
    }

    protected abstract void negateImpl();

    public Element<T> negate() {
        return applyUnaryOperation(this::negateImpl);
    }

    protected abstract Element<T> minImpl(Element<T> element);

    public Element<T> min(Element<T> element) {
        return applyBinaryOperation(this::minImpl, element);
    }

    protected Element<T> maxImpl(Element<T> element) {
        Element<T> result = min(element);
        return result == this ? element : this;
    }

    public Element<T> max(Element<T> element) {
        return applyBinaryOperation(this::maxImpl, element);
    }

}
