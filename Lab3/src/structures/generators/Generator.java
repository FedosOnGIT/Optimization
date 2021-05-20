package structures.generators;

import java.io.IOException;
import java.nio.file.Path;

public interface Generator  {
    void generate(Path directory) throws IOException;
}
