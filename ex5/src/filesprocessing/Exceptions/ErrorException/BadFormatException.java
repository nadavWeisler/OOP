package filesprocessing.Exceptions.ErrorException;

public class BadFormatException extends ErrorException {
    public BadFormatException() {
        super("Bad format in command file");
    }
}
