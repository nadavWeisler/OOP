import oop.ex3.spaceship.Item;

import java.util.HashMap;
import java.util.Map;

public class Locker extends Storage {
    private LongTermStorage longTermStorage;
    private Item[][] constraints;

    /**
     * Constructor for Locker class
     *
     * @param lts         LongTermStorage object
     * @param capacity    Int represent locker capacity
     * @param constraints Matrix represent locker constraints
     */
    Locker(LongTermStorage lts, int capacity, Item[][] constraints) {
        super();
        this.longTermStorage = lts;
        this.constraints = constraints;
        this.inventory = new HashMap<>();
        this.items = new HashMap<>();
        this.capacity = capacity;
    }

    /**
     * Add new item for locker
     *
     * @param item Item object
     * @param n    Item storage unit
     * @return int
     */
    @Override
    public int addItem(Item item, int n) {
        int result = 0;
        for (Item[] constraint :
                this.constraints) {
            Item check_constraint = null;
            if (Utils.ItemContainInArray(constraint, item)) {
                if (constraint[0] == item) {
                    check_constraint = constraint[1];
                } else {
                    check_constraint = constraint[0];
                }
            }
            if (check_constraint != null) {
                if (this.getInventory().containsKey(check_constraint.getType())) {
                    result = -2;
                }
            }
        }
        int all_item_units = n * item.getVolume();
        if (this.getInventory().containsKey(item.getType())) {
            all_item_units += this.getInventory().get(item.getType()) * item.getVolume();
        }
        if (all_item_units > this.getCapacity() / 2) {
            if (this.longTermStorage.getAvailableCapacity() >
                    all_item_units - this.getAvailableCapacity() * 0.2) {
                while (this.longTermStorage.getAvailableCapacity() > item.getVolume() && (n > 0 ||
                        (this.inventory.containsKey(item.getType()) && this.inventory.get(item.getType()) > 0))) {
                    if (n > 0) {
                        n--;
                    } else {
                        this.inventory.put(item.getType(), this.inventory.get(item.getType()) - 1);
                    }
                    this.longTermStorage.addItem(item, 1);
                }
            } else {
                result = -1;
            }
        }

        if (this.getAvailableCapacity() < item.getVolume() * n) {
            result = -1;
        }

        if (this.inventory.containsKey(item.getType())) {
            this.inventory.put(item.getType(), this.inventory.get(item.getType()) + n);
        } else {
            this.inventory.put(item.getType(), n);
        }
        this.items.put(item.getType(), item);

        if (result != 0) {
            String errorMsg = GenerateError(result, "addItem", item.getType(), n);
            System.out.println(errorMsg);
        }

        return result;
    }

    /**
     * Remove item from locker
     *
     * @param item Item to remove
     * @param n    count
     * @return int
     */
    public int removeItem(Item item, int n) {

        int result = 0;

        if (n < 0) {
            result = -2;
        } else if (this.inventory.containsKey(item.getType()) &&
                this.inventory.get(item.getType()) >= n) {
            this.inventory.put(item.getType(), this.inventory.get(item.getType()) - 1);
            if (this.inventory.get(item.getType()) == 0) {
                this.items.remove(item.getType());
            }
        } else {
            result = -1;
        }

        if (result != 0) {
            String errorMsg = this.GenerateError(result, "removeItem", item.getType(), n);
            System.out.println(errorMsg);
            if (result == -2) {
                result = -1;
            }
        }
        return result;

    }

}
