package oop.ex6.code.properties;

public abstract class Property {
    protected String name;
    protected String type;
    protected boolean isFinal;
    protected boolean methodProperty;
    protected boolean isNull;

    public Property(String _name, String _type, boolean _isFinal, boolean _method) {
        this.name = _name;
        this.type = _type;
        this.isFinal = _isFinal;
        this.methodProperty = _method;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return this.type;
    }
}




