package launcher;

import expressions.Expression;
import expressions.Variable;
import structures.Gradient;
import structures.Hessian;
import structures.matrices.Vector;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static expressions.Variable.createVariable;

public class FunctionData {
    private final Function<Vector, Double> function;
    private final Gradient gradient;
    private final Hessian hessian;

    public FunctionData(Expression function, int variables) {
        this.function = function::evaluate;
        gradient = new Gradient(
                IntStream.rangeClosed(1, variables)
                        .mapToObj(i -> function.diff(createVariable(i)))
                        .map(f -> (Function<Vector, Double>) (f::evaluate))
                        .collect(Collectors.toList())
        );
        hessian = new Hessian(
                IntStream.rangeClosed(1, variables)
                        .mapToObj(i ->
                                IntStream.rangeClosed(1, variables)
                                        .mapToObj(j -> function.diff(createVariable(i)).diff(createVariable(j)))
                                        .map(f -> (Function<Vector, Double>) (f::evaluate))
                                        .collect(Collectors.toList())
                        )
                        .collect(Collectors.toList())
        );
    }

    public Function<Vector, Double> getFunction() {
        return function;
    }

    public Gradient getGradient() {
        return gradient;
    }

    public Hessian getHessian() {
        return hessian;
    }
}
