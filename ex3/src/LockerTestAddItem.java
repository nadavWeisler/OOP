import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import oop.ex3.spaceship.Item;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class LockerTestAddItem {
    private Locker Locker1;
    private Locker Locker2;
    private Locker Locker3;
    private LongTermStorage longTermStorage1;
    private LongTermStorage longTermStorage2;
    private LongTermStorage longTermStorage3;
    private Item[][] itemsConstraints;

    @BeforeClass
    public void createLockerObject() {
        this.itemsConstraints = new Item[][]{
                {new Item("football", 4), new Item("baseball", 1)},
                {new Item("bag", 1), new Item("food", 1)},
                {new Item("1", 1), new Item("2", 1)},
                {new Item("1", 1), new Item("2", 1)},
                {new Item("1", 1), new Item("2", 1)}
        };
        this.longTermStorage1 = new LongTermStorage();
        this.Locker1 = new Locker(this.longTermStorage1, 1, itemsConstraints);
    }

    @Test
    public void testAddItemToLocker() {

    }

    @Test(expected = NullPointerException.class)
    public void testNullReference() {

    }
}

