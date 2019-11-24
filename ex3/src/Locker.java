import oop.ex3.spaceship.Item;

import java.util.Map;

public class Locker implements Storage {
    private LongTermStorage longTermStorage;
    private final int capacity;
    private Item[][] constraints;

    /**
     *Constructor for Locker class
     * @param lts LongTermStorage object
     * @param capacity Int represent locker capacity
     * @param constraints Matrix represent locker constraints
     */
    public Locker(LongTermStorage lts, int capacity, Item[][] constraints) {
        this.longTermStorage = lts;
        this.capacity = capacity;
        this.constraints = constraints;
    }

    /**
     *Add new item for locker
     * @param item Item object
     * @param n Item storage unit
     * @return
     */
    @Override
    public int addItem(Item item, int n) {
        return 0;
    }

    /**
     *
     * @param type
     * @return
     */
    @Override
    public int getItemCount(String type) {
        return 0;
    }

    /**
     *
     * @return
     */
    @Override
    public Map<String, Integer> getInventory() {
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public int getCapacity() {
        return 0;
    }

    /**
     *
     * @return
     */
    @Override
    public int getAvailableCapacity() {
        return 0;
    }

    /**
     *
     * @param item
     * @param n
     * @return
     */
    public int removeItem(Item item, int n) {
        return 0;
    }

}
