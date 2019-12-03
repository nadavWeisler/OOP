import oop.ex3.spaceship.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class LongTermStorageTest extends StorageTest {
    @Override
    public void PrepareTest() {
        super.PrepareTest();
        this.currentClass = "LongTermStorage";
    }

    @Test
    public void AddItemTest() {
        int count = 1;
        this.storage = new LongTermStorage();

        //LongTermStorage AddItemTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "AddItemTest"),
                0,
                this.storage.addItem(this.item_football, 1));

        //LongTermStorage AddItemTest Test 2
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "AddItemTest"),
                0,
                this.storage.addItem(this.item_football, 50));

        this.storage = new LongTermStorage();

        //LongTermStorage AddItemTest Test 3
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "AddItemTest"),
                -1,
                this.storage.addItem(this.baseballBat, 1000));

        //LongTermStorage AddItemTest Test 4
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "AddItemTest"),
                -1,
                this.storage.addItem(this.baseballBat, -1));
    }

    @Test
    public void GetCapacityTest() {
        this.storage = new LongTermStorage();
        super.GetCapacityTest();
        int count = 1;

        this.storage.addItem(this.item_football, 1);
        LongTermStorage longTermStorage = (LongTermStorage) this.storage;
        longTermStorage.resetInventory();

        //LongTermStorage GetCapacityTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetCapacityTest"),
                longTermStorage.capacity,
                longTermStorage.getCapacity());
    }

    @Test
    public void GetItemCountTest() {
        this.storage = new LongTermStorage();
        super.GetItemCountTest();
        int count = 1;
        LongTermStorage longTermStorage = (LongTermStorage) this.storage;

        longTermStorage.resetInventory();

        //LongTermStorage GetItemCountTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetItemCountTest"),
                0,
                longTermStorage.getItemCount(this.item_helmet1.getType()));
    }

    @Test
    public void GetAvailableCapacityTest() {
        this.storage = new LongTermStorage();
        super.GetAvailableCapacityTest();
        int count = 1;

        this.storage.addItem(this.item_football, 1);
        LongTermStorage longTermStorage = (LongTermStorage) this.storage;
        longTermStorage.resetInventory();

        //LongTermStorage GetAvailableCapacityTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetAvailableCapacityTest"),
                longTermStorage.getCapacity(),
                longTermStorage.getAvailableCapacity());
    }

    @Test
    public void GetInventoryTest() {
        this.storage = new LongTermStorage();
        super.GetInventoryTest();
        int count = 1;

        LongTermStorage longTermStorage1 = (LongTermStorage) this.storage;
        longTermStorage1.resetInventory();

        //LongTermStorage GetInventoryTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetInventoryTest"),
                0,
                this.storage.getItemCount(this.item_helmet1.getType()));
    }

    @Test
    public void ResetInventoryTest() {
        int count = 1;
        this.storage = new LongTermStorage();
        this.storage.addItem(this.item_helmet1, 3);

        LongTermStorage longTermStorage = (LongTermStorage) this.storage;
        longTermStorage.resetInventory();

        //LongTermStorage ResetInventoryTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "ResetInventoryTest"),
                longTermStorage.getCapacity(),
                longTermStorage.getAvailableCapacity());

        //LongTermStorage ResetInventoryTest Test 2
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "ResetInventoryTest"),
                longTermStorage.getCapacity(),
                longTermStorage.getAvailableCapacity());

        this.longTermStorage.addItem(this.item_helmet1, 3);
        this.longTermStorage.addItem(this.item_football, 5);
        this.longTermStorage.resetInventory();

        //LongTermStorage ResetInventoryTest Test 3
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "ResetInventoryTest"),
                longTermStorage.getCapacity(),
                longTermStorage.getAvailableCapacity());
    }
}
