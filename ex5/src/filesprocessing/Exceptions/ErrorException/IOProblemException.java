package filesprocessing.Exceptions.ErrorException;

public class IOProblemException extends ErrorException {
    public IOProblemException() {
        super("Input\\output error by reading files");
    }
}
