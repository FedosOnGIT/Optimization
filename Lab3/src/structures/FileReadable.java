package structures;

import java.nio.file.Path;

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

    static <T extends FileReadable> T init(Class<T> clazz, Path source) {
        try {
            return clazz.getConstructor(Path.class).newInstance(source);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid input - can not create object from source file");
        }
    }
}
