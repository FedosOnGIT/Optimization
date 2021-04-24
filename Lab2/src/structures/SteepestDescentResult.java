package structures;

import java.util.ArrayList;

/**
 * @author Vladislav Gusev (vladislav.sg@yandex.ru)
 */
public class SteepestDescentResult extends MethodResult<Vector> {
    private ArrayList<AlphaPair> alphas;

    public void addAlpha(final AlphaPair e) {
        alphas.add(e);
    }

    public AlphaPair getAlpha(int i) {
        return alphas.get(i);
    }
}
