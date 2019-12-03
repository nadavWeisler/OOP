import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.Assert;
import org.junit.Before;

import java.util.HashMap;

public abstract class StorageTest {

    /**
     *
     */
    protected Storage storage;
    protected LongTermStorage longTermStorage;
    /**
     *
     */
    protected Item item_football;
    /**
     *
     */
    protected Item item_helmet1;
    /**
     *
     */
    protected Item item_helmet2;
    /**
     *
     */
    protected Item sporesEngine;
    /**
     *
     */
    protected Item baseballBat;

    protected Item[] legalItems;

    protected String currentClass;

    @Before
    public void PrepareTest() {
        this.currentClass = "Storage";
        this.item_football = ItemFactory.createSingleItem("football");
        this.item_helmet1 = ItemFactory.createSingleItem("helmet, size 1");
        this.item_helmet2 = ItemFactory.createSingleItem("helmet, size 3");
        this.sporesEngine = ItemFactory.createSingleItem("spores engine");
        this.baseballBat = ItemFactory.createSingleItem("baseball bat");
        this.legalItems = ItemFactory.createAllLegalItems();
        this.longTermStorage = new LongTermStorage();
    }

    protected void GetItemCountTest() {
        int count = 1;

        //Storage GetItemCountTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetItemCountTest"),
                0,
                this.storage.getItemCount(this.item_football.getType()));
        count++;

        //Storage GetItemCountTest Test 2
        this.storage.addItem(this.item_helmet1, 1);
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetItemCountTest"),
                0,
                this.storage.getItemCount(this.item_football.getType()));
        count++;

        //Storage GetItemCountTest Test 3
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetItemCountTest"),
                1,
                this.storage.getItemCount(this.item_helmet1.getType()));
        count++;

        this.storage.addItem(this.item_helmet1, 2);

        //Storage GetItemCountTest Test 4
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetItemCountTest"),
                3,
                this.storage.getItemCount(this.item_helmet1.getType()));
    }

    protected void GetInventoryTest() {
        int count = 1;

        //Storage GetInventoryTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetInventoryTest"),
                new HashMap<String, Integer>(),
                this.storage.getInventory());
        count++;

        this.storage.addItem(this.item_helmet1, 1);
        HashMap<String, Integer> returnValue = new HashMap<String, Integer>();
        returnValue.put(this.item_helmet1.getType(), 1);

        //Storage GetInventoryTest Test 2
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetInventoryTest"),
                returnValue,
                this.storage.getInventory());
        count++;

        this.storage.addItem(this.item_helmet1, 1);
        returnValue.put(this.item_helmet1.getType(), 2);

        //Storage GetInventoryTest Test 3
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetInventoryTest"),
                returnValue,
                this.storage.getInventory());
    }

    protected void GetCapacityTest() {
        int count = 1;
        int current_capacity = this.storage.getCapacity();

        //Storage GetCapacityTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetCapacityTest"),
                current_capacity,
                this.storage.getCapacity());
        count++;

        this.storage.addItem(this.item_helmet1, 4);

        //Storage GetCapacityTest Test 2
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetCapacityTest"),
                current_capacity,
                this.storage.getCapacity());
        count++;

        this.storage.addItem(this.sporesEngine, 1);

        //Storage GetCapacityTest Test 3
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetCapacityTest"),
                current_capacity,
                this.storage.getCapacity());
        count++;

        this.storage.addItem(this.item_football, 189);

        //Storage GetCapacityTest Test 4
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetCapacityTest"),
                current_capacity,
                this.storage.getCapacity());

    }

    protected void GetAvailableCapacityTest() {
        int count = 1;
        int current_capacity = this.storage.getCapacity();

        //Storage GetAvailableCapacityTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetAvailableCapacityTest"),
                current_capacity,
                this.storage.getAvailableCapacity());
        count++;

        //Storage GetAvailableCapacityTest Test 2
        this.storage.addItem(this.item_helmet1, 1);
        current_capacity -= this.item_helmet1.getVolume();
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetAvailableCapacityTest"),
                current_capacity,
                this.storage.getAvailableCapacity());

    }
}
