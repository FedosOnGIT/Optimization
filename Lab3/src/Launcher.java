import methods.Gauss;
import methods.LU;
import methods.Method;
import structures.elements.DoubleElement;
import structures.generators.GilbertGenerator;
import structures.matrices.Matrix;
import structures.matrices.Vector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Launcher {
    private final static Path TESTS_MAIN_DIRECTORY = Path.of("tests");

    private static void generateTests() {
        try {
            if (!Files.exists(TESTS_MAIN_DIRECTORY)) {
                Files.createDirectory(TESTS_MAIN_DIRECTORY);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Can not create tests directory");
        }

    }

    public static void main(String[] args) {
        try {
            generateTests();
            // task1();
            //task2();
            //task3();
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }
}
