import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.Assert;

import java.util.HashMap;

public abstract class StorageTest {

    /**
     * Storage object
     */
    protected static Storage storage;

    /**
     *  LongTermStorage object
     */
    protected static LongTermStorage longTermStorage;

    /**
     *  Football item
     */
    protected static Item item_football;

    /**
     * Helmet1 item
     */
    protected static Item item_helmet1;

    /**
     * Helmet2 item
     */
    protected static Item item_helmet2;

    /**
     *  Spore Engine item
     */
    protected static Item item_sporesEngine;

    /**
     *  Baseball bat item
     */
    protected static Item item_baseballBat;

    /**
     *  Current class string for tests
     */
    protected static String currentClass;

    /**
     * Prepare storage tests
     */
    public static void PrepareTest() {
        currentClass = "Storage";
        item_football = ItemFactory.createSingleItem("football");
        item_helmet1 = ItemFactory.createSingleItem("helmet, size 1");
        item_helmet2 = ItemFactory.createSingleItem("helmet, size 3");
        item_sporesEngine = ItemFactory.createSingleItem("spores engine");
        item_baseballBat = ItemFactory.createSingleItem("baseball bat");
        longTermStorage = new LongTermStorage();
    }

    /**
     * Test getItemCount method
     */
    protected static void GetItemCountTest() {
        int count = 1;

        //Storage GetItemCountTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetItemCountTest"),
                0,
                storage.getItemCount(item_football.getType()));
        count++;

        //Storage GetItemCountTest Test 2
        storage.addItem(item_helmet1, 1);
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetItemCountTest"),
                0,
                storage.getItemCount(item_football.getType()));
    }

    /**
     * Test getInventory method
     */
    protected static void GetInventoryTest() {
        int count = 1;

        //Storage GetInventoryTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetInventoryTest"),
                new HashMap<String, Integer>(),
                storage.getInventory());
        count++;

        storage.addItem(item_helmet1, 1);
        HashMap<String, Integer> returnValue = new HashMap<>();
        returnValue.put(item_helmet1.getType(), 1);

        //Storage GetInventoryTest Test 2
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetInventoryTest"),
                returnValue,
                storage.getInventory());
        count++;

        storage.addItem(item_helmet1, 1);
        returnValue.put(item_helmet1.getType(), 2);

        //Storage GetInventoryTest Test 3
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetInventoryTest"),
                returnValue,
                storage.getInventory());
    }

    /**
     * Test getCapacity method
     */
    protected static void GetCapacityTest() {
        int count = 1;
        int current_capacity = storage.getCapacity();

        //Storage GetCapacityTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetCapacityTest"),
                current_capacity,
                storage.getCapacity());
        count++;

        storage.addItem(item_helmet1, 4);

        //Storage GetCapacityTest Test 2
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetCapacityTest"),
                current_capacity,
                storage.getCapacity());
        count++;

        storage.addItem(item_sporesEngine, 1);

        //Storage GetCapacityTest Test 3
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetCapacityTest"),
                current_capacity,
                storage.getCapacity());
        count++;

        storage.addItem(item_football, 189);

        //Storage GetCapacityTest Test 4
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetCapacityTest"),
                current_capacity,
                storage.getCapacity());

    }

    /**
     * Test getAvailableCapacity method
     */
    protected static void GetAvailableCapacityTest() {
        int count = 1;
        int current_capacity = storage.getCapacity();

        //Storage GetAvailableCapacityTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetAvailableCapacityTest"),
                current_capacity,
                storage.getAvailableCapacity());
        count++;

        //Storage GetAvailableCapacityTest Test 2
        storage.addItem(item_helmet1, 1);
        current_capacity -= item_helmet1.getVolume();
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetAvailableCapacityTest"),
                current_capacity,
                storage.getAvailableCapacity());

    }
}
