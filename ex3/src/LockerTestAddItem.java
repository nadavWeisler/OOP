import org.junit.*;


public class LockerTestAddItem extends LockerMethodTest {
    @Test
    public void testAddItemToLocker() {
        Assert.assertEquals("Test 1 - Should Return 0", 0,
                this.locker.addItem(item_football, 1));

        Assert.assertEquals("Test 2 - Should return 1", 1,
                this.locker.addItem(item_football, 1));

        this.locker = new Locker(this.longTermStorage, 10, itemsConstraints);

        Assert.assertEquals("Test 3 - Should return 1", 1,
                this.locker.addItem(item_football, 3));

        Assert.assertEquals("Test 4 should return -2", -2,
                this.locker.addItem(this.item_baseball,1));

        this.locker = new Locker(this.longTermStorage, 11, itemsConstraints);

        Assert.assertEquals("Test 5 should return 1", 1,
                this.locker.addItem(this.item_chair,1));

        Assert.assertEquals("Test 6 should return 0", 0,
                this.locker.addItem(this.item_bag,1));

        Assert.assertEquals("Test 7 should return 0", 0,
                this.locker.addItem(this.item_banana,2));

        Assert.assertEquals("Test 8 should return -1", -1,
                this.locker.addItem(this.item_football,1));

        this.longTermStorage = new LongTermStorage();
        this.locker = new Locker(this.longTermStorage, 11, itemsConstraints);

        Assert.assertEquals("Test 9 should return 1", 1,
                this.locker.addItem(this.item_whale,1));

        Assert.assertEquals("Test 10 should return -1", -1,
                this.locker.addItem(this.item_banana,10));

        this.longTermStorage = new LongTermStorage();
        this.locker = new Locker(this.longTermStorage, 11, itemsConstraints);

        Assert.assertEquals("Test 9 should return -1", -1,
                this.locker.addItem(this.item_whale,2));
    }

    @Test(expected = NullPointerException.class)
    public void testNullReference() {
        Locker l = null;
        l.addItem(this.item_football, 1);
    }

    @Test(expected = ArithmeticException.class)
    public void testArithmeticException() {
        Locker l = null;
        l.addItem(this.item_football, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsException() {
        Locker l = null;
        l.addItem(this.item_football, 1);
    }
}