package ProjectExceptions;

/**
 * Created by drapek on 27.12.15.
 */
public class NameAlreadyExistException extends Exception {
    public NameAlreadyExistException() {

    }

    public NameAlreadyExistException(String message) {
        super(message);
    }
}
