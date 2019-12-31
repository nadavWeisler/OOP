package filesprocessing;

import filesprocessing.filterPackage.Filter;
import filesprocessing.orderPackage.Order;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

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
     * @param dirPath Directory path
     */
    public void PrintSection(String dirPath) {
        File dir = new File(dirPath);
        ArrayList<File> sortedFiles;
        if (dir.listFiles() != null) {
            sortedFiles = this.filter.FilterFile(dir.listFiles());
            sortedFiles = this.order.OrderList(sortedFiles);
            IterateSection(sortedFiles);
        }
    }

    private void IterateSection(ArrayList<File> sortedFiles) {
        for (File file : sortedFiles) {
            if (!file.isDirectory()) {
                System.out.println(file.getName());
            }
        }
    }
}
