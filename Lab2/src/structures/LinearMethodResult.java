package structures;

import java.io.PrintWriter;
import java.util.function.Function;

/**
 * @author Vladislav Gusev (vladislav.sg@yandex.ru)
 */
public class LinearMethodResult extends MethodResult<Double> {
    private final Function<Double, Double> func;

    public LinearMethodResult(Function<Double, Double> func) {
        this.func = func;
    }

    public void write(PrintWriter writer) {
        writeHeader(writer);
        writer.println("x,func");
        for (int i = 0; i < iterations(); i++) {
            Double point = get(i);
            writer.print(point);
            writer.print(',');
            writer.println(func.apply(point));
        }
    }
}
