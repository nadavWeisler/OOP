import oop.ex3.spaceship.ItemFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;

public class SpaceshipTest {
    /**
     * Spaceship object
     */
    private static Spaceship spaceship;

    /**
     * Current class for tests
     */
    private static String currentClass = "Spaceship";

    /**
     * Reset class spaceship
     */
    private static void resetSpaceship() {
        spaceship = new Spaceship("Millennium Falcon",
                new int[]{1, 2, 3},
                5);
    }

    /**
     *  Prepare tests
     */
    private static void PrepareTest() {
        resetSpaceship();
    }

    /**
     *  Test getLongTermStorage method
     */
    private static void GetLongTermStorageTest() {
        int count = 1;
        LongTermStorage returnValue = new LongTermStorage();

        //Spaceship GetLongTermStorageTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "GetLongTermStorageTest"),
                returnValue.getInventory(),
                spaceship.getLongTermStorage().getInventory());
    }

    /**
     * Test createLocker method
     */
    private static void createLockerTest() {
        resetSpaceship();
        int count = 1;

        Locker[] lockers = new Locker[] {null, null, null, null, null};
        Locker[] getLockerResult = spaceship.getLockers();

        //Spaceship createLockerTest Test 1
        Assert.assertTrue(Utils.GenerateTestString(count, currentClass, "getLockers"),
                CompareLockerArrays(lockers, getLockerResult));
    }

    /**
     *  Test getCrewIDs method
     */
    private static void getCrewIDsTest() {
        resetSpaceship();
        int count = 1;

        //Spaceship getCrewIDsTest Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "getCrewIDsTest"),
                Arrays.toString(new int[]{1, 2, 3}),
                Arrays.toString(spaceship.getCrewIDs()));
    }

    /**
     *  Test getLockers method
     */
    private static void getLockers() {
        resetSpaceship();
        int count = 1;

        //Spaceship getLockers Test 1
        Assert.assertEquals(Utils.GenerateTestString(count, currentClass, "getLockers"),
                Arrays.toString(new Locker[]{null, null, null, null, null}),
                Arrays.toString(spaceship.getLockers()));
        count++;

        Locker[] lockers = new Locker[]{
                new Locker(spaceship.getLongTermStorage(), 5, null),
                null, null, null, null};
        spaceship.createLocker(1, 5);
        Locker[] getLockerResult = spaceship.getLockers();

        //Spaceship getLockers Test 2
        Assert.assertTrue(Utils.GenerateTestString(count, currentClass, "getLockers"),
                CompareLockerArrays(lockers, getLockerResult));

        lockers = new Locker[]{
                new Locker(spaceship.getLongTermStorage(), 5, null),
                new Locker(spaceship.getLongTermStorage(), 10, null),
                new Locker(spaceship.getLongTermStorage(), 28, null),
                null, null};

        spaceship.createLocker(2, 10);
        spaceship.createLocker(2, 28);
        getLockerResult = spaceship.getLockers();

        //Spaceship getLockers Test 3
        Assert.assertTrue(Utils.GenerateTestString(count, currentClass, "getLockers"),
                CompareLockerArrays(lockers, getLockerResult));

    }

    /**
     * Compare lockers array
     * @param lockers Lockers array
     * @param getLockerResult Locker array
     * @return Boolean
     */
    private static boolean CompareLockerArrays(Locker[] lockers, Locker[] getLockerResult) {
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

    /**
     *  Test all Spaceship methods
     */
    public static void TestAllSpaceship() {
        PrepareTest();
        GetLongTermStorageTest();
        getCrewIDsTest();
        getLockers();
        createLockerTest();
    }
}

