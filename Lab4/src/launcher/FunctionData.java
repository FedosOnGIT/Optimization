package launcher;

import structures.Gradient;
import structures.matrices.Vector;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

public class FunctionData {
//    private final Function<Vector, Double> function;
//    private final Gradient gradient;
//    private final Getian getian;
//
//    public FunctionData(Function<Vector, Double> function, Gradient gradient, Getian getian) {
//        this.function = function;
//        this.gradient = gradient;
//        this.getian = getian;
//    }
//
//    public FunctionData(BiFunction<Double, Double, Double> function, List<BiFunction<Double, Double, Double>> gradient,
//                        List<List<BiFunction<Double, Double, Double>>> getian) {
//        this.function = v -> function.apply(v.get(0), v.get(1));
//
//        final BiFunction<Vector, Integer, Double> gradientHelper = (v, i) -> gradient.get(i).apply(v.get(0), v.get(1));
//        this.gradient = new Gradient(v -> gradientHelper.apply(v, 0), v -> gradientHelper.apply(v, 1));
//
//        this.getian =
//    }
//
//    public Function<Vector, Double> getFunction() {
//        return function;
//    }
//
//    public Gradient getGradient() {
//        return gradient;
//    }
//
//    public Getian getGetian() {
//        return getian;
//    }
}
