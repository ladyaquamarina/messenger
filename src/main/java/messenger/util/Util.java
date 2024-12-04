package messenger.util;

public class Util {
    public static final String SUCCESS = "Success";

    public static void throwException(String message) {
        try {
            throw new Exception(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void checkAuthorizedAccess(Boolean checkNotUser, Boolean checkMatchChat) {
        if (checkNotUser && !checkMatchChat) {
            throwException("Unauthorized access attempts");
        }
    }
}
