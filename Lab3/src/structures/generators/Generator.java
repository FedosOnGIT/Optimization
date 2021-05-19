package structures.generators;

import structures.matrices.DenseMatrix;
import structures.matrices.Matrix;
import structures.matrices.Vector;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.IntStream;

public interface Generator  {
    void generate(Path directory) throws IOException;
}
