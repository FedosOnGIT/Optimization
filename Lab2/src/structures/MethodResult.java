package structures;

import java.util.ArrayList;

public class MethodResult<T> {
    private T minimal;
    private final ArrayList<T> points;

    public MethodResult() {
        points = new ArrayList<>();
    }

    public int iterations() {
        return points.size();
    }

    public void setMinimal(final T minimal) {
        this.minimal = minimal;
        points.add(minimal);
    }

    public T getMinimal() {
        return minimal;
    }

    public T get(int index) {
        return points.get(index);
    }

    public void add(T element) {
        points.add(element);
    }
}
