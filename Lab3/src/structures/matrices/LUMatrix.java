package structures.matrices;

import structures.elements.Element;
import structures.elements.Operation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LUMatrix<T extends Number> extends Matrix<T> {
    //private final Vector<T> L;
    //private final int[] positionsL;
    //private final Vector<T> U;
    //private final int[] positionsU;
    private final int size;

    @SuppressWarnings("unchecked")
    private Element<T>[][] getMatrix(Matrix<T> main) {
        return (Element<T>[][]) Collections.nCopies(size, Collections.nCopies(size, main.get(0, 0).getZero()).toArray()).toArray();
    }

    public LUMatrix(Matrix<T> main) {
        size = main.size();
        Element<T>[][] LMatrix = getMatrix(main);
        Element<T>[][] UMatrix = getMatrix(main);
        LMatrix[0][0] = main.get(0, 0);
        UMatrix[0][0] = main.get(0, 0).getOne();
        Operation<T> operation = new Operation<>();
        for (int i = 1; i < size; i++) {
            if (LMatrix[i - 1][i - 1].isZero()) {
                throw new IllegalArgumentException("Minor is zero!");
            }
            for (int j = 0; j < i - 1; j++) {
                LMatrix[i][j] = main.get(i, j);
                for (int k = 0; k < j; k++) {
                    LMatrix[i][j].sub(operation.multiply(LMatrix[i][k], UMatrix[k][j]));
                }
                UMatrix[j][i] = main.get(j, i);
                for (int k = 0; k < j; k++) {
                    UMatrix[j][i].sub(operation.multiply(LMatrix[j][k], UMatrix[k][i]));
                }
                UMatrix[j][i].div(LMatrix[j][j]);
            }
        }
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int rowsCount() {
        return 0;
    }

    @Override
    public int columnsCount() {
        return 0;
    }

    @Override
    protected Element<T> getImpl(int i, int j) {
        return null;
    }

    @Override
    protected void setImpl(int i, int j, Element<T> element) {

    }
}
