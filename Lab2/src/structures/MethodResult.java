package structures;

import java.io.PrintWriter;
import java.io.Writer;
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

    public void write(PrintWriter writer) {
        writer.println("min = " + getMinimal().toString());
        writer.println("quadratic iterations = " + iterations());
        writer.println("points:");
        for (int i = 0; i < iterations(); i++) {
            writer.println(get(i).toString());
        }
    }
}
