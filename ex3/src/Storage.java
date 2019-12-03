import oop.ex3.spaceship.Item;

import java.util.HashMap;
import java.util.Map;

public abstract class Storage {
    HashMap<String, Item> items;
    HashMap<String, Integer> inventory;
    int capacity;

    String GenerateError(int result, String func, String type, int n) {
        String resultString = "";
        switch (func) {
            case "addItem":
                switch (result) {
                    case -1:
                        resultString = "Error: Your request cannot be completed at this time. " +
                                "Problem: no room for " + n + " Items of type " + type;
                        break;
                    case -2:
                        resultString = "Error: Your request cannot be completed at this time. " +
                                "Problem: the locker cannot contain items of type " + type + ", as it " +
                                "contains a contradicting item";
                        break;
                    case 1:
                        resultString = "Warning: Action successful, but has caused items to be moved to storage";
                        break;
                }
            case "removeItem":
                switch (result) {
                    case -1:
                        resultString = "Error: Your request cannot be completed at this time. " +
                                "Problem: the locker does not contain " + n + " items of type " + type;
                        break;
                    case -2:
                        resultString = "Error: Your request cannot be completed at this time. Problem: cannot " +
                                "remove a negative number of items of type " + type;
                        break;
                }
        }
        return resultString;


    }

    protected Storage() {
        this.items = new HashMap<>();
        this.inventory = new HashMap<>();
    }

    /**
     * @param item Add Item to Storage
     * @param n Item count to add
     * @return result int
     */
    public abstract int addItem(Item item, int n);

    /**
     * @param type Item type
     * @return Item count int
     */
    public int getItemCount(String type){
        return this.inventory.getOrDefault(type, 0);
    }

    /**
     * @return Get storage capacity int
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * @return Get available capacity int
     */
    public int getAvailableCapacity() {
        int count = this.capacity;
        for (String key :
                this.items.keySet()) {
            count -= this.items.get(key).getVolume() * this.inventory.get(key);
        }
        return count;
    }

    /**
     * @return Return inventory hash map
     */
    public Map<String, Integer> getInventory() {
        return this.inventory;
    }
}
