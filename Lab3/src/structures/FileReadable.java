package structures;

import structures.matrices.Diagonal;
import structures.matrices.Vector;

import java.io.IOException;
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

    static Diagonal parseDiagonal(String diag) {
        String[] parts = diag.split(" ");
        return new Diagonal(Integer.parseInt(parts[0]),
                new Vector(IntStream.range(2, parts.length)
                        .mapToObj(i -> FileReadable.clearElement(parts[i]))
                        .map(Double::parseDouble)
                )
        );
    }

    static void createDirectoryIfNotExists(Path directory) throws IOException {
        if (Files.notExists(directory)) {
            Files.createDirectories(directory);
        }
    }
}
