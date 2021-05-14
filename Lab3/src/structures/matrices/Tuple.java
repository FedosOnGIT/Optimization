package structures.matrices;

import structures.elements.Element;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

public abstract class Tuple<T> {
    public abstract Element<T> get(int index);

    public abstract void set(int index, Element<T> element);

    public abstract int size();

    public void swap(int i, int j) {
        Element<T> first = get(i);
        set(i, get(j));
        set(j, first);
    }

    public Element<T> getZero() {
        return get(0).getZero();
    }

    public Element<T> getOne() {
        return get(0).getOne();
    }

    private Tuple<T> apply(Function<Integer, Element<T>> operationByIndex) {
        int size = size();
        for (int i = 0; i < size; i++) {
            set(i, operationByIndex.apply(i));
        }
        return this;
    }

    public Tuple<T> add(Tuple<T> other) {
        assert size() == other.size();
        return apply(i -> get(i).add(other.get(i)));
    }

    public Tuple<T> multiply(Element<T> alpha) {
        return apply(i -> get(i).multiply(alpha));
    }

    public Element<T> norm() {
        return IntStream.range(0, size())
                .mapToObj(i -> get(i).multiply(get(i)))
                .reduce(getZero(), Element::add)
                .sqrt();
    }

    public Tuple<T> normalize() {
        Element<T> norm = norm();
        return apply(i -> get(i).divide(norm));
    }

    public Element<T> scalar(Tuple<T> other) {
        assert size() == other.size();
        return IntStream.range(0, size())
                .mapToObj(i -> get(i).multiply(other.get(i)))
                .reduce(getZero(), Element::add);
    }

}
