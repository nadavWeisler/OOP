package filesprocessing;

import filesprocessing.Exceptions.ErrorException.ErrorException;
import filesprocessing.Exceptions.ErrorException.IOProblemException;
import filesprocessing.Exceptions.ErrorException.InvalidUsageException;

import java.io.*;
import java.util.ArrayList;

public class DirectoryProcessor {
    private static void TestArgs(String[] args) throws ErrorException {
        if (args.length != 2) {
            throw new InvalidUsageException();
        }

        File dir = new File(args[0]);
        File file = new File(args[1]);

        if (!dir.isDirectory() || !file.isFile()) {
            System.out.println("Not valid directory");
            throw new InvalidUsageException();
        }

        if (dir.exists() || file.exists()) {
            System.out.println("Not valid file");
            throw new IOProblemException();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            TestArgs(args);
            ArrayList<Section> sections = Parser.CreateSectionsFromFileName(args[1]);

            for (Section section : sections) {
                section.PrintSection(args[0]);
            }

        } catch (ErrorException err) {
            System.out.println(err.getMessage());
        }
    }
}
