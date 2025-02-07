package methods4.one_dim_methods;

import methods4.AbstractMethod;

import java.util.function.Function;

public class GoldenRatioMethod extends AbstractMinimizationMethod {
    private final double FactorOne = 2.0 / (3.0 + Math.sqrt(5.0));
    private final double FactorTwo = 2.0 / (Math.sqrt(5.0) + 1.0);

    @Override
    public Double min(Function<Double, Double> function, double start, double end, double epsilon) {
        double alpha1 = (end - start) * FactorOne + start;
        double alpha2 = (end - start) * FactorTwo + start;
        double functionOne = function.apply(alpha1);
        double functionTwo = function.apply(alpha2);
        while (end - start > 2 * epsilon) {
            if (functionOne < functionTwo) {
                end = alpha2;
                alpha2 = alpha1;
                functionTwo = functionOne;
                alpha1 = (end - start) * FactorOne + start;
                functionOne = function.apply(alpha1);
            } else {
                start = alpha1;
                alpha1 = alpha2;
                functionOne = functionTwo;
                alpha2 = (end - start) * FactorTwo + start;
                functionTwo = function.apply(alpha2);
            }
        }
        return (end + start) / 2;
    }
}
