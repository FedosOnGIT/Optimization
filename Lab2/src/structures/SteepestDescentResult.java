package structures;

import java.io.PrintWriter;
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
    public void write(PrintWriter writer) {
        writeHeader(writer);
        writer.println(tableHeader() + ",alpha,linear iterations");
        for (int i = 0; i < iterations() - 1; i++) {
            writer.print(get(i));
            writer.print(',');
            AlphaPair alpha = getAlpha(i);
            writer.print(alpha.getAlpha());
            writer.print(',');
            writer.println(alpha.getIterations());
        }
        writer.print(get(iterations() - 1));
    }
}
