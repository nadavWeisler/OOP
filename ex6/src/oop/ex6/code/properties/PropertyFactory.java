package oop.ex6.code.properties;

import oop.ex6.Utils;
import oop.ex6.exceptions.BadFormatException;

import java.util.regex.Pattern;

/**
 * PropertyFactory class creates all different properties types, uses the Singleton design pattern
 */
public class PropertyFactory {

    // creates a single instance of Property Factory - singleton design
    private static PropertyFactory factory = new PropertyFactory();

    // finals to use in the factory
    private final String STRING_TYPE = "String";
    private final String INT_TYPE = "int";
    private final String DOUBLE_TYPE = "double";
    private final String CHAR_TYPE = "char";
    private final String BOOLEAN_TYPE = "boolean";
    private final String TRUE = "true";
    private final String FALSE = "false";

    private final Pattern stringPattern = Pattern.compile("\".*\"");
    private final Pattern charPattern = Pattern.compile("'.'");
    private final String ILLEGAL_CODE = "Illegal code";

    // private constructor - singleton design
    private PropertyFactory() {}

    /**
     * Verifies if the given String one of the valid variable types
     * @param str the given string to verify
     * @return true if the String is a valid variable type else false
     */
    public boolean isPropertyType(String str) {
        return str.equals(DOUBLE_TYPE) ||
                str.equals(BOOLEAN_TYPE) ||
                str.equals(INT_TYPE) ||
                str.equals(STRING_TYPE) ||
                str.equals(CHAR_TYPE);
    }

    /**
     * returns the single PropertyFactory instance - singleton design
     * @return PropertyFactory instance
     */
    public static PropertyFactory getInstance() {
        return factory;
    }

    /**
     * Verifies that the given string is a valid variable name
     * @param name the given string to verify
     * @return true if the string is a valid name else false
     */
    public boolean validParameterName(String name) {
        if (name.length() == 0) {
            return true;
        } else if (Pattern.matches(".*\\W+.*", name)) {
            return true;
        } else if (Pattern.matches("\\d.*", name)) { //Name start with diit
            return true;
        } else if (name.contains("_")) { // if the name contains _ then it has to contain at least
            // one more letter or digit
            return !Pattern.matches(".*[a-zA-Z0-9]+.*", name);
        }
        return false;
    }

    /**
     * Verifies that the variable assignment is valid according to the variable type
     * @param type  the given variable type
     * @param value the given variable value to be assigned
     * @return true if the assignment is valid else false
     */
    public boolean validValue(String type, String value) {
        if (value == null) {
            return false;
        }
        switch (type) {
            case STRING_TYPE:
                if (!stringPattern.matcher(value).matches()) {
                    return true;
                }
                break;
            case DOUBLE_TYPE:
                if (!Utils.isDouble(value)) {
                    return true;
                }
                break;
            case INT_TYPE:
                if (Utils.isInteger(value)) {
                    return true;
                }
                break;
            case CHAR_TYPE:
                if (!charPattern.matcher(value).matches()) {
                    return true;
                }
                break;
            case BOOLEAN_TYPE:
                if (!Utils.isDouble(value) && !value.equals(TRUE) && !value.equals(FALSE)) {
                    return true;
                }
        }
        return false;
    }

    /**
     * Returns the given property value
     * @param property the given property
     * @return property value
     */
    public String getValueFromProperty(Property property) {
        if (property.isNull) {
            return null;
        }
        switch (property.getType()) {
            case STRING_TYPE:
                return "\"" + ((StringProperty) property).getValue() + "\"";
            case INT_TYPE:
                return String.valueOf(((IntProperty) property).getValue());
            case DOUBLE_TYPE:
                return String.valueOf(((DoubleProperty) property).getValue());
            case CHAR_TYPE:
                return "'" + ((CharProperty) property).getValue() + "'";
            case BOOLEAN_TYPE:
                return String.valueOf(((BooleanProperty) property).getValue());
        }
        return null;
    }

    /**
     * Verifies that the assignment of the variable is legal from one variable to another
     * @param toType The variable that wants to be assigned to
     * @param fromType The variable that wants to be assigned from
     * @return true if the assignment is valid, else false
     */
    public boolean validTypesTo(String toType, String fromType) {
        if (toType.equals(fromType)) {
            return false;
        } else {
            if (toType.equals(this.BOOLEAN_TYPE)) {
                return !fromType.equals(DOUBLE_TYPE) && !fromType.equals(INT_TYPE);
            } else {
                return !toType.equals(this.DOUBLE_TYPE) || !fromType.equals(this.INT_TYPE);
            }
        }
    }

