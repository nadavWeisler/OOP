import org.junit.Assert;
import org.junit.Test;

public class LockerTestGetItemCount extends LockerMethodTest {
    @Test
    public void testAddItemToLocker() {
        Assert.assertEquals("Test 1 - should return 0", 0,
                this.locker.getItemCount(this.item_football.getType()));

        this.locker.addItem(this.item_banana, 1);

        Assert.assertEquals("Test 1 - should return 0", 0,
                this.locker.getItemCount(this.item_football.getType()));

        Assert.assertEquals("Test 1 - should return 1", 1,
                this.locker.getItemCount(this.item_banana.getType()));

        this.locker.addItem(this.item_banana, 2);

        Assert.assertEquals("Test 1 - should return 2", 3,
                this.locker.getItemCount(this.item_banana.getType()));

        this.locker.removeItem(this.item_banana, 2);

        Assert.assertEquals("Test 1 - should return 1", 1,
                this.locker.getItemCount(this.item_banana.getType()));

        this.locker.removeItem(this.item_banana, 2);

        Assert.assertEquals("Test 1 - should return 1", 1,
                this.locker.getItemCount(this.item_banana.getType()));

        this.locker.removeItem(this.item_banana, 1);

        Assert.assertEquals("Test 1 - should return 0", 0,
                this.locker.getItemCount(this.item_banana.getType()));
    }
}
