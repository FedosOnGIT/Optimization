package structures.matrices;

import structures.FileReadable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public abstract class FileReadableMatrix extends Matrix implements FileReadable {
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
                        .mapToObj(i -> clearElement(parts[i]))
                        .map(Double::parseDouble)
                )
        );
    }

    static double[][] readToDense(Path file) throws IOException {
        double[][] values = null;
        try (var reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                Diagonal diag = parseDiagonal(line);
                int n = Math.abs(diag.getNumber()) + diag.getVector().size();
                if (values == null) {
                    values = new double[n][n];
                }
                int i = 0, j = 0, k = 0;
                if (diag.getNumber() > 0) {
                    j = diag.getNumber();
                } else {
                    i = -diag.getNumber();
                }
                while (i < n && j < n) {
                    values[i++][j++] = diag.getVector().get(k++);
                }
            }
        }
        return values;
    }

    static List<Diagonal> readToDiagonals(Path source) throws IOException {
        List<Diagonal> diagonals = new ArrayList<>();
        try (var reader = Files.newBufferedReader(source)) {
            String diag;
            while ((diag = reader.readLine()) != null) {
                diagonals.add(parseDiagonal(diag));
            }
        }
        return diagonals;
    }
}
