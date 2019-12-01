public class Spaceship {
    private LongTermStorage longTermStorage;
    private String spaceshipName;
    private final int[] crews;
    private Locker[] lockers;
    private int[] lockerOwners;

    /**
     * Spaceship constructor
     *
     * @param name         Spaceship name
     * @param crewIDs      Crew IDs array
     * @param numOfLockers Number of lockers in spaceship
     */
    public Spaceship(String name, int[] crewIDs, int numOfLockers) {
        this.longTermStorage = new LongTermStorage();
        this.spaceshipName = name;
        this.crews = crewIDs;
        this.lockers = new Locker[numOfLockers];
        this.lockerOwners = new int[numOfLockers];

    }

    /**
     * Get spaceship long term storage
     *
     * @return LongTermStorage object
     */
    public LongTermStorage getLongTermStorage() {
        return this.longTermStorage;
    }

    /**
     * Return true if int array contains int
     *
     * @param num      int
     * @param numArray int array
     * @return boolean
     */
    private boolean IntInIntArray(int num, int[] numArray) {
        for (int n :
                numArray) {
            if (n == num) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the first empty locker, if all full return -1
     *
     * @return int
     */
    private int getEmptyLockerSlot() {
        for (int i = 0; i < this.lockers.length; i++) {
            if (this.lockers[i] == null) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Create new locker
     *
     * @param crewID   Locker owner ID
     * @param capacity New Locker capacity
     * @return int
     */
    public int createLocker(int crewID, int capacity) {
        if (this.IntInIntArray(crewID, this.crews)) {
            int emptySlot = this.getEmptyLockerSlot();
            if (emptySlot == -1) {
                return -3;
            } else {
                this.lockers[emptySlot] = new Locker(this.longTermStorage, capacity, null);
                this.lockerOwners[emptySlot] = crewID;
                return 0;
            }
        } else {
            return -1;
        }
    }

    /**
     * Get crew IDs array
     * @return int array
     */
    public int[] getCrewIDs() {
        return this.crews;
    }

    /**
     * Get spaceship lockers array
     * @return Locker array
     */
    public Locker[] getLockers() {
        return this.lockers;
    }
}
