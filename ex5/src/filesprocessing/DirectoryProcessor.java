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
            throw new InvalidUsageException();
        }

        if (!(dir.exists() && file.exists())) {
            throw new IOProblemException();
        }
    }

    private enum fileState {
        waitForFILTER,
        waitForFilterData,
        waitForORDER,
        waitForOrderData
    }

    /**
     * Create section array list from file
     *
     * @param fileName File name
     * @return Section array list
     * @throws ErrorException Error
     */
    public static ArrayList<Section> CreateSectionsFromFileName(String fileName) throws ErrorException {
        ArrayList<Section> result = new ArrayList<Section>();
        ArrayList<String> fileList = Utils.fileToArrayList(fileName);
        Filter newFilter = null;
        String line;
        fileState state = fileState.waitForFILTER;
        for (int i = 0; i < fileList.size(); i++) {
            line = fileList.get(i);
            switch (state) {
                case waitForFILTER:
                    if (!line.equals(Utils.FILTER_SECTION)) {
                        throw new BadFormatException();
                    } else {
                        state = fileState.waitForFilterData;
                    }
                    break;
                case waitForFilterData:
                    newFilter = FilterFactory.CreateFilter(line, i + 1);
                    state = fileState.waitForORDER;
                    break;
                case waitForORDER:
                    if (!line.equals(Utils.ORDER_SECTION)) {
                        throw new BadFormatException();
                    } else {
                        state = fileState.waitForOrderData;
                    }
                    break;
                case waitForOrderData:
                    if (line.equals(Utils.FILTER_SECTION)) {
                        result.add(new Section(newFilter,
                                OrderFactory.CreateOrder(Utils.ABS_ORDER, i + 1)));
                        state = fileState.waitForFilterData;
                    } else {
                        result.add(new Section(newFilter, OrderFactory.CreateOrder(line, i + 1)));
                        state = fileState.waitForFILTER;
                    }
                    break;
            }
        }

        if (state.equals(fileState.waitForOrderData)) {
            result.add(new Section(newFilter, OrderFactory.CreateOrder(Utils.ABS_ORDER, 0)));
        } else if (!state.equals(fileState.waitForFILTER)) {
            throw new BadFormatException();
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
            System.err.println(err.getMessage());
        }
    }
}
