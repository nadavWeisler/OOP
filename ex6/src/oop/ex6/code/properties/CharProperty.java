package oop.ex6.code.properties;

public class CharProperty extends Property {
    protected char value;

    public CharProperty(String _name, String _type, boolean _isFinal, boolean _method, Character _value) {
        super(_name, _type, _isFinal, _method);
        this.value = _value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }
}
