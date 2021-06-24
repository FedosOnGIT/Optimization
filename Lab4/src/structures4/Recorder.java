package structures4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Vladislav Gusev (vladislav.sg@yandex.ru)
 */
public class Recorder {
    private final List<List<String>> data = new ArrayList<>();
    private final Map<String, Integer> map = new LinkedHashMap<>();

    public Recorder(String... headers) {
        this(Arrays.asList(headers));
    }

    public Recorder(List<String> headers) {
        for (int i = 0; i < headers.size(); i++) {
            map.put(headers.get(i), i);
        }
    }

    public void newIter() {
        data.add( new ArrayList<>(Collections.nCopies(map.size(), "NaN")));
    }

    public void set(String header, Object value) {
        if (!map.containsKey(header)) {
            throw new IllegalArgumentException("Key " + header + " was not passed to the constructor");
        }
        set(map.get(header), value);
    }

    public void set(int i, Object value) {
        assert i < map.size();
        data.get(data.size() - 1).set(i, Objects.toString(value));
    }

    public void addIteration(Object... values) {
        addIteration(Arrays.asList(values));
    }

    public <T> void addIteration(List<T> values) {
        assert values.size() == map.size();
        newIter();
        for (int i = 0; i < values.size(); i++) {
            set(i, values.get(i));
        }
    }

    public void record(Path dest) throws IOException {
        try (var writer = Files.newBufferedWriter(dest)) {
            writer.write(
                    map.keySet().stream().collect(Collectors.joining(",", "", "\n"))
            );
            for (List<String> row : data) {
                writer.write(row.stream().collect(Collectors.joining(",", "", "\n")));
            }
        }
    }

    public int getIterations() {
        return data.size();
    }
}
