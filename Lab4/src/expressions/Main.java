package expressions;

import expressions.binaryOp.Add;
import expressions.binaryOp.Multiply;
import expressions.binaryOp.Subtract;
import structures.matrices.Vector;

public class Main {
    public static void main(String[] args) {
       System.out.println(
               new Add(
                       new Subtract(
                               new Multiply(
                                       new Variable("x1"),
                                       new Variable("x1")
                               ),
                               new Multiply(
                                       new Const(2.),
                                       new Variable("x2"))
                       ),
                       new Const(1.)
               ).toString()
       );
    }
}
