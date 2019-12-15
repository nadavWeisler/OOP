import java.util.Collection;

public class CollectionFacadeSet implements SimpleSet {
    protected Collection<String> collection;

    /**
     * CollectionFacadeSet constructor
     * @param collection Some collection
     */
    public CollectionFacadeSet(Collection<String> collection) {
        this.collection = collection;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {
        if(this.contains(newValue)) {
            return false;
        } else {
            this.collection.add(newValue);
            return true;
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
        return this.collection.contains(searchVal);
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True if toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        if(this.contains(toDelete)){
            this.collection.remove(toDelete);
            return true;
        }
        return false;
    }

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        return this.collection.size();
    }
}
