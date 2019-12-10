import java.util.Collection;

public class CollectionFacadeSet implements SimpleSet {
    protected Collection<String> collection;

    CollectionFacadeSet(Collection<String> collection) {

    }

    @Override
    public boolean add(String newValue) {
        return false;
    }

    @Override
    public boolean contains(String searchVal) {
        return false;
    }

    @Override
    public boolean delete(String toDelete) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }
}
