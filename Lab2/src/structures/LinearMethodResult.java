package structures;

import java.util.function.Function;

/**
 * @author Vladislav Gusev (vladislav.sg@yandex.ru)
 */
public class LinearMethodResult extends MethodResult<Double> {
    private final Function<Double, Double> func;

    public LinearMethodResult(Function<Double, Double> func) {
        this.func = func;
    }

    @Override
    protected String tableHeader() {
        return "x,func";
    }

    @Override
    protected String getTableLine(int i) {
        return getTableLinePrefix(i, func);
    }
}
