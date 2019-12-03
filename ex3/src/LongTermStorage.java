import oop.ex3.spaceship.Item;

import java.util.HashMap;
import java.util.Map;

public class LongTermStorage extends Storage {
    /**
     * Default capacity of long term storage
     */
    private final int DEFAULT_CAPACITY = 1000;

    /**
     * LongTermStorage Constructor
     */
    LongTermStorage() {
        super();
        this.capacity = DEFAULT_CAPACITY;
        this.inventory = new HashMap<>();
        this.items = new HashMap<>();
    }

    /**
     * Add new item for Long Term Storage
     *
     * @param item Item to add
     * @param n    Amount of items to add
     * @return int
     */
    @Override
    public int addItem(Item item, int n) {
        int result = 0;
        if (n < 0) {
            result = -1;
        } else {
            if (this.getAvailableCapacity() >= item.getVolume() * n) {
                if (this.items.containsKey(item.getType())) {
                    this.inventory.put(item.getType(), this.inventory.get(item.getType()) + n);
                } else {
                    this.items.put(item.getType(), item);
                    this.inventory.put(item.getType(), n);
                }
            } else {
                result = -1;
            }
        }
        if (result != 0) {
            System.out.println(this.GenerateError(result, "addItem", item.getType(), n));
        }
        return result;
    }

    /**
     * Reset inventory and items
     */
    public void resetInventory() {
        this.inventory = new HashMap<>();
        this.items = new HashMap<>();
    }
}
