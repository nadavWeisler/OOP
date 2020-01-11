package oop.ex6.main;

import oop.ex6.exceptions.BadFormatException;
import oop.ex6.parsers.FileParser;

import java.io.IOException;

public class Sjavac {
    public static void main(String[] args) {
        try {
            if(args.length != 1) {
                throw new IOException("More than 1 argument");
            } else {
                FileParser.getInstance().ParseFile(args[0]);
            }
            System.out.println(0);
        } catch (IOException e) {
            System.err.println(2);
        } catch (BadFormatException e) {
            System.err.println(1);
            System.err.println(e.getMessage());
        }
    }
}
