import oop.ex3.spaceship.ItemFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;

public class SpaceshipTest {
    private Spaceship spaceship;
    private String currentClass = "Spaceship";

    /**
     * Reset class spaceship
     */
    private void resetSpaceship() {
        this.spaceship = new Spaceship("Millennium Falcon",
                new int[]{1, 2, 3},
                5);
    }

    @Before
    public void PrepareTest() {
        resetSpaceship();
    }

    @Test
    public void GetLongTermStorageTest() {
        int count = 1;
        LongTermStorage returnValue = new LongTermStorage();

        //Spaceship GetLongTermStorageTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "GetLongTermStorageTest"),
                returnValue.getInventory(),
                this.spaceship.getLongTermStorage().getInventory());
    }

    @Test
    public void createLockerTest() {
        this.resetSpaceship();
        int count = 1;

        Locker[] lockers = new Locker[] {null, null, null, null, null};
        Locker[] getLockerResult = this.spaceship.getLockers();

        //Spaceship createLockerTest Test 1
        Assert.assertTrue(Utils.GenerateTestString(count, this.currentClass, "getLockers"),
                CompareLockerArrays(lockers, getLockerResult));
    }

    @Test
    public void getCrewIDsTest() {
        this.resetSpaceship();
        int count = 1;

        //Spaceship getCrewIDsTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "getCrewIDsTest"),
                Arrays.toString(new int[]{1, 2, 3}),
                Arrays.toString(this.spaceship.getCrewIDs()));
    }

    @Test
    public void getLockers() {
        this.resetSpaceship();
        int count = 1;

        //Spaceship getLockers Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, this.currentClass, "getLockers"),
                Arrays.toString(new Locker[]{null, null, null, null, null}),
                Arrays.toString(this.spaceship.getLockers()));
        count++;

        Locker[] lockers = new Locker[]{
                new Locker(this.spaceship.getLongTermStorage(), 5, null),
                null, null, null, null};
        this.spaceship.createLocker(1, 5);
        Locker[] getLockerResult = this.spaceship.getLockers();

        //Spaceship getLockers Test 2
        Assert.assertTrue(Utils.GenerateTestString(count, this.currentClass, "getLockers"),
                CompareLockerArrays(lockers, getLockerResult));

        lockers = new Locker[]{
                new Locker(this.spaceship.getLongTermStorage(), 5, null),
                new Locker(this.spaceship.getLongTermStorage(), 10, null),
                new Locker(this.spaceship.getLongTermStorage(), 28, null),
                null, null};

        this.spaceship.createLocker(2, 10);
        this.spaceship.createLocker(2, 28);
        getLockerResult = this.spaceship.getLockers();

        //Spaceship getLockers Test 3
        Assert.assertTrue(Utils.GenerateTestString(count, this.currentClass, "getLockers"),
                CompareLockerArrays(lockers, getLockerResult));

    }

    /**
     * Compare lockers array
     * @param lockers Lockers array
     * @param getLockerResult Locker array
     * @return Boolean
     */
    private boolean CompareLockerArrays(Locker[] lockers, Locker[] getLockerResult) {
        boolean result = true;
        for (int i = 0; i < lockers.length; i++) {
            if(lockers[i] == null){
                if(getLockerResult[i] == null) {
                    continue;
                }
                else{
                    result = false;
                    break;
                }
            }
            if (!lockers[i].inventory.equals(getLockerResult[i].inventory)) {
                result = false;
                break;
            }
        }
        return result;
    }
}

