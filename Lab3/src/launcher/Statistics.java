package launcher;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Statistics {
    private Integer n;
    private Integer k;
    private Long iterations;
    private Double absoluteError;
    private Double ratioError;
    private Double condA;

    public enum Field {
        N, K, RATIO_ERROR, ABSOLUTE_ERROR, ITERATIONS, COND_A;

        private final static Set<Field> DOUBLE_TYPE = Set.of(RATIO_ERROR, ABSOLUTE_ERROR, COND_A);

        public static boolean isDoubleType(Field field) {
            return DOUBLE_TYPE.contains(field);
        }

        private final static Set<Field> LONG_TYPE = Set.of(ITERATIONS);

        public static boolean isLongType(Field field) {
            return LONG_TYPE.contains(field);
        }

        private final static Set<Field> INTEGER_TYPE = Set.of(N, K);

        public static boolean isIntegerType(Field field) {
            return INTEGER_TYPE.contains(field);
        }
    }

    public Statistics() {
        reset();
    }

    public Statistics(Statistics stat) {
        setN(stat.getN());
        setK(stat.getK());
        setIterations(stat.getIterations());
        setAbsoluteError(stat.getAbsoluteError());
        setRatioError(stat.getRatioError());
        setCondA(stat.getCondA());
    }

    public void reset() {
        n = null;
        k = null;
        iterations = 0L;
        absoluteError = null;
        ratioError = null;
        condA = null;
    }

    public void set(Field field, Object data) {
        if ((Field.isIntegerType(field) && !(data instanceof Integer)) ||
            (Field.isLongType(field) && !(data instanceof Long)) ||
            (Field.isDoubleType(field) && !(data instanceof Double))) {
            throw new IllegalArgumentException("data must be same type as field");
        }
        switch (field) {
            case N -> setN((Integer) data);
            case K -> setK((Integer) data);
            case ITERATIONS -> setIterations((Long) data);
            case RATIO_ERROR -> setRatioError((Double) data);
            case ABSOLUTE_ERROR -> setAbsoluteError((Double) data);
            case COND_A -> setCondA((Double) data);
        }
    }

    public void set(Field field, String data) {
        if (Field.isIntegerType(field)) {
            set(field, Integer.parseInt(data));
            return;
        }
        if (Field.isLongType(field)) {
            set(field, Long.parseLong(data));
            return;
        }
        if (Field.isDoubleType(field)) {
            set(field, Double.parseDouble(data));
        }
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Statistics withN(Integer n) {
        setN(n);
        return this;
    }

    public Integer getK() {
        return k;
    }

    public void setK(Integer k) {
        this.k = k;
    }

    public Statistics withK(Integer k) {
        setK(k);
        return this;
    }

    public Long getIterations() {
        return iterations;
    }

    public void setIterations(Long iterations) {
        this.iterations = iterations;
    }

    public void addIterations(Long toAddIterations) {
        iterations += toAddIterations;
    }

    public void addIterations(Integer toAddIterations) {
        iterations += toAddIterations;
    }

    public void incIterations() {
        ++iterations;
    }

    public Statistics withIterations(Long iterations) {
        setIterations(iterations);
        return this;
    }

    public Double getAbsoluteError() {
        return absoluteError;
    }

    public void setAbsoluteError(Double absoluteError) {
        this.absoluteError = absoluteError;
    }

    public Statistics withAbsoluteError(Double absoluteError) {
        setAbsoluteError(absoluteError);
        return this;
    }

    public Double getRatioError() {
        return ratioError;
    }

    public void setRatioError(Double ratioError) {
        this.ratioError = ratioError;
    }

    public Statistics withRatioError(Double ratioError) {
        setRatioError(ratioError);
        return this;
    }

    public Double getCondA() {
        return condA;
    }

    public void setCondA(Double condA) {
        this.condA = condA;
    }

    public Statistics withCondA(Double condA) {
        setCondA(condA);
        return this;
    }

    public void log(Writer writer, Field... fields) throws IOException {
        log(writer, ",", fields);
    }

    public void logLn(Writer writer, Field... fields) throws IOException {
        logLn(writer, ",", "\n", fields);
    }

    public void log(Writer writer, String delimiter, Field... fields) throws IOException {
        List<String> values = new ArrayList<>(fields.length);
        for (Field field : fields) {
            String value = switch (field) {
                case N -> getN().toString();
                case K -> getK().toString();
                case ITERATIONS -> getIterations().toString();
                case RATIO_ERROR -> getRatioError().toString();
                case ABSOLUTE_ERROR -> getAbsoluteError().toString();
                case COND_A -> getCondA().toString();
            };
            values.add(value);
        }
        writer.write(String.join(delimiter, values));
    }

    public void logLn(Writer writer, String delimiter, String newLine, Field... fields) throws IOException {
        log(writer, delimiter, fields);
        writer.write(newLine);
    }

    public static void logHead(Writer writer, Field... fields) throws IOException {
        logHead(writer, ",", fields);
    }

    public static void logHeadLn(Writer writer, Field... fields) throws IOException {
        logHeadLn(writer, ",", "\n", fields);
    }

    public static void logHead(Writer writer, String delimiter, Field... fields) throws IOException {
        writer.write(Arrays.stream(fields)
                .map(Field::toString)
                .map(s -> s.replace('_', ' '))
                .collect(Collectors.joining(delimiter)));
    }

    public static void logHeadLn(Writer writer, String delimiter, String newLine, Field... fields) throws IOException {
        logHead(writer, delimiter, fields);
        writer.write(newLine);
    }

    public static String generateStringFormat(Field... fields) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            sb.append(field).append("=%").append(Field.isDoubleType(field) ? 'f' : 'd');
            if (i != fields.length - 1) {
                sb.append("_");
            }
        }
        return sb.toString();
    }
}
