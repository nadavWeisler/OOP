package filesprocessing;

public class ErrorException extends Exception {
    public String ErrorException(String errorMsg) {
        System.err.println("ERROR: " + "\n" + errorMsg);
        return ("ERROR: " + errorMsg+ "\n");
    }
}
