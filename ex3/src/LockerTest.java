import oop.ex3.spaceship.ItemFactory;
import org.junit.Assert;
import java.util.HashMap;

public class LockerTest extends StorageTest {

    /**
     * Reset locker
     */
    private static void resetLocker(int _capacity, boolean resetLongTerm) {
        if (resetLongTerm) {
            longTermStorage = new LongTermStorage();
        }
        storage = new Locker(longTermStorage, _capacity, ItemFactory.getConstraintPairs());
    }

    /**
     * Prepare locker tests
     */
    private static void PrepareTestLocker() {
        PrepareTest();
        resetLocker(10, true);
        currentClass = "Locker";
    }

    /**
     * Test addItem methods
     */
    private static void AddItemTest() {
        PrepareTestLocker();

        int count = 1;

        resetLocker(10, true);
        //Locker AddItemTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "AddItemTest"),
                0,
                storage.addItem(item_football, 1));
        count++;

        resetLocker(10, false);

        //Locker AddItemTest Test 2
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "AddItemTest"),
                -1,
                storage.addItem(item_football, 3));
        count++;

        storage.addItem(item_football, 1);

        //Locker AddItemTest Test 3
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "AddItemTest"),
                -2,
                storage.addItem(item_baseballBat, 1));
        count++;

        resetLocker(9, false);

        //Locker AddItemTest Test 4
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "AddItemTest"),
                -1,
                storage.addItem(item_sporesEngine, 1));
        count++;

        //Locker AddItemTest Test 5
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "AddItemTest"),
                0,
                storage.addItem(item_helmet1, 1));
    }

    /**
     * Test getCapacity methods
     */
    private static void GetCapacityLockerTest() {
        GetCapacityTest();
    }

    /**
     * Test getItemCount method
     */
    private static void GetItemCountLockerTest() {
        GetItemCountTest();
        int count = 1;

        Locker locker = new Locker(new LongTermStorage(), 10, ItemFactory.getConstraintPairs());

        locker.addItem(item_helmet1,2);
        locker.removeItem(item_helmet1, 1);

        //Locker GetItemCountTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetItemCountTest"),
                1,
                locker.getItemCount(item_helmet1.getType()));
        count++;

        locker.removeItem(item_helmet1, 2);

        //Locker GetItemCountTest Test 2
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetItemCountTest"),
                1,
                locker.getItemCount(item_helmet1.getType()));
        count++;

        locker.removeItem(item_helmet1, 1);

        //Locker GetItemCountTest Test 3
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetItemCountTest") ,
                0,
                locker.getItemCount(item_helmet1.getType()));
    }

    /**
     * Test removeItem method
     */
    private static void RemoveItemTest() {
        int count = 1;

        Locker locker = new Locker(new LongTermStorage(), 10, ItemFactory.getConstraintPairs());

        //Locker RemoveItemTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "RemoveItemTest"),
                -1,
                locker.removeItem(item_football, 1));
        count++;

        locker.addItem(item_helmet1, 2);
        //Locker RemoveItemTest Test 2
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "RemoveItemTest") ,
                0,
                locker.removeItem(item_helmet1, 1));
        count++;

        //Locker RemoveItemTest Test 3
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "RemoveItemTest"),
                -1,
                locker.removeItem(item_helmet1, 2));
    }

    /**
     * Test getInventory method
     */
    private static void GetInventoryLockerTest() {
        resetLocker(10,true);
        GetInventoryTest();

        Locker locker = (Locker)storage;
        HashMap<String, Integer> returnValue = new HashMap<>();
        int count = 0;

        locker.removeItem(item_helmet1, 1);
        returnValue.put(item_helmet1.getType(), 1);

        //Locker GetInventoryTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetInventoryTest"),
                returnValue,
                locker.getInventory());
    }

    /**
     * Test getAvailableCapacity method
     */
    private static void GetAvailableCapacityLockerTest() {
        resetLocker(10, true);
        GetAvailableCapacityTest();
        int count = 1;

        //Locker GetAvailableCapacityTest Test 1
        resetLocker(10, true);
        Locker locker = (Locker)storage;
        locker.addItem(item_baseballBat, 1);
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetAvailableCapacityTest"),
                10 - item_baseballBat.getVolume(),
                locker.getAvailableCapacity());
        count++;

        //Locker GetAvailableCapacityTest Test 2
        locker.removeItem(item_baseballBat, 1);
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetAvailableCapacityTest"),
                10,
                locker.getAvailableCapacity());
        count++;

        //Locker GetAvailableCapacityTest Test 3
        locker.addItem(item_baseballBat, 2);
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetAvailableCapacityTest"),
                10 - item_baseballBat.getVolume() * 2,
                locker.getAvailableCapacity());
        count++;

        //Locker GetAvailableCapacityTest Test 4
        locker.removeItem(item_baseballBat, 1);
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetAvailableCapacityTest"),
                10 - item_baseballBat.getVolume(),
                locker.getAvailableCapacity());
    }

    /**
     * Test all locker
     */
    public static void TestAllLocker(){
        PrepareTestLocker();
        AddItemTest();
        GetCapacityLockerTest();
        GetItemCountLockerTest();
        RemoveItemTest();
        GetInventoryLockerTest();
        GetAvailableCapacityLockerTest();
    }
}
