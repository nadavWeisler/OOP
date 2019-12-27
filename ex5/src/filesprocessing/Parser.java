package filesprocessing;

import filesprocessing.Exceptions.ErrorException.BadFormatException;
import filesprocessing.Exceptions.ErrorException.ErrorException;
import filesprocessing.Exceptions.ErrorException.MissedSubSectionException;
import filesprocessing.filterPackage.Filter;
import filesprocessing.filterPackage.FilterFactory;
import filesprocessing.orderPackage.OrderFactory;

import java.util.ArrayList;

public class Parser {
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
}
