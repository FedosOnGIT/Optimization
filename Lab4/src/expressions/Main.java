package expressions;

import expressions.binaryOp.Add;
import expressions.binaryOp.Multiply;
import expressions.binaryOp.Subtract;
import expressions.unaryOp.Square;
import structures.matrices.Vector;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Vector> testData = List.of(
                new Vector(1., 5.),
                new Vector(10., -20.),
                new Vector(500., 700.)
        );
        Expression expression = new Subtract(
                new Multiply(new Const(100), new Square(new Subtract(new Variable("x2"), new Square(new Variable("x1"))))),
                new Square(new Subtract(Const.ONE, new Variable("x1"))));
        calc(expression, testData);
        calc(expression.diff(Variable.createVariable(1)), testData);
        calc(expression.diff(new Variable("x2")), testData);
    }

    public static void calc(Expression expression, List<Vector> testData) {
        for (Vector data : testData) {
            System.out.println(expression.evaluate(data));
        }
        System.out.println();
    }
}
