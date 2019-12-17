public class ClosedHashSet extends SimpleHashSet {

    /**
     * A default constructor
     */
    public ClosedHashSet() {
        super();
        this.hashSet = new StringWrapper[INITIAL_CAPACITY];
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     *
     * @param upperLoadFactor The upper load factor before rehashing
     * @param lowerLoadFactor The lower load factor before rehashing
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        this.hashSet = new StringWrapper[INITIAL_CAPACITY];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one
     *
     * @param data Values to add to the set.
     */
    public ClosedHashSet(String[] data) {
        this();
        if (data == null) {
            return;
        }

        for (String datum : data) {
            if (datum != null) {
                this.add(datum);
            }
        }
    }

    /**
     * Get free index of hash set
     *
     * @param hashVal Hashed string
     * @return index
     */
    private int getFreeIndex(int hashVal) {
        if (this.size() == this.capacity()) {
            return -1;
        }
        int i = 0;

        int clampVal = getClampValue(i, hashVal);
        while (this.hashSet[clampVal] != null &&
                (((StringWrapper) this.hashSet[clampVal]).getString()) != null) {
            i++;
            clampVal = getClampValue(i, hashVal);
        }

        return clampVal;
    }

    /**
     * Resize hash set if needed (By 2 or by 0.5)
     */
    private void resize(double num) {
        StringWrapper[] tempHash = (StringWrapper[]) this.hashSet.clone();
        this.hashSet = new StringWrapper[(int) (tempHash.length * num)];
        for (StringWrapper hash : tempHash) {
            if (hash != null && hash.getString() != null) {
                this.add(hash.getString());
            }
        }

    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {
        if (this.contains(newValue)) {
            return false;
        }
        int i = getFreeIndex(newValue.hashCode());
        if (i == -1) {
            return false;
        }
        this.hashSet[i] = new StringWrapper(newValue);

        if (this.size() > this.upperLoadFactor * this.capacity()) {
            resize(this.UP_RESIZE);
        }

        return true;
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True if searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {
        return this.getIndexOf(searchVal) != -1;
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True if toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        int index = this.getIndexOf(toDelete);
        if (index == -1) {
            return false;
        } else {
            this.hashSet[index] = new StringWrapper();
            if (this.size() > 1 && this.size() < this.capacity() * this.lowerLoadFactor) {
                resize(this.LOW_RESIZE);
            }
            return true;
        }
    }

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        int count = 0;
        for (StringWrapper item :
                (StringWrapper[]) this.hashSet) {
            if (item != null && item.getString() != null) {
                count++;
            }
        }
        return count;
    }

    /**
     * Get index of string in hash
     *
     * @param val String
     * @return Index if exist, -1 else
     */
    public int getIndexOf(String val) {
        int i = 0;
        int clampValue = getClampValue(i, val.hashCode());
        while (this.hashSet[clampValue] != null) {
            if (((StringWrapper) this.hashSet[clampValue]).getString().equals(val)) {
                return clampValue;
            }
            i++;
            clampValue = getClampValue(i, val.hashCode());
        }
        return -1;
    }

    private int getClampValue(int i, int hashCode) {
        return this.clamp((int) ((hashCode + ((i + Math.pow(i, 2)) * CLAMP_CONSTANT))));
    }
}
