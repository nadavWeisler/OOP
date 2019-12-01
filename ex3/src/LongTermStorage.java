import oop.ex3.spaceship.Item;

import java.util.HashMap;
import java.util.Map;

public class LongTermStorage extends Storage {
    /**
     *
     */
    private final int DEFAULT_CAPACITY = 1000;

    public LongTermStorage() {

    }

    /**
     * @param item
     * @param n
     * @return
     */
    @Override
    public int addItem(Item item, int n) {
        if (this.getAvailableCapacity() < item.getVolume() * n) {
            if (this.items.containsKey(item.getType())) {
                this.inventory.put(item.getType(), this.inventory.get(item.getType()) + n);
            } else {
                this.items.put(item.getType(), item);
                this.inventory.put(item.getType(), n);
            }
            return 0;
        } else {
            return -1;
        }
    }

    /**
     *
     */
    public void resetInventory() {
        for (String key :
                this.inventory.keySet()) {
            if (this.items.containsKey(key) && this.inventory.get(key) > 0) {
                return;
            }
        }
        this.inventory = new HashMap<>();
        this.items = new HashMap<>();
    }

    /**
     * @param type
     * @return
     */
    @Override
    public int getItemCount(String type) {
        return this.inventory.getOrDefault(type, 0);
    }

    /**
     * @return
     */
    @Override
    public int getCapacity() {
        return this.DEFAULT_CAPACITY;
    }

    /**
     * @return
     */
    @Override
    public int getAvailableCapacity() {
        int count = this.DEFAULT_CAPACITY;
        for (String key :
                this.items.keySet()) {
            count -= this.items.get(key).getVolume() * this.inventory.get(key);
        }
        return count;
    }

    /**
     * @return
     */
    @Override
    public Map<String, Integer> getInventory() {
        return this.inventory;
    }
}
