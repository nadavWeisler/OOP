package filesprocessing.Exceptions;

public class WarningException extends Exception {
    /**
     * Warning exception constructor
     *
     * @param line Line number
     */
    public WarningException(int line) {
        super("Warning in line " + line);
    }
}
