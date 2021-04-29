import quadraticMethods.QuadraticMethod;
import structures.QuadraticFunction;
import structures.Vector;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Vladislav Gusev (vladislav.sg@yandex.ru)
 */
public class GuiHelper {
    /**
     * Первый аргумент - номер функции от 1 до 3
     * Второй аргумент - позиция выбранного метода в списке Testing.QUADRATIC_METHODS
     * Третий аргумент - координата x1 стартовой точки
     * Четвёртый аргумент - координата x2 стартовой точки
     *
     * Логи итераций будут записаны в файл .\gui_logs.txt
     */
    public static void main(String[] args) throws IOException {
        if (args == null || args.length != 4)
            throw new IllegalArgumentException();

        int funcId = Integer.parseInt(args[0]) - 1;
        int methodId = Integer.parseInt(args[1]);
        double[] cord = new double[2];
        cord[0] = Double.parseDouble(args[2]);
        cord[1] = Double.parseDouble(args[3]);
        if (funcId < 0 || funcId >= Testing.FUNCTIONS.size())
            throw new IllegalArgumentException("Function number not in [1;" + Testing.FUNCTIONS.size() + "]");
        if (methodId < 0 || methodId >= Testing.QUADRATIC_METHODS.size()) {
            throw new IllegalArgumentException("Method number not in [0;" + (Testing.QUADRATIC_METHODS.size()-1) + "]");
        }

        QuadraticFunction function = Testing.FUNCTIONS.get(funcId);
        QuadraticMethod method = Testing.QUADRATIC_METHODS.get(methodId);

        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Path.of("gui_logs.txt")))) {
            method.minimum(function, new Vector(cord), 1e-2).write(writer);
        }
    }
}
