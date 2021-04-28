package structures;

import java.io.PrintWriter;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Vladislav Gusev (vladislav.sg@yandex.ru)
 */
public class QuadraticMethodResult extends MethodResult<Vector> {
    private final QuadraticFunction func;

    public QuadraticMethodResult(QuadraticFunction func) {
        this.func = func;
    }

    protected String tableHeader() {
        return IntStream.rangeClosed(1, func.size())
                .mapToObj(i -> "x" + i)
                .collect(Collectors.joining(","))
                + ",func";
    }

    @Override
    public void write(PrintWriter writer) {
        writeHeader(writer);
        writer.println(tableHeader());
        for (int i = 0; i < iterations(); i++) {
            writer.println(get(i));
        }
    }
}
