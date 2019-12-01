import oop.ex3.spaceship.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class LongTermStorageTest extends StorageTest {
    @Override
    public void PrepareTest() {

    }

    @Test
    public void AddItemTest() {

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

        Locker locker = (Locker) this.storage;

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
    public void GetAvailableCapacityTest() {

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

        Locker locker = (Locker) this.storage;

        locker.removeItem(this.item_helmet1, 1);
        returnValue.replace(this.item_helmet1.getType(), 1);
        Assert.assertEquals("Test 3 - should return 1 helmet size 1", returnValue,
                locker.getInventory());
    }

    @Test
    public void ResetInventoryTest(){

    }
}
