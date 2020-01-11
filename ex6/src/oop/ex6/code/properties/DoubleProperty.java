package oop.ex6.code.properties;

/**
 * Extends Property and represents a double variable
 */
public class DoubleProperty extends Property {
    protected double value;

    /**
     * Constructor of DoubleProperty
     * @param _name the given variable name
     * @param _type the given variable type
     * @param _isFinal true when the variable is declared final else false
     * @param _method true when the variable is local in a method else false, i.e is global
     * @param _value the variable value
     */
    public DoubleProperty(String _name, String _type, boolean _isFinal, boolean _method, Double _value) {
        super(_name, _type, _isFinal, _method);
        if (_value != null) {
            this.value = _value;
        } else {
            isNull = true;
        }
    }

    /**
     * Set double property value
     * @param value the given value to set
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * return the double property value
     * @return double property value
     */
    public double getValue() {
        return value;
    }
}