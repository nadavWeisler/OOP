package oop.ex6.code.properties;

public class IntProperty extends Property {
    protected int value;

    public IntProperty(String _name, String _type, boolean _isFinal, boolean _method, Integer _value) {
        super(_name, _type, _isFinal, _method);
        if (_value != null) {
            this.value = _value;
        } else {
            isNull = true;
        }
    }

    public void setValue(int value) {
        this.value = value;
        isNull = false;
    }

    public int getValue() {
        return value;
    }
}
