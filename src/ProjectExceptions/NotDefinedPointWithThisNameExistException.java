package ProjectExceptions;

/**
 * Created by drapek on 27.12.15.
 */
public class NotDefinedPointWithThisNameExistException extends Exception {
    public NotDefinedPointWithThisNameExistException() {

    }

    public NotDefinedPointWithThisNameExistException(String message) {
        super(message);
    }


}
