package structures;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Function;

public abstract class MethodResult<T> {
    private final ArrayList<T> points;

    public MethodResult() {
        points = new ArrayList<>();
    }

    public int iterations() {
        return points.size() - 1;
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

    private void writeMainResult(PrintWriter writer) {
        writer.println("min = " + getMinimal());
        writer.println("quadratic iterations = " + iterations());
    }

    protected abstract String tableHeader();

    protected abstract String getTableLine(int i);

    protected String getTableLinePrefix(int i, Function<T, Double> func) {
        T point = get(i);
        return point + "," + func.apply(point);
    }

    public void write(PrintWriter writer) {
        writeMainResult(writer);
        writer.println(tableHeader());
        for (int i = 0; i < iterations(); i++) {
            writer.println(getTableLine(i));
        }
    }
}
