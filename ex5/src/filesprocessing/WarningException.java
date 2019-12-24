package filesprocessing;

public class WarningException extends Exception {
    public WarningException(int line) {
        super("Warning in line " + line);
    }
}