    /**
     * Creates a specific property
     * @param propertyType the created property type
     * @param propertyName the created property name
     * @param propertyValue the created property value
     * @param isFinal true if the property is declared as final else false
     * @return the created property
     * @throws BadFormatException when the property deceleration is invalid
     */
    public Property createProperty(String propertyType, String propertyName,
                                   String propertyValue, boolean isFinal) throws BadFormatException {
        if (!isPropertyType(propertyType) || validParameterName(propertyName) ||
                validValue(propertyType, propertyValue)) {
            throw new BadFormatException(ILLEGAL_CODE);
        }
        if (propertyValue == null) {
            switch (propertyType) {
                case STRING_TYPE:
                    return new StringProperty(propertyName,
                            propertyType, isFinal, false, null);
                case INT_TYPE:
                    return new IntProperty(propertyName,
                            propertyType, isFinal, false, null);
                case DOUBLE_TYPE:
                    return new DoubleProperty(propertyName,
                            propertyType, isFinal, false, null);
                case BOOLEAN_TYPE:
                    return new BooleanProperty(propertyName,
                            propertyType, isFinal, false, null);
                case CHAR_TYPE:
                    return new CharProperty(propertyName,
                            propertyType, isFinal, false, null);
            }
        } else {
            switch (propertyType) {
                case STRING_TYPE:
                    return new StringProperty(propertyName,
                            propertyType, isFinal, false, propertyValue);
                case INT_TYPE:
                    return new IntProperty(propertyName,
                            propertyType, isFinal, false, Integer.parseInt(propertyValue));
                case DOUBLE_TYPE:
                    return new DoubleProperty(propertyName,
                            propertyType, isFinal, false, Double.parseDouble(propertyValue));
                case BOOLEAN_TYPE:
                    return new BooleanProperty(propertyName,
                            propertyType, isFinal, false, Boolean.parseBoolean(propertyValue));
                case CHAR_TYPE:
                    return new CharProperty(propertyName,
                            propertyType, isFinal, false, propertyValue.charAt(1));
            }
        }
        return null;
    }

    /**
     * Creates specific properties for the method (does not need assignment)
     * @param propertyType the given property type
     * @param propertyName the given property name
     * @param isFinal indicates if the given property is a final variable
     * @return Property object when the object is successfully created
     * @throws BadFormatException when the property is not valid to create
     */
    public Property createMethodProperty(String propertyType, String propertyName, boolean isFinal)
            throws BadFormatException {
        if (!isPropertyType(propertyType) || validParameterName(propertyName)) {
            throw new BadFormatException(ILLEGAL_CODE);
        }
        switch (propertyType) {
            case STRING_TYPE:
                return new StringProperty(propertyName,
                        propertyType, isFinal, true, null);
            case INT_TYPE:
                return new IntProperty(propertyName,
                        propertyType, isFinal, true, null);
            case DOUBLE_TYPE:
                return new DoubleProperty(propertyName,
                        propertyType, isFinal, true, null);
            case BOOLEAN_TYPE:
                return new BooleanProperty(propertyName,
                        propertyType, isFinal, true, null);
            case CHAR_TYPE:
                return new CharProperty(propertyName,
                        propertyType, isFinal, true, null);
        }
        return null;
    }

    /**
     * Sets the value for the given property
     * @param property the given property to set the value to
     * @param value the given value to set in the property
     * @return the Property object with the new value
     * @throws BadFormatException when the given value cannot be assigned to the existing property
     */
    public Property getUpdatedProperty(Property property, String value) throws BadFormatException {
        if(property.isFinal) {
            throw new BadFormatException(ILLEGAL_CODE);
        }
        switch (property.getType()) {
            case STRING_TYPE:
                ((StringProperty) property).setValue(value.substring(1, value.length() - 1));
                break;
            case INT_TYPE:
                ((IntProperty) property).setValue(Integer.parseInt(value));
                break;
            case DOUBLE_TYPE:
                ((DoubleProperty) property).setValue(Double.parseDouble(value));
                break;
            case CHAR_TYPE:
                ((CharProperty) property).setValue(value.charAt(1));
                break;
            case BOOLEAN_TYPE:
                if (Utils.isDouble(value)) {
                    ((BooleanProperty) property).setValue(Double.parseDouble(value) != 0);
                } else {
                    ((BooleanProperty) property).setValue(Boolean.parseBoolean(value));
                }
        }
        return property;
    }
}
