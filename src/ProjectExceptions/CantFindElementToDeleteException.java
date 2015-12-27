package ProjectExceptions;

/**
 * Created by drapek on 27.12.15.
 */
public class CantFindElementToDeleteException extends Exception{
    public CantFindElementToDeleteException() {

    }

    public CantFindElementToDeleteException(String message) {
        super(message);
    }
}
