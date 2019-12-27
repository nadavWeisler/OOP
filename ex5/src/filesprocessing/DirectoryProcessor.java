package filesprocessing;

import filesprocessing.Exceptions.ErrorException;

import java.io.*;

public class DirectoryProcessor {
    private static boolean TestArgs(String[] args) {
        if (args.length < 2) {
            System.out.println("Less than two args");
            return false;
        }

        if (args.length > 2) {
            System.out.println("More than two args");
            return false;
        }

        File dir = new File(args[0]);
        File file = new File(args[1]);

        if (!dir.isDirectory() || dir.exists()) {
            System.out.println("Not valid directory");
            return false;
        }

        if (!file.isFile() || file.exists()) {
            System.out.println("Not valid file");
            return false;
        }

        return true;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) throws ErrorException {
        if (!TestArgs(args)) {
            throw new ErrorException();
        }

        File dir = new File(args[0]);
        File file = new File(args[1]);



    }


}
