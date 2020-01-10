package oop.ex6.code.properties;

public class StringProperty extends Property {
    protected String value;

    public StringProperty(String _name, String _type, boolean _isFinal, boolean _method, String _value) {
        super(_name, _type, _isFinal, _method);
        this.value = _value;
    }
}
