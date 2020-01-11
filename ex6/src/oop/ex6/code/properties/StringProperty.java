package oop.ex6.code.properties;

public class StringProperty extends Property {
    protected String value;

    public StringProperty(String _name, String _type, boolean _isFinal, boolean _method, String _value) {
        super(_name, _type, _isFinal, _method);
        if(_value == null) {
            isNull = true;
        } else {
            this.value = _value;
        }
    }

    public void setValue(String value) {
        this.value = value;
        isNull = value == null;
    }

    public String getValue() {
        return value;
    }
}
