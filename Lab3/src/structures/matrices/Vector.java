package structures.matrices;

import structures.elements.Element;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Vector<T extends Number> extends Tuple<T> {
    private final List<Element<T>> values;

    public Vector(List<Element<T>> values) {
        this.values = values;
    }

    public Vector(int size, Element<T> value) {
        values = IntStream.range(0, size).mapToObj(i -> value.copy()).collect(Collectors.toList());
    }

    @Override
    public Element<T> get(int index) {
        return values.get(index);
    }

    @Override
    public void set(int index, Element<T> value) {
        values.set(index, value);
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public String toString() {
        return values.toString();
    }
}
