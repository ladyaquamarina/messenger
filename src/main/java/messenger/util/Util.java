package messenger.util;

public class Util {
    public static final String SUCCESS = "Success";
    public static final String UNAUTHORIZED_ACCESS_ATTEMPT = "Unauthorized access attempt";

    public static final Throwable UNAUTHORIZED_ACCESS_ATTEMPT_EXCEPTION = new Exception(UNAUTHORIZED_ACCESS_ATTEMPT);
}
