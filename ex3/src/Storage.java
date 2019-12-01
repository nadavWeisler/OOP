import oop.ex3.spaceship.Item;

import java.util.HashMap;
import java.util.Map;

public abstract class Storage {
    protected HashMap<String, Item> items;
    protected HashMap<String, Integer> inventory;

    protected String GenerateError(int result, String func, String type, int n) {
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
    }

    /**
     * @param item
     * @param n
     * @return
     */
    public abstract int addItem(Item item, int n);

    /**
     * @param type
     * @return
     */
    public abstract int getItemCount(String type);

    /**
     * @return
     */
    public abstract int getCapacity();

    /**
     * @return
     */
    public abstract int getAvailableCapacity();

    /**
     * @return
     */
    public abstract Map<String, Integer> getInventory();
}
