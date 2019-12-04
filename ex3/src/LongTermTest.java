import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LongTermTest extends StorageTest {

    /**
     * Prepare LongTermStorage Tests
     */
    private static void PrepareTestLongTermStorage() {
        PrepareTest();
        currentClass = "LongTermStorage";
    }

    /**
     *  Test addItem method
     */
    private static void AddItemTest() {
        int count = 1;
        storage = new LongTermStorage();

        //LongTermStorage AddItemTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "AddItemTest"),
                0,
                storage.addItem(item_football, 1));

        //LongTermStorage AddItemTest Test 2
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "AddItemTest"),
                0,
                storage.addItem(item_football, 50));

        storage = new LongTermStorage();

        //LongTermStorage AddItemTest Test 3
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "AddItemTest"),
                -1,
                storage.addItem(item_baseballBat, 1000));

        //LongTermStorage AddItemTest Test 4
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "AddItemTest"),
                -1,
                storage.addItem(item_baseballBat, -1));
    }

    /**
     *  Test getCapacity method
     */
    private static void GetCapacityLongTermStorageTest() {
        storage = new LongTermStorage();
        GetCapacityTest();
        int count = 1;

        storage.addItem(item_football, 1);
        LongTermStorage longTermStorage = (LongTermStorage)storage;
        longTermStorage.resetInventory();

        //LongTermStorage GetCapacityTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetCapacityTest"),
                longTermStorage.capacity,
                longTermStorage.getCapacity());
    }

    /**
     *  Test getItemCount method
     */
    private static void GetItemCountLongTermStorageTest() {
        storage = new LongTermStorage();
        GetItemCountTest();
        int count = 1;
        LongTermStorage longTermStorage = (LongTermStorage)storage;

        longTermStorage.resetInventory();

        //LongTermStorage GetItemCountTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetItemCountTest"),
                0,
                longTermStorage.getItemCount(item_helmet1.getType()));
    }

    /**
     *  Test getAvailableCapacity method
     */
    private static void GetAvailableCapacityLongTermStorageTest() {
        storage = new LongTermStorage();
        GetAvailableCapacityTest();
        int count = 1;

        storage.addItem(item_football, 1);
        LongTermStorage longTermStorage = (LongTermStorage) storage;
        longTermStorage.resetInventory();

        //LongTermStorage GetAvailableCapacityTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetAvailableCapacityTest"),
                longTermStorage.getCapacity(),
                longTermStorage.getAvailableCapacity());
    }

    /**
     *  Test getInventory method
     */
    private static void GetInventoryLongTermStorageTest() {
        storage = new LongTermStorage();
        GetInventoryTest();
        int count = 1;

        LongTermStorage longTermStorage1 = (LongTermStorage)storage;
        longTermStorage1.resetInventory();

        //LongTermStorage GetInventoryTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetInventoryTest"),
                0,
                storage.getItemCount(item_helmet1.getType()));
    }

    /**
     *  Test resetInventory method
     */
    private static void ResetInventoryTest() {
        int count = 1;

        LongTermStorage currentLongTermStorage = new LongTermStorage();
        currentLongTermStorage.resetInventory();

        //LongTermStorage ResetInventoryTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "ResetInventoryTest"),
                currentLongTermStorage.getCapacity(),
                currentLongTermStorage.getAvailableCapacity());

        //LongTermStorage ResetInventoryTest Test 2
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "ResetInventoryTest"),
                currentLongTermStorage.getCapacity(),
                currentLongTermStorage.getAvailableCapacity());

        currentLongTermStorage.addItem(item_helmet1, 3);
        currentLongTermStorage.addItem(item_football, 5);
        currentLongTermStorage.resetInventory();

        //LongTermStorage ResetInventoryTest Test 3
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "ResetInventoryTest"),
                currentLongTermStorage.getCapacity(),
                currentLongTermStorage.getAvailableCapacity());
    }

    /**
     *  Test All LongTermStorage methods
     */
    public static void TestAllLongTermStorage(){
        PrepareTestLongTermStorage();
        AddItemTest();
        ResetInventoryTest();
        GetAvailableCapacityLongTermStorageTest();
        GetCapacityLongTermStorageTest();
        GetInventoryLongTermStorageTest();
        GetItemCountLongTermStorageTest();
    }
}
