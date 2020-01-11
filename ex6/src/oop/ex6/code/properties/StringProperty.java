package oop.ex6.code.properties;

/**
 * Extends Property and represents a String variable
 */
public class StringProperty extends Property {

    protected String value;

    /**
     * Constructor of StringProperty
     * @param _name the given variable name
     * @param _type the given variable type
     * @param _isFinal true when the variable is declared final else false
     * @param _method true when the variable is local in a method else false, i.e is global
     * @param _value the variable value
     */
    public StringProperty(String _name, String _type, boolean _isFinal, boolean _method, String _value) {
        super(_name, _type, _isFinal, _method);
        if(_value == null) {
            isNull = true;
        } else {
            this.value = _value;
        }
    }

    /**
     * Set String property value
     * @param value the given value to set
     */
    public void setValue(String value) {
        this.value = value;
        isNull = value == null;
    }

    /**
     * return the String property value
     * @return String property value
     */
    public String getValue() {
        return value;
    }
}
