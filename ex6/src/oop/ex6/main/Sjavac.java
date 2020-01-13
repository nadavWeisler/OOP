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
            System.err.println(2);
        } catch (BadFormatException e) {
            System.err.println(1); // The code is illegal
            System.err.println(e.getMessage());
        }
    }

    public int MainReturn(String[] args) {
        if(args.length == 2) {
            System.out.println(args[0] + args[1]);
        }
        try {
            if(args.length != 1) { // The program arg is invalid
                throw new IOException("More than 1 argument");
            } else { // Try to parse the file
                FileParser.getInstance().ParseFile(args[0]);
            }
            return 0; // The file was successfully parsed
        } catch (IOException e) { //
            return 2;
        } catch (BadFormatException e) {
            System.err.println(e.getMessage());
            return 1;
        }
    }

    public int testRun(String[] args){
        return MainReturn(args);
    }

}
