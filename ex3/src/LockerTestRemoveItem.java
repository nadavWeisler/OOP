import org.junit.Assert;
import org.junit.Test;

public class LockerTestRemoveItem extends LockerMethodTest{
    @Test
    public void testAddItemToLocker() {
        Assert.assertEquals("Test 1 - should return -1", -1,
                this.locker.removeItem(this.item_football, 1));

        this.locker.addItem(item_banana, 2);

        Assert.assertEquals("Test 2 - should return 0", 0,
                this.locker.removeItem(this.item_banana, 1));

        Assert.assertEquals("Test 3 - should return -1", -1,
                this.locker.removeItem(this.item_banana, 2));

        this.locker.addItem(item_banana, 1);

        Assert.assertEquals("Test 4 - should return 0", 0,
                this.locker.removeItem(this.item_banana, 2));
    }
}
