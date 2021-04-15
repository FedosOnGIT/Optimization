package structures;

import java.util.ArrayList;

public class MethodResult<T> {
    private final ArrayList<T> points;

    public MethodResult() {
        points = new ArrayList<>();
    }

    public int iterations() {
        return points.size();
    }

    public T getMinimal() {
        return points.get(points.size()-1);
    }

    public T get(int index) {
        return points.get(index);
    }

    public void add(T element) {
        points.add(element);
    }
}
