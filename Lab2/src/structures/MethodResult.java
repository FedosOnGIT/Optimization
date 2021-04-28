package structures;

import java.io.PrintWriter;
import java.util.ArrayList;

public abstract class MethodResult<T> {
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

    protected void writeHeader(PrintWriter writer) {
        writer.println("min = " + getMinimal());
        writer.println("quadratic iterations = " + iterations());
    }

    public abstract void write(PrintWriter writer);
}
