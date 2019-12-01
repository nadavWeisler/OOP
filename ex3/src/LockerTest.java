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
            this.longTermStorage = new LongTermStorage(capacity);
        }
        this.storage = new Locker(this.longTermStorage, _capacity, ItemFactory.getConstraintPairs());
    }

    @Before
    @Override
    public void PrepareTest() {
        super.PrepareTest();
        resetLocker(10, true);
    }

    @Test
    @Override
    public void AddItemTest() {
        super.AddItemTest();

        Assert.assertEquals("Test 2 - Should return 1", 1,
                this.storage.addItem(this.item_football, 1));

        resetLocker(10, false);

        Assert.assertEquals("Test 3 - Should return 1", 1,
                this.storage.addItem(item_football, 3));

        Assert.assertEquals("Test 4 should return -2", -2,
                this.storage.addItem(this.baseballBat, 1));

        resetLocker(9, false);

        Assert.assertEquals("Test 5 should return 1", 1,
                this.storage.addItem(this.sporesEngine, 1));

        Assert.assertEquals("Test 6 should return 0", 0,
                this.storage.addItem(this.item_helmet1, 1));

        Assert.assertEquals("Test 8 should return -1", -1,
                this.storage.addItem(this.item_football, 1));
    }

    @Test
    public void GetCapacityTest() {
        super.GetCapacityTest();
    }

    @Test
    public void GetItemCountTest() {
        Assert.assertEquals("Test 1 - should return 0", 0,
                this.storage.getItemCount(this.item_football.getType()));

        this.storage.addItem(this.item_helmet1, 1);

        Assert.assertEquals("Test 1 - should return 0", 0,
                this.storage.getItemCount(this.item_football.getType()));

        Assert.assertEquals("Test 1 - should return 1", 1,
                this.storage.getItemCount(this.item_helmet1.getType()));

        this.storage.addItem(this.item_helmet1, 2);

        Assert.assertEquals("Test 1 - should return 2", 3,
                this.storage.getItemCount(this.item_helmet1.getType()));

        Locker locker = (Locker)this.storage;

        locker.removeItem(this.item_helmet1, 2);

        Assert.assertEquals("Test 1 - should return 1", 1,
                locker.getItemCount(this.item_helmet1.getType()));

        locker.removeItem(this.item_helmet1, 2);

        Assert.assertEquals("Test 1 - should return 1", 1,
                locker.getItemCount(this.item_helmet1.getType()));

        locker.removeItem(this.item_helmet1, 1);

        Assert.assertEquals("Test 1 - should return 0", 0,
                locker.getItemCount(this.item_helmet1.getType()));
    }

    @Test
    public void RemoveItemTest() {
        Locker locker = (Locker)this.storage;

        Assert.assertEquals("Test 1 - should return -1", -1,
                locker.removeItem(this.item_football, 1));

        locker.addItem(item_helmet1, 2);

        Assert.assertEquals("Test 2 - should return 0", 0,
                locker.removeItem(this.item_helmet1, 1));

        Assert.assertEquals("Test 3 - should return -1", -1,
                locker.removeItem(this.item_helmet1, 2));

        locker.addItem(item_helmet1, 1);

        Assert.assertEquals("Test 4 - should return 0", 0,
                locker.removeItem(this.item_helmet1, 2));
    }

    @Test
    public void GetInventoryTest() {
        Assert.assertEquals("Test 1 - should return empty map", new HashMap<String, Integer>(),
                this.storage.getInventory());

        this.storage.addItem(this.item_helmet1, 1);

        HashMap<String, Integer> returnValue = new HashMap<String, Integer>();
        returnValue.put(this.item_helmet1.getType(), 1);
        Assert.assertEquals("Test 2 - should return 1 helmet size 1", returnValue,
                this.storage.getInventory());

        this.storage.addItem(this.item_helmet1, 1);
        returnValue.replace(this.item_helmet1.getType(), 2);
        Assert.assertEquals("Test 3 - should return 2 helmet size 1", returnValue,
                this.storage.getInventory());

        Locker locker = (Locker)this.storage;

        locker.removeItem(this.item_helmet1, 1);
        returnValue.replace(this.item_helmet1.getType(), 1);
        Assert.assertEquals("Test 3 - should return 1 helmet size 1", returnValue,
                locker.getInventory());
    }

    @Test
    public void GetAvailableCapacityTest(){

    }
}
