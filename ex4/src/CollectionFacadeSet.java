import java.util.Collection;

public class CollectionFacadeSet implements SimpleSet {
    protected Collection<String> collection;

    CollectionFacadeSet(Collection<String> collection) {
        this.collection = collection;
    }

    @Override
    public boolean add(String newValue) {
        if(this.contains(newValue)) {
            return false;
        } else {
            this.collection.add(newValue);
            return true;
        }
    }

    @Override
    public boolean contains(String searchVal) {
        return this.collection.contains(searchVal);
    }

    @Override
    public boolean delete(String toDelete) {
        if(this.contains(toDelete)){
            this.collection.remove(toDelete);
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return this.collection.size();
    }
}
