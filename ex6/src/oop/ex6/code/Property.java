package oop.ex6.code;

public abstract class Property {
    protected String name;
    protected String type;
    protected boolean isFinal;
    protected boolean methodProperty;

    public Property(String _name, String _type, boolean _isFinal, boolean _method) {
        this.name = _name;
        this.type = _type;
        this.isFinal = _isFinal;
        this.methodProperty = _method;
    }
}

class IntProperty extends Property {
    protected int value;

    public IntProperty(String _name, String _type, boolean _isFinal, boolean _method, int _value) {
        super(_name, _type, _isFinal, _method);
        this.value = _value;
    }
}

class DoubleProperty extends Property {
    protected double value;

    public DoubleProperty(String _name, String _type, boolean _isFinal, boolean _method, double _value) {
        super(_name, _type, _isFinal, _method);
        this.value = _value;
    }
}

class BooleanProperty extends Property {
    protected boolean value;

    public BooleanProperty(String _name, String _type, boolean _isFinal, boolean _method, boolean _value) {
        super(_name, _type, _isFinal, _method);
        this.value = _value;
    }
}

class CharProperty extends Property {
    protected char value;

    public CharProperty(String _name, String _type, boolean _isFinal, boolean _method, char _value) {
        super(_name, _type, _isFinal, _method);
        this.value = _value;
    }
}

class StringProperty extends Property {
    protected String value;

    public StringProperty(String _name, String _type, boolean _isFinal, boolean _method, String _value) {
        super(_name, _type, _isFinal, _method);
        this.value = _value;
    }
}