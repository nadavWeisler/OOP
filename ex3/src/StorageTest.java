import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.Assert;
import org.junit.Before;

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

    @Before
    public void PrepareTest() {
        this.item_football = ItemFactory.createSingleItem("football");
        this.item_helmet1 = ItemFactory.createSingleItem("helmet, size 1");
        this.item_helmet2 = ItemFactory.createSingleItem("helmet, size 3");
        this.sporesEngine = ItemFactory.createSingleItem("spores engine");
        this.baseballBat = ItemFactory.createSingleItem("baseball bat");
        this.legalItems = ItemFactory.createAllLegalItems();
        this.longTermStorage = new LongTermStorage();
    }

    protected void AddItemTest() {
        Assert.assertEquals("Test 1 - Should Return 0",
                0,
                this.storage.addItem(item_football, 1));
    }

    protected void GetCapacityTest() {
        int current_capacity = this.storage.getCapacity();

        Assert.assertEquals("Test 1 - should return " + current_capacity,
                current_capacity,
                this.storage.getCapacity());

        this.storage.addItem(this.item_helmet1, 4);

        Assert.assertEquals("Test 1 - should return " + current_capacity,
                current_capacity,
                this.storage.getCapacity());

        this.storage.addItem(this.sporesEngine, 1);

        Assert.assertEquals("Test 1 - should return " + current_capacity,
                current_capacity,
                this.storage.getCapacity());

        this.storage.addItem(this.item_football, 189);

        Assert.assertEquals("Test 1 - should return " + current_capacity,
                current_capacity,
                this.storage.getCapacity());

    }

    protected void GetAvailableCapacityTest(){
        int current_capacity = this.storage.getCapacity();

        Assert.assertEquals("Test 1 - should return " + current_capacity,
                current_capacity,
                this.storage.getAvailableCapacity());

        this.storage.addItem(this.item_helmet1, 1);
        current_capacity -= this.item_helmet1.getVolume();

        Assert.assertEquals("Test 1 - should return " + current_capacity,
                current_capacity,
                this.storage.getAvailableCapacity());

    }
}
