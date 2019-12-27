package filesprocessing;

import filesprocessing.filterPackage.Filter;
import filesprocessing.orderPackage.Order;

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
}
