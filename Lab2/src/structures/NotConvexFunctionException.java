package structures;

import java.io.IOException;

public class NotConvexFunctionException extends IOException {
    public NotConvexFunctionException(String message) {
        super(message);
    }

    public NotConvexFunctionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotConvexFunctionException(Throwable cause) {
        super(cause);
    }
}
