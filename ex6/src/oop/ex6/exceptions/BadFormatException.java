package oop.ex6.exceptions;

/**
 * Exception class that represent illegal code exception threw the program
 */
public class BadFormatException extends Exception {

    public BadFormatException(String message) {
        super(message);
    }
}
