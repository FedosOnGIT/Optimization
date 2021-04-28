package structures;

import java.util.ArrayList;

/**
 * @author Vladislav Gusev (vladislav.sg@yandex.ru)
 */
public class SteepestDescentResult extends QuadraticMethodResult {
    private final ArrayList<AlphaPair> alphas = new ArrayList<>();

    public SteepestDescentResult(QuadraticFunction func) {
        super(func);
    }

    public void addAlpha(final AlphaPair e) {
        alphas.add(e);
    }

    public AlphaPair getAlpha(int i) {
        return alphas.get(i);
    }

    @Override
    protected String tableHeader() {
        return super.tableHeader() + ",alpha,linear iterations";
    }

    @Override
    protected String getTableLine(int i) {
        String ret = super.getTableLine(i);
        if (i < alphas.size()) {
            AlphaPair alpha = getAlpha(i);
            ret += "," + alpha.getAlpha() + "," + alpha.getIterations();
        }
        return ret;
    }
}
