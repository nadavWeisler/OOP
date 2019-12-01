import oop.ex3.spaceship.Item;

import java.util.HashMap;
import java.util.Map;

public class Locker extends Storage {
    private LongTermStorage longTermStorage;
    private Item[][] constraints;
    private int capacity;

    /**
     * Constructor for Locker class
     *
     * @param lts         LongTermStorage object
     * @param capacity    Int represent locker capacity
     * @param constraints Matrix represent locker constraints
     */
    public Locker(LongTermStorage lts, int capacity, Item[][] constraints) {
        this.longTermStorage = lts;
        this.constraints = constraints;
        this.inventory = new HashMap<>();
        this.capacity = capacity;
    }

    private boolean ItemContainInArray(Item[] array, Item item) {
        for (Item i :
                array) {
            if (i == item) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add new item for locker
     *
     * @param item Item object
     * @param n    Item storage unit
     * @return
     */
    @Override
    public int addItem(Item item, int n) {
        int result = 0;
        for (Item[] constraint :
                this.constraints) {
            Item check_constraint = null;
            if (ItemContainInArray(constraint, item)) {
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

        if (this.getInventory().containsKey(item.getType())) {
            int all_item_units = (this.getInventory().get(item.getType()) + n) * item.getVolume();
            if (all_item_units > this.getAvailableCapacity() / 2) {
                if (this.longTermStorage.getAvailableCapacity() >
                        all_item_units - this.getAvailableCapacity() * 0.2) {
                    while (this.longTermStorage.getAvailableCapacity() < item.getVolume() &&
                            this.inventory.get(item.getType()) > 0) {
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
        }

        if (this.getAvailableCapacity() < item.getVolume() * n) {
            result = -1;
        }

        if (this.inventory.containsKey(item.getType())) {
            this.inventory.replace(item.getType(), this.inventory.get(item.getType()) + n);
        } else {
            this.inventory.put(item.getType(), n);
        }

        if (result != 0) {
            String errorMsg = GenerateError(result, "addItem", item.getType(), n);
            System.out.println(errorMsg);
        }

        return result;
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
    public Map<String, Integer> getInventory() {
        return this.inventory;
    }

    /**
     * @return
     */
    @Override
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * @return
     */
    @Override
    public int getAvailableCapacity() {
        int count = this.capacity;
        for (String item :
                this.inventory.keySet()) {
            count -= this.items.get(item).getVolume() * this.inventory.get(item);
        }
        return count;
    }

    private boolean StringInStringArray(String str, String[] strArray) {
        for (String s :
                strArray) {
            if (s.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param item
     * @param n
     * @return
     */
    public int removeItem(Item item, int n) {
        int result = 0;

        if (this.inventory.containsKey(item.getType()) &&
                this.inventory.get(item.getType()) > 0) {
            this.inventory.put(item.getType(), this.inventory.get(item.getType()) - 1);
            if (this.inventory.get(item.getType()) == 0) {
                this.items.remove(item.getType());
            }
        } else {
            result = -1;
        }

        return result;

    }

}
