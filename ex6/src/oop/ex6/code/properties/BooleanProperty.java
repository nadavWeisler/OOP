package oop.ex6.code.properties;

public class BooleanProperty extends Property {
    protected boolean value;

    public BooleanProperty(String _name, String _type, boolean _isFinal, boolean _method, Boolean _value) {
        super(_name, _type, _isFinal, _method);
        if (_value != null) {
            this.value = _value;
        } else {
            isNull = true;
        }
    }

    public void setValue(boolean value) {
        this.value = value;
        this.isNull = false;
    }

    public boolean getValue(){
        return this.value;
    }
}