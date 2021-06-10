
import methods.newton_methods.ChoosingNewton;
import methods.newton_methods.ClassicNewton;
import methods.newton_methods.DescentNewton;
import methods.newton_methods.FunctionMethod;
import methods.one_dim_methods.BrentMethod;
import methods.quasi_methods.DfpMethod;
import methods.quasi_methods.QuasiMethod;
import structures.Gradient;
import structures.Hessian;
import structures.matrices.Vector;

import java.util.function.Function;

/**
 * @author Vladislav Gusev (vladislav.sg@yandex.ru)
 */
public class Main {
    public static Double function(Vector vector) {
        double answer = 0.0;
        answer += vector.get(0) * vector.get(0);
        answer += vector.get(1) * vector.get(1);
        answer -= 1.2 * vector.get(0) * vector.get(1);
        return answer;
    }
    public static void main(String[] args) {
        Gradient gradient = new Gradient(
                vector -> 2 * vector.get(0) - 1.2 * vector.get(1),
                vector -> 2 * vector.get(1) - 1.2 * vector.get(0));
        Function<Vector, Double>[][] coordinates = new Function[][]{
                {vector -> 2.0, vector -> -1.2},
                {vector -> -1.2, vector -> 2.0}
        };
        Hessian hessian = new Hessian(coordinates);
        QuasiMethod dfp = new DfpMethod(new BrentMethod());
        Vector answer = dfp.min(Main::function, hessian, gradient, new Vector(4.0, 1.0), 0.0001);
        System.out.println(answer.toString());
    }
}
