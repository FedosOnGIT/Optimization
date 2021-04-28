package structures;

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

    @Override
    protected String variablesHeader() {
        return IntStream.rangeClosed(1, func.size())
                .mapToObj(i -> "x" + i)
                .collect(Collectors.joining(","));
    }

    @Override
    protected String getTableLine(int i) {
        return getTableLinePrefix(i, func);
    }
}
