package structures.matrices;

import structures.FileReadable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class FileReadableMatrix extends Matrix implements FileReadable {
    public static double[][] readToDense(Path file) throws IOException {
        double[][] values = null;
        try (var reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                Diagonal diag = Diagonal.parseDiagonal(line);
                int n = Math.abs(diag.getNumber()) + diag.getVector().size();
                values = new double[n][n];
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
}
