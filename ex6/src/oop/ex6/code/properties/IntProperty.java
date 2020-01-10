package oop.ex6.code.properties;

public class IntProperty extends Property {
    protected int value;

    public IntProperty(String _name, String _type, boolean _isFinal, boolean _method, Integer _value) {
        super(_name, _type, _isFinal, _method);
        this.value = _value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
