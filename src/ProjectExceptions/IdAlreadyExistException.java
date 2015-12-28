package ProjectExceptions;

/**
 * Created by drapek on 28.12.15.
 */
public class IdAlreadyExistException extends Exception {
    public IdAlreadyExistException() {

    }

    public IdAlreadyExistException(String message) {
        super(message);
    }
}
