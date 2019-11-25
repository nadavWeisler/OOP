import oop.ex3.spaceship.Item;
import org.junit.Before;

public abstract class LockerMethodTest {
    /**
     *
     */
    protected Locker locker;
    /**
     *
     */
    protected LongTermStorage longTermStorage;
    /**
     *
     */
    protected Item item_football;
    /**
     *
     */
    protected Item item_baseball;
    /**
     *
     */
    protected Item item_bag;
    /**
     *
     */
    protected Item item_banana;
    /**
     *
     */
    protected Item item_whale;
    /**
     *
     */
    protected Item item_chair;
    /**
     *
     */
    protected Item[][] itemsConstraints;

    @Before
    public void createLockerObject() {
        this.item_football = new Item("football", 4);
        this.item_baseball = new Item("baseball", 1);
        this.item_bag = new Item("bag", 5);
        this.item_banana = new Item("banana", 2);
        this.item_chair = new Item("chair", 50);
        this.item_whale = new Item("Whale", 999);
        this.itemsConstraints = new Item[][]{
                {this.item_football, this.item_baseball},
                {this.item_bag, this.item_whale},
                {this.item_banana, this.item_baseball},
        };
        this.longTermStorage = new LongTermStorage();
        this.locker = new Locker(this.longTermStorage, 10, itemsConstraints);
    }
}
