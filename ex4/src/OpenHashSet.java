public class OpenHashSet extends SimpleHashSet {

    /**
     * Constructs a new, empty table with the specified load factors,
     * and the default initial capacity (16).
     *
     * @param upperLoadFactor The upper load factor before rehashing
     * @param lowerLoadFactor The lower load factor before rehashing
     */
    OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        this.hashSet = new LinkedListWrapper[INITIAL_CAPACITY];
    }

    /**
     * A default constructor.
     * Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    OpenHashSet() {
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
    OpenHashSet(String[] data) {
        this();
    }

    /**
     * Check if linked list wrapper is null or empty
     *
     * @param linkedListWrapper linked list wrapper object
     * @return true if null or empty, false else
     */
    private boolean linkedListWrapperNullOrEmpty(LinkedListWrapper linkedListWrapper) {
        return linkedListWrapper == null || linkedListWrapper.IsEmpty();
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
        if (!linkedListWrapperNullOrEmpty((LinkedListWrapper) this.hashSet[clamped])) {
            if (((LinkedListWrapper) this.hashSet[clamped]).GetLinkedList().contains(newValue)) {
                return false;
            }
        }
        ((LinkedListWrapper) this.hashSet[clamped]).AddToLinkList(newValue);
        resize(true);
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
        if (!((LinkedListWrapper) this.hashSet[this.clamp(stringHash)]).IsEmpty()) {
            for (String str :
                    ((LinkedListWrapper) this.hashSet[this.clamp(stringHash)]).GetLinkedList()) {
                if (str.equals(searchVal)) {
                    return true;
                }
            }
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
        resize(false);
        return ((LinkedListWrapper) this.hashSet[this.clamp(stringHash)]).RemoveFromLinkList(toDelete);
    }

    private void resize(boolean up) {
        if(!up && this.hashSet.length == 1){
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
        LinkedListWrapper[] tempHash = (LinkedListWrapper[]) this.hashSet.clone();
        this.hashSet = new String[(int) (tempHash.length * num)];
        for (LinkedListWrapper linkedListWrapper : tempHash) {
            if (!linkedListWrapperNullOrEmpty(linkedListWrapper)) {
                for (String str :
                        linkedListWrapper.GetLinkedList()) {
                    this.add(str);
                }
            }
        }
    }

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        int count = 0;
        for (LinkedListWrapper linkedList :
                ((LinkedListWrapper[]) this.hashSet)) {
            if (linkedList != null && !linkedList.IsEmpty()) {
                count += linkedList.GetLinkedList().size();
            }
        }
        return count;
    }
}
