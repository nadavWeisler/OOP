package oop.ex6.code.properties;

/**
 * Extends Property and represents a char variable
 */
public class CharProperty extends Property {
    protected char value;

    /**
     * Constructor of CharProperty
     * @param _name the given variable name
     * @param _type the given variable type
     * @param _isFinal true when the variable is declared final else false
     * @param _method true when the variable is local in a method else false, i.e is global
     * @param _value the variable value
     */
    public CharProperty(String _name, String _type, boolean _isFinal, boolean _method, Character _value) {
        super(_name, _type, _isFinal, _method);
        if (_value != null) {
            this.value = _value;
        } else {
            isNull = true;
        }
    }

    /**
     * Set char property value
     * @param value the given value to set
     */
    public void setValue(char value) {
        this.value = value;
    }

    /**
     * return the char property value
     * @return char property value
     */
    public char getValue() {
        return value;
    }
}
