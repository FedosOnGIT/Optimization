package structures.generators;

import structures.elements.Element;
import structures.matrices.DenseMatrix;

import java.util.function.Supplier;

public class GilbertGenerator<T> extends Task2Generator<T> {
    public GilbertGenerator(Element<T> zero, Element<T> one) {
        super(null, zero, one);
    }

    @Override
    protected void generateMatrix(int n) {
        matrix = new DenseMatrix<T>(n, n, zero);
        Element<T> iValue = one;
        for (int i = 0; i < n; i++) {
            Element<T> jValue = one;
            for (int j = 0; j < n; j++) {
                matrix.set(i, j, one.divide(iValue.add(jValue).subtract(one)));
                jValue = jValue.add(one);
            }
            iValue = iValue.add(one);
        }
    }
}
