import org.junit.Assert;
import org.junit.Test;

public class LockerTestGetCapacity extends LockerMethodTest {
    @Test
    public void testAddItemToLocker() {
        Assert.assertEquals("Test 1 - should return 11", 11,
                this.locker.getCapacity());

        this.locker.addItem(this.item_banana, 1);

        Assert.assertEquals("Test 1 - should return 11", 11,
                this.locker.getCapacity());

        this.locker.addItem(this.item_whale, 1);

        Assert.assertEquals("Test 1 - should return 11", 11,
                this.locker.getCapacity());

        this.locker.addItem(this.item_football, 2);

        Assert.assertEquals("Test 1 - should return 11", 11,
                this.locker.getCapacity());
    }
}
