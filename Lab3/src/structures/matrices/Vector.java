package structures.matrices;

import structures.elements.Element;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Vector<T> extends Tuple<T> {
    private final List<Element<T>> elements;

    public Vector(List<Element<T>> elements) {
        this.elements = elements;
    }

    public Vector(Stream<Element<T>> elementStream) {
        this(elementStream.collect(Collectors.toList()));
    }

    public Vector(int size, Element<T> element) {
        this(IntStream.range(0, size).mapToObj(i -> element));
    }

    @Override
    public Element<T> get(int index) {
        return elements.get(index);
    }

    @Override
    public void set(int index, Element<T> element) {
        elements.set(index, element);
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public String toString() {
        return elements.toString();
    }

}
