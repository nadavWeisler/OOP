package filesprocessing;

import filesprocessing.Exceptions.ErrorException.*;
import filesprocessing.filterPackage.Filter;
import filesprocessing.filterPackage.FilterFactory;
import filesprocessing.orderPackage.OrderFactory;

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
     * Create section array list from file
     *
     * @param fileName File name
     * @return Section array list
     * @throws ErrorException Error
     */
    public static ArrayList<Section> CreateSectionsFromFileName(String fileName) throws ErrorException {
        ArrayList<Section> result = new ArrayList<>();
        ArrayList<String> fileList = Utils.fileToArrayList(fileName);
        Filter newFilter = null;
        boolean doneOrder = true;
        boolean inFilter = false;
        boolean inOrder = false;
        for (int i = 0; i < fileList.size(); i++) {
            if (fileList.get(i).equals(Utils.FILTER_SECTION)) {
                if (inOrder) {
                    inFilter = true;
                    inOrder = false;
                    result.add(new Section(newFilter, OrderFactory.CreateOrder("", i)));
                } else if (inFilter) {
                    inFilter = false;
                    newFilter = FilterFactory.CreateFilter("", i);
                } else {
                    if(doneOrder) {
                        inFilter = true;
                        doneOrder = false;
                    } else {
                        throw new MissedSubSectionException();
                    }
                }
            } else if (fileList.get(i).equals(Utils.ORDER_SECTION)) {
                if (inFilter) {
                    newFilter = FilterFactory.CreateFilter("", i);
                    inFilter = false;
                    inOrder = true;
                } else if (inOrder) {
                    result.add(new Section(newFilter, OrderFactory.CreateOrder("", i)));
                    inOrder = false;
                } else {
                    inOrder = true;
                }
            } else {
                if (inFilter) {
                    newFilter = FilterFactory.CreateFilter(fileList.get(i), i);
                    inFilter = false;
                } else if (inOrder) {
                    result.add(new Section(newFilter, OrderFactory.CreateOrder(fileList.get(i), i)));
                    newFilter = null;
                    doneOrder = true;
                } else {
                    throw new BadFormatException();
                }
            }
        }

        return result;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            TestArgs(args);
            ArrayList<Section> sections = CreateSectionsFromFileName(args[1]);

            for (Section section : sections) {
                section.PrintSection(args[0]);
            }

        } catch (ErrorException err) {
            System.out.println(err.getMessage());
        }
    }
}
