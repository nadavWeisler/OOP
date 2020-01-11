package oop.ex6.code.properties;

/**
 * Extends Property and represents a boolean variable
 */
public class BooleanProperty extends Property {

    protected boolean value;

    /**
     * Constructor of BooleanProperty
     * @param _name the given variable name
     * @param _type the given variable type
     * @param _isFinal true when the variable is declared final else false
     * @param _method true when the variable is local in a method else false, i.e is global
     * @param _value the variable value
     */
    public BooleanProperty(String _name, String _type, boolean _isFinal, boolean _method, Boolean _value) {
        super(_name, _type, _isFinal, _method);
        if (_value != null) {
            this.value = _value;
        } else {
            isNull = true;
        }
    }

    /**
     * Set boolean property value
     * @param value the given value to set
     */
    public void setValue(boolean value) {
        this.value = value;
        this.isNull = false;
    }

    /**
     * return the boolean property value
     * @return boolean property value
     */
    public boolean getValue(){
        return this.value;
    }
}