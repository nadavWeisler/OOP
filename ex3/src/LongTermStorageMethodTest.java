import org.junit.Before;

public abstract class LongTermStorageMethodTest {
    protected LongTermStorage lts;

    @Before
    public void prepareTest() {
        this.lts = new LongTermStorage();
    }
}
