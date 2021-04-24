package structures;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * @author Vladislav Gusev (vladislav.sg@yandex.ru)
 */
public class SteepestDescentResult extends MethodResult<Vector> {
    private final ArrayList<AlphaPair> alphas = new ArrayList<>();

    public void addAlpha(final AlphaPair e) {
        alphas.add(e);
    }

    public AlphaPair getAlpha(int i) {
        return alphas.get(i);
    }

    @Override
    public void write(PrintWriter writer) {
        writer.println("min = " + getMinimal().toString());
        writer.println("quadratic iterations = " + iterations());
        writer.println("points,alpha,linear iterations");
        for (int i = 0; i < iterations(); i++) {
            writer.print(get(i).toString());
            writer.print(',');
            writer.print(getAlpha(i).getAlpha());
            writer.print(',');
            writer.println(getAlpha(i).getIterations());
        }
    }
}
