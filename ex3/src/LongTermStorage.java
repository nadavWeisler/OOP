import oop.ex3.spaceship.Item;

import java.util.Map;

public class LongTermStorage implements Storage {
    /**
     *
     */
    private final int START_CAPACITY = 1000;

    /**
     *
     */
    private int current_capacity;

    /**
     *
     */
    public LongTermStorage(){
        this.current_capacity = 0;
    }

    /**
     *
     * @param item
     * @param n
     * @return
     */
    @Override
    public int addItem(Item item, int n) {
        return 0;
    }

    /**
     *
     */
    public void resetInventory(){

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
     * @return
     */
    @Override
    public Map<String, Integer> getInventory() {
        return null;
    }
}
