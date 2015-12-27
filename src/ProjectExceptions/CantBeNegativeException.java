package ProjectExceptions;

/**
 * Created by drapek on 27.12.15.
 */
public class CantBeNegativeException extends Exception  {
    public CantBeNegativeException() {

    }

    public CantBeNegativeException( String message) {
        super(message);
    }
}
