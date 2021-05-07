package methods;

import structures.LinearMethodResult;

import java.util.function.Function;

public class BrentMethod implements Method {
    private final double Factor = (3 - Math.sqrt(5)) / 2;

    @Override
    public LinearMethodResult minimum(Function<Double, Double> function, double start, double end, double epsilon) {
        LinearMethodResult result = new LinearMethodResult(function);
        double x = start + Factor * (end - start), w = x, v = x;
        double fx = function.apply(x), fw = fx, fv = fx, parabolaMin = 0;
        double d = end - start, e = d, g;
        while (Math.abs(x - (start + end) / 2 + (end - start) / 2) >= 2 * epsilon) {
            result.add(x);
            g = e;
            e = d;
            boolean parabolaAccepted = false;
            if (fx != fw && fx != fv && fw != fv) {
                double x1, x2, x3, f1, f2, f3;
                if (x < w) {
                    if (x < v) {
                        x1 = x;
                        f1 = fx;
                        if (v < w) {
                            x2 = v; f2 = fv; x3 = w; f3 = fw;
                        } else {
                            x2 = w; f2 = fw; x3 = v; f3 = fv;
                        }
                    } else {
                        x1 = v; f1 = fv; x2 = x; f2 = fx; x3 = w; f3 = fw;
                    }
                } else {
                    if (w < v) {
                        x1 = w;
                        f1 = fw;
                        if (v < x) {
                            x2 = v; f2 = fv; x3 = x; f3 = fx;
                        } else {
                            x2 = x; f2 = fx; x3 = v; f3 = fv;
                        }
                    } else {
                        x1 = v; f1 = fv; x2 = w; f2 = fw; x3 = x; f3 = fx;
                    }
                }
                double a1 = (f2 - f1) / (x2 - x1);
                double a2 = ((f3 - f1) / (x3 - x1) - (f2 - f1) / (x2 - x1)) / (x3 - x2);
                parabolaMin = 0.5 * (x1 + x2 - a1/a2);
                if (start <= parabolaMin && parabolaMin <= end && Math.abs(parabolaMin - x) < g / 2) {
                    parabolaAccepted = true;
                    if (parabolaMin - start < epsilon || end - parabolaMin < epsilon) {
                        parabolaMin = x - Math.signum(x - (start + end) / 2) * epsilon;
                    }
                }
            }
            if (!parabolaAccepted) {
                if (x < (start + end) / 2) {
                    parabolaMin = x + Factor * (end - x);
                    e = end - x;
                } else {
                    parabolaMin = x - Factor * (x - start);
                    e = x - start;
                }
            }
            if (Math.abs(parabolaMin - x) < epsilon) {
                parabolaMin = x + Math.signum(parabolaMin - x) * epsilon;
            }
            d = Math.abs(parabolaMin - x);
            double fu = function.apply(parabolaMin);
            if (fu <= fx) {
                if (parabolaMin >= x) {
                    start = parabolaMin;
                } else {
                    end = parabolaMin;
                }
                v = w; w = x; x = parabolaMin; fv = fw; fw = fx; fx = fu;
            } else {
                if (parabolaMin >= x) {
                    end = parabolaMin;
                } else {
                    start = parabolaMin;
                }
                if (fu <= fw || w == x) {
                    parabolaMin = w;
                    w = parabolaMin;
                    fv = fw;
                    fw = fu;
                } else if (fu <= fv || v == x || v == w) {
                    v = parabolaMin;
                    fv = fu;
                }
            }
        }
        result.add(x);
        return result;
    }
}
