package oop.ex6.code.properties;

/**
 * Extends Property and represents a int variable
 */
public class IntProperty extends Property {
    protected int value;

    /**
     * Constructor of IntProperty
     * @param _name the given variable name
     * @param _type the given variable type
     * @param _isFinal true when the variable is declared final else false
     * @param _method true when the variable is local in a method else false, i.e is global
     * @param _value the variable value
     */
    public IntProperty(String _name, String _type, boolean _isFinal, boolean _method, Integer _value) {
        super(_name, _type, _isFinal, _method);
        if (_value != null) {
            this.value = _value;
        } else {
            isNull = true;
        }
    }

    /**
     * Set int property value
     * @param value the given value to set
     */
    public void setValue(int value) {
        this.value = value;
        isNull = false;
    }

    /**
     * return the int property value
     * @return int property value
     */
    public int getValue() {
        return value;
    }
}
