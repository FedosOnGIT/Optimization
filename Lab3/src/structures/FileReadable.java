package structures;

import structures.matrices.Diagonal;
import structures.matrices.Matrix;
import structures.matrices.Vector;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

public interface FileReadable {
    static String clearElement(String str) {
        int l = 0, r = str.length();
        if (str.charAt(0) == '[') {
            ++l;
        }
        if (str.charAt(r - 1) == ',' || str.charAt(r - 1) == ']') {
            --r;
        }
        return str.substring(l, r);
    }

    static void createDirectoryIfNotExists(Path directory) throws IOException {
        if (Files.notExists(directory)) {
            Files.createDirectories(directory);
        }
    }

    static <T extends Matrix & FileReadable> T init(Class<T> clazz, Path source) {
        try {
            return clazz.getConstructor(Path.class).newInstance(source);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid class token - can not create object from source file");
        }
    }

}
