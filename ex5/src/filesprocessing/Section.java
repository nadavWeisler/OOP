package filesprocessing;

import filesprocessing.filterPackage.Filter;
import filesprocessing.orderPackage.Order;

import java.io.File;
import java.util.ArrayList;

public class Section {
    private Filter filter;
    private Order order;

    /**
     * Section constructor
     *
     * @param newFilter Filter object
     * @param newOrder  Order object
     */
    public Section(Filter newFilter, Order newOrder) {
        this.filter = newFilter;
        this.order = newOrder;
    }

    /**
     * @param dirPath
     */
    public void PrintSection(String dirPath) {
        File dir = new File(dirPath);
        ArrayList<File> sortedFiles;
        sortedFiles = this.filter.FilterFile(dir.listFiles());
        sortedFiles = this.order.OrderList(sortedFiles);
        for (File file : sortedFiles) {
            System.out.println(file.getName());
        }
    }
}
