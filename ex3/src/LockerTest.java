import oop.ex3.spaceship.ItemFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class LockerTest extends StorageTest {
    /**
     *
     */
    private void resetLocker(int _capacity, boolean resetLongTerm) {
        if (resetLongTerm) {
            this.longTermStorage = new LongTermStorage();
        }
        this.storage = new Locker(this.longTermStorage, _capacity, ItemFactory.getConstraintPairs());
    }

    @Before
    @Override
    public void PrepareTest() {
        super.PrepareTest();
        resetLocker(10, true);
        this.currentClass = "Locker";
    }

    @Test
    public void AddItemTest() {
        int count = 1;

        //Locker AddItemTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "AddItemTest"),
                1,
                this.storage.addItem(this.item_football, 1));
        count++;

        resetLocker(10, false);

        //Locker AddItemTest Test 2
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "AddItemTest"),
                1,
                this.storage.addItem(item_football, 3));
        count++;

        //Locker AddItemTest Test 3
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "AddItemTest"),
                -2,
                this.storage.addItem(this.baseballBat, 1));
        count++;

        resetLocker(9, false);

        //Locker AddItemTest Test 4
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "AddItemTest"),
                1,
                this.storage.addItem(this.sporesEngine, 1));
        count++;

        //Locker AddItemTest Test 5
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "AddItemTest"),
                0,
                this.storage.addItem(this.item_helmet1, 1));
        count++;

        //Locker AddItemTest Test 6
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "AddItemTest"),
                -1,
                this.storage.addItem(this.item_football, 1));
    }

    @Test
    public void GetCapacityTest() {
        super.GetCapacityTest();
    }

    @Test
    public void GetItemCountTest() {
        super.GetItemCountTest();
        int count = 1;

        Locker locker = (Locker) this.storage;
        locker.removeItem(this.item_helmet1, 2);

        //Locker GetItemCountTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetItemCountTest"),
                1,
                locker.getItemCount(this.item_helmet1.getType()));
        count++;

        locker.removeItem(this.item_helmet1, 2);

        //Locker GetItemCountTest Test 2
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetItemCountTest"),
                1,
                locker.getItemCount(this.item_helmet1.getType()));
        count++;

        locker.removeItem(this.item_helmet1, 1);

        //Locker GetItemCountTest Test 3
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetItemCountTest") ,
                0,
                locker.getItemCount(this.item_helmet1.getType()));
    }

    @Test
    public void RemoveItemTest() {
        int count = 1;
        Locker locker = (Locker) this.storage;

        //Locker RemoveItemTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "RemoveItemTest"),
                -1,
                locker.removeItem(this.item_football, 1));
        count++;

        locker.addItem(item_helmet1, 2);

        //Locker RemoveItemTest Test 2
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "RemoveItemTest") ,
                0,
                locker.removeItem(this.item_helmet1, 1));
        count++;

        //Locker RemoveItemTest Test 3
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "RemoveItemTest"),
                -1,
                locker.removeItem(this.item_helmet1, 2));
        count++;

        locker.addItem(item_helmet1, 1);

        //Locker RemoveItemTest Test 4
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "RemoveItemTest") ,
                0,
                locker.removeItem(this.item_helmet1, 2));
    }

    @Test
    public void GetInventoryTest() {
        super.GetInventoryTest();

        Locker locker = (Locker) this.storage;
        HashMap<String, Integer> returnValue = new HashMap<String, Integer>();
        int count = 0;

        locker.removeItem(this.item_helmet1, 1);
        returnValue.put(this.item_helmet1.getType(), 1);

        //Locker GetInventoryTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetInventoryTest"),
                returnValue,
                locker.getInventory());
    }

    @Test
    public void GetAvailableCapacityTest() {
        this.resetLocker(10, true);
        super.GetAvailableCapacityTest();
        int count = 1;

        //Locker GetAvailableCapacityTest Test 1
        this.resetLocker(10, true);
        Locker locker = (Locker)this.storage;
        locker.addItem(this.baseballBat, 1);
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetAvailableCapacityTest"),
                10 - this.baseballBat.getVolume(),
                locker.getAvailableCapacity());
        count++;

        //Locker GetAvailableCapacityTest Test 2
        locker.removeItem(this.baseballBat, 1);
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetAvailableCapacityTest"),
                10,
                locker.getAvailableCapacity());
        count++;

        //Locker GetAvailableCapacityTest Test 3
        locker.addItem(this.baseballBat, 2);
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetAvailableCapacityTest"),
                10 - this.baseballBat.getVolume() * 2,
                locker.getAvailableCapacity());
        count++;

        //Locker GetAvailableCapacityTest Test 4
        locker.removeItem(this.baseballBat, 1);
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetAvailableCapacityTest"),
                10 - this.baseballBat.getVolume(),
                locker.getAvailableCapacity());
        count++;

        //Locker GetAvailableCapacityTest Test 5
        locker.addItem(this.item_helmet1, 2);
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetAvailableCapacityTest"),
                10 - this.baseballBat.getVolume(),
                locker.getAvailableCapacity());
    }
}
