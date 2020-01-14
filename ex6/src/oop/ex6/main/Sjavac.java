package oop.ex6.main;

import oop.ex6.exceptions.BadFormatException;
import oop.ex6.parsers.FileParser;

import java.io.IOException;

/**
 * Runs the program and contains the main method
 */
public class Sjavac {

    public static void main(String[] args) {
        try {
            if(args.length != 1) { // The program arg is invalid
                throw new IOException("More than 1 argument");
            } else { // Try to parse the file
                FileParser.getInstance().ParseFile(args[0]);
            }
            System.out.println(0); // The file was successfully parsed
        } catch (IOException e) { //
            System.out.println(2);
        } catch (BadFormatException e) {
            System.out.println(1); // The code is illegal
            System.err.println(e.getMessage());
        }
    }
}
