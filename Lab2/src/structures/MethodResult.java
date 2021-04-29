package structures;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Function;

public abstract class MethodResult<T> {
    private final ArrayList<T> points;
    private final Function<T, Double> optimizedFunc;

    public MethodResult(Function<T, Double> optimizedFunc) {
        this.points = new ArrayList<>();
        this.optimizedFunc = optimizedFunc;
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
        writer.println("min = " + optimizedFunc.apply(getMinimal()));
        writer.println("quadratic iterations = " + iterations());
    }

    protected String tableHeader() {
        return "№," + variablesHeader() + ",func";
    }

    protected abstract String variablesHeader();

    protected String getTableLine(int i) {
        T point = get(i);
        return i + "," + point + "," + optimizedFunc.apply(point);
    }

    public void write(PrintWriter writer) {
        writeMainResult(writer);
        writer.println(tableHeader());
        for (int i = 0; i < points.size(); i++) {
            writer.println(getTableLine(i));
        }
    }
}
