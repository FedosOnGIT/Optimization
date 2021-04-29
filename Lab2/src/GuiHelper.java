import quadraticMethods.QuadraticMethod;
import quadraticMethods.SteepestDescent;
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
    private static void sizeAssert(int pos, int size, String message) throws IllegalArgumentException {
        if (pos < 0 || pos >= size)
            throw new IllegalArgumentException(message + " not in [0;" + size + "]");
    }

    /**
     * Первый аргумент - позиция выбранной функции в списке Testing.FUNCTIONS
     * Второй аргумент - позиция выбранного метода в списке Testing.QUADRATIC_METHODS
     * Третий аргумент - координата x1 стартовой точки
     * Четвёртый аргумент - координата x2 стартовой точки
     * Пятый аргумент - выбранный метод одномерной оптимизации (опционально)
     *
     * Логи итераций будут записаны в файл .\gui_logs.txt
     */
    public static void main(String[] args) throws IOException {
        if (args == null || args.length != 4 && args.length != 5)
            throw new IllegalArgumentException("Illegal arguments count");

        int funcId = Integer.parseInt(args[0]) - 1;
        int methodId = Integer.parseInt(args[1]);
        double[] cord = new double[2];
        cord[0] = Double.parseDouble(args[2]);
        cord[1] = Double.parseDouble(args[3]);
        sizeAssert(funcId, Testing.FUNCTIONS.size(), "Function number");
        sizeAssert(methodId, Testing.QUADRATIC_METHODS.size(), "Method number");

        QuadraticFunction function = Testing.FUNCTIONS.get(funcId);
        QuadraticMethod method;
        if (methodId == 1) {
            if (args.length != 5) {
                throw new IllegalArgumentException("Linear Method number is absent");
            }
            int linearMethodId = Integer.parseInt(args[4]);
            sizeAssert(linearMethodId, Testing.LINEAR_METHODS.size(), "Linear Method number");
            method = new SteepestDescent(Testing.LINEAR_METHODS.get(linearMethodId));
        } else {
            if (args.length != 4) {
                throw new IllegalArgumentException("Used Quadratic Method not use Linear Methods");
            }
            method = Testing.QUADRATIC_METHODS.get(methodId);
        }

        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Path.of("gui_logs.txt")))) {
            method.minimum(function, new Vector(cord), 1e-2).write(writer);
        }
    }
}
