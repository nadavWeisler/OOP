public class OpenHashSet extends SimpleHashSet {

    /**
     * Constructs a new, empty table with the specified load factors,
     * and the default initial capacity (16).
     *
     * @param upperLoadFactor The upper load factor before rehashing
     * @param lowerLoadFactor The lower load factor before rehashing
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        this.hashSet = new LinkedListWrapper[INITIAL_CAPACITY];
    }

    /**
     * A default constructor.
     * Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public OpenHashSet() {
        super();
        this.hashSet = new LinkedListWrapper[INITIAL_CAPACITY];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * Duplicate values should be ignored.
     * The new table has the default values of initial capacity (16),
     * upper load factor (0.75), and lower load factor (0.25).
     *
     * @param data Values to add to the set.
     */
    public OpenHashSet(String[] data) {
        this();
        for (String str :
                data) {
            if (str != null) {
                this.add(str);
            }
        }
    }

    /**
     * Check if linked list wrapper is null or empty
     *
     * @param linkedListWrapper linked list wrapper object
     * @return true if null or empty, false else
     */
    private boolean linkedListWrapperNullOrEmpty(LinkedListWrapper linkedListWrapper) {
        return linkedListWrapper != null && linkedListWrapper.IsEmpty();
    }

    /**
     * Resize array
     *
     * @param num Resize up or down int
     */
    private void resize(double num) {
        LinkedListWrapper[] tempHash = (LinkedListWrapper[]) this.hashSet.clone();
        this.hashSet = new LinkedListWrapper[(int) (tempHash.length * num)];
        for (LinkedListWrapper linkedListWrapper : tempHash) {
            if (linkedListWrapperNullOrEmpty(linkedListWrapper)) {
                for (String str :
                        linkedListWrapper.GetLinkedList()) {
                    this.add(str);
                }
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

        int stringHash = newValue.hashCode();
        int clamped = this.clamp(stringHash);
        if (linkedListWrapperNullOrEmpty((LinkedListWrapper) this.hashSet[clamped])) {
            if (((LinkedListWrapper) this.hashSet[clamped]).GetLinkedList().contains(newValue)) {
                return false;
            }
        }

        if (this.hashSet[clamped] == null) {
            this.hashSet[clamped] = new LinkedListWrapper();
        }
        ((LinkedListWrapper) this.hashSet[clamped]).AddToLinkList(newValue);
        if (this.size() > this.capacity() * this.upperLoadFactor) {
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
        int stringHash = searchVal.hashCode();
        if (this.hashSet[this.clamp(stringHash)] != null &&
                ((LinkedListWrapper) this.hashSet[this.clamp(stringHash)]).IsEmpty()) {
            return ((LinkedListWrapper)
                    this.hashSet[this.clamp(stringHash)]).GetLinkedList().contains(searchVal);
        }
        return false;
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True if toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        if (!this.contains(toDelete)) {
            return false;
        }

        int stringHash = toDelete.hashCode();
        if (this.size() > 1 && this.lowerLoadFactor * this.capacity() > this.size()) {
            resize(this.LOW_RESIZE);
        }
        return ((LinkedListWrapper) this.hashSet[this.clamp(stringHash)]).RemoveFromLinkList(toDelete);
    }

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        int count = 0;
        for (LinkedListWrapper linkedList :
                ((LinkedListWrapper[]) this.hashSet)) {
            if (linkedList != null && linkedList.IsEmpty()) {
                count += linkedList.GetLinkedList().size();
            }
        }
        return count;
    }
}
