package structures;

import java.util.function.Function;

/**
 * @author Vladislav Gusev (vladislav.sg@yandex.ru)
 */
public class LinearMethodResult extends MethodResult<Double> {
    public LinearMethodResult(Function<Double, Double> func) {
        super(func);
    }

    @Override
    protected String variablesHeader() {
        return "x";
    }
}
