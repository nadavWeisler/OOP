import oop.ex3.spaceship.Item;

import java.util.Map;

public interface Storage {
    /**
     *
     * @param item
     * @param n
     * @return
     */
    public int addItem(Item item, int n);

    /**
     *
     * @param type
     * @return
     */
    public int getItemCount(String type);

    /**
     *
     * @return
     */
    public int getCapacity();

    /**
     *
     * @return
     */
    public int getAvailableCapacity();

    /**
     *
     * @return
     */
    public Map<String, Integer> getInventory();
}
