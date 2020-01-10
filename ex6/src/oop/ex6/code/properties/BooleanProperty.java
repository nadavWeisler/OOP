package oop.ex6.code.properties;

public class BooleanProperty extends Property {
    protected boolean value;

    public BooleanProperty(String _name, String _type, boolean _isFinal, boolean _method, Boolean _value) {
        super(_name, _type, _isFinal, _method);
        this.value = _value;
    }
}