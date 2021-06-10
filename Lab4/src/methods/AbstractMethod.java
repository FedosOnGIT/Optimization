package methods;

import structures.Recorder;
import structures.matrices.Vector;

/**
 * @author Vladislav Gusev (vladislav.sg@yandex.ru)
 */
public abstract class AbstractMethod implements Method {
    private Recorder rec;

    protected void initRecorder(int dim) {
        rec = new Recorder();
        for (int i = 1; i <= dim; ++i) {
            rec.addToHeader("x" + i);
        }
    }

    protected void writeVector(final Vector vec) {
        for (int i = 0; i < vec.size(); ++i) {
            rec.set(i, vec.get(i));
        }
    }

    public Recorder getRecorder() {
        return rec;
    }
}
