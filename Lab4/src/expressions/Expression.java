package expressions;

import structures.matrices.Vector;

public interface Expression {
    Double evaluate(Vector v);

    Expression diff(Variable v);
}
