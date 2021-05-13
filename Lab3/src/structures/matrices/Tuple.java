package structures.matrices;

import structures.elements.Element;

import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public abstract class Tuple<T extends Number> {
    public abstract Element<T> get(int index);
    public abstract void set(int index, Element<T> value);
    public abstract int size();

    private Tuple<T> apply(Consumer<Integer> operationByIndex) {
        int size = size();
        for (int i = 0; i < size; i++) {
            operationByIndex.accept(i);
        }
        return this;
    }

    public Tuple<T> add(Tuple<T> other) {
        assert size() == other.size();
        return apply(i -> get(i).add(other.get(i)));
    }

    public Tuple<T> mul(Element<T> alpha) {
        return apply(i -> get(i).mul(alpha));
    }

    public Element<T> norm() {
        return IntStream.range(0, size())
                .mapToObj(i -> get(i).copy().mul(get(i)))
                .reduce(get(0).getZero(), (a, b) -> a.copy().add(b))
                .sqrt();
    }

    public Tuple<T> normalize() {
        Element<T> norm = norm();
        return apply(i -> get(i).div(norm));
    }

    public Element<T> scalar(Tuple<T> other) {
        assert size() == other.size();
        return IntStream.range(0, size())
                .mapToObj(i -> get(i).copy().mul(other.get(i)))
                .reduce(get(0).getZero(), (a, b) -> a.copy().add(b));
    }
}
