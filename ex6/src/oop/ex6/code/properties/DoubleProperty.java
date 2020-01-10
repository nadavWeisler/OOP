package oop.ex6.code.properties;

public class DoubleProperty extends Property {
    protected double value;

    public DoubleProperty(String _name, String _type, boolean _isFinal, boolean _method, Double _value) {
        super(_name, _type, _isFinal, _method);
        this.value = _value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}