public class StringWrapper {
    private String string;

    public StringWrapper(String val) {
        this.string = val;
    }

    public StringWrapper() {
        this.string = null;
    }

    public String getString() {
        return this.string;
    }
}
