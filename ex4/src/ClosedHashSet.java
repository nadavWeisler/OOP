public class ClosedHashSet extends SimpleHashSet {
    private final String EMPTY_STRING = "";

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
        this.hashSet = new String[INITIAL_CAPACITY];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one
     *
     * @param data Values to add to the set.
     */
    public ClosedHashSet(String[] data) {
        super();
        this.hashSet = new StringWrapper[INITIAL_CAPACITY];
        if (data == null) {
            return;
        }

        for (int i = 0; i < this.hashSet.length; i++) {
            if (data[i] != null && data[i].isEmpty()) {
                this.hashSet[i] = new StringWrapper(EMPTY_STRING);
            } else {
                this.add(data[i]);
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
        this.hashSet[i] = new StringWrapper(newValue);
        resize(true);
        return true;
    }

    /**
     * Get free index of hash set
     *
     * @param hashVal Hashed string
     * @return index
     */
    private int getFreeIndex(int hashVal) {
        int i = 0;
        while (this.hashSet[clamp(hashVal + (i + Math.abs(i) / 2))] != null &&
                ((StringWrapper) this.hashSet[clamp(hashVal + (i + Math.abs(i) / 2))]).getString() != null &&
                i < this.hashSet.length) {
            i++;
        }
        return clamp(hashVal + ((i + Math.abs(i)) / 2));
    }

    /**
     * Resize hash set if needed (By 2 or by 0.5)
     */
    private void resize(boolean up) {
        if (!up && this.hashSet.length == 1) {
            return;
        }
        double num;
        if (up && this.size() > upperLoadFactor * this.capacity()) {
            num = 2;
        } else if (!up && this.size() < this.lowerLoadFactor * capacity()) {
            num = 0.5;
        } else {
            return;
        }
        StringWrapper[] tempHash = (StringWrapper[]) this.hashSet.clone();
        this.hashSet = new StringWrapper[(int) (tempHash.length * num)];
        for (int i = 0; i < tempHash.length; i++) {
            if (tempHash[i] != null) {
                if (tempHash[i].getString().isEmpty()) {
                    this.hashSet[i] = EMPTY_STRING;
                } else {
                    this.add(tempHash[i].getString());
                }
            }
        }
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
     * Get index of string in hash
     *
     * @param val String
     * @return Index if exist, -1 else
     */
    public int getIndexOf(String val) {
        int i = 0;
        int hash = val.hashCode();
        while (clamp(hash + i) < this.capacity() && this.hashSet[clamp(hash + i)] != null) {
            if (this.hashSet[clamp(hash + i)].equals(val)) {
                return clamp(hash + i);
            }
            i++;
        }
        return -1;
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
            this.hashSet[index] = EMPTY_STRING;
            resize(false);
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
            if (item != null && !item.getString().isEmpty()) {
                count++;
            }
        }
        return count;
    }
}
