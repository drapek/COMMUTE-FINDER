package ProjectExceptions;

/**
 * Created by drapek on 27.12.15.
 */
public class NotDefinedPointWithThisIdExistException extends Exception {
    public NotDefinedPointWithThisIdExistException() {

    }

    public NotDefinedPointWithThisIdExistException(String message) {
        super(message);
    }
}
