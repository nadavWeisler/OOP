public class OpenHashSet extends SimpleHashSet {
    private LinkedListWrapper[] hashMap;

    /**
     * Constructs a new, empty table with the specified load factors,
     * and the default initial capacity (16).
     */
    OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super();
        this.hashMap = new LinkedListWrapper[INITIAL_CAPACITY];
    }

    /**
     * A default constructor.
     * Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    OpenHashSet() {
        super();
        this.hashMap = new LinkedListWrapper[INITIAL_CAPACITY];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * Duplicate values should be ignored.
     * The new table has the default values of initial capacity (16),
     * upper load factor (0.75), and lower load factor (0.25).
     *
     * @param data Values to add to the set.
     */
    OpenHashSet(String[] data) {

    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {
        return false;
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True if searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {
        int stringHash = searchVal.hashCode();
        if(this.hashMap[this.clamp(stringHash)].IsEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True if toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        return false;
    }

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * Capacity in class SimpleHashSet
     * @return The current capacity (number of cells) of the table
     */
    @Override
    int capacity() {
        return 0;
    }

    @Override
    int clamp(int index) {
        return index & hashMap.length - 1;
    }
}
