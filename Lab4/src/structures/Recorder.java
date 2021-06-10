package structures;

import java.util.*;

/**
 * @author Vladislav Gusev (vladislav.sg@yandex.ru)
 */
public class Recorder {
    private final List<double[]> data = new ArrayList<>();
    private final Map<String, Integer> map = new HashMap<>();

    public Recorder() {}

    public Recorder(String... header) {
        addToHeader(header);
    }

    public void addToHeader(String... header) {
        int i = map.size();
        for (String s : header) {
            map.put(s, i++);
        }
    }

    public void newIter() {
        double[] iter = new double[map.size()];
        Arrays.fill(iter, Double.NaN);
        data.add(iter);
    }

    public void set(String s, double value) {
        if (!map.containsKey(s)) {
            throw new IllegalArgumentException("Key " + s + " was not passed to the constructor");
        }
        set(map.get(s), value);
    }

    public void set(int i, double value) {
        assert i < map.size();
        data.get(data.size()-1)[i] = value;
    }
}
