package structures;

import java.nio.file.Path;

public interface FileReadable {
    static <T extends FileReadable> T init(Class<T> clazz, Path source) {
        try {
            return clazz.getConstructor(Path.class).newInstance(source);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid input - can not create object from source file");
        }
    }
}
