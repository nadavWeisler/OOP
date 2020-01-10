package oop.ex6.code.properties;

import oop.ex6.Validations;
import oop.ex6.exceptions.BadFormatException;

import java.util.regex.Pattern;

public class PropertyFactory {
    private static PropertyFactory factory = new PropertyFactory();
    private final String STRING_TYPE = "String";
    private final String INT_TYPE = "int";
    private final String DOUBLE_TYPE = "double";
    private final String CHAR_TYPE = "char";
    private final String BOOLEAN_TYPE = "boolean";
    private final String EMPTY_STRING = "";
    private final String BLANK_SPACE = " ";

    private PropertyFactory() {

    }

    public boolean isPropertyType(String str) {
        return str.equals(DOUBLE_TYPE) ||
                str.equals(BOOLEAN_TYPE) ||
                str.equals(INT_TYPE) ||
                str.equals(STRING_TYPE) ||
                str.equals(CHAR_TYPE);
    }

    public static PropertyFactory getInstance() {
        return factory;
    }

    private boolean validParameterName(String name) {
        if (name.length() == 0) {
            return false;
        } else if (Pattern.matches(".*\\W+.*", name)) {
            return false;
        } else if (Pattern.matches("\\d.*", name)) { //Name start with digit
            return false;
        } else if (name.contains("_")) { // if the name contains _ then it has to contain at least
            // one more letter or digit
            if (!Pattern.matches(".*[a-zA-Z0-9]+.*", name)) {
                return false;
            }
        }
        return true;
    }

    private boolean validValue(String type, String value) {
        switch (type) {
            case STRING_TYPE:
                if (!value.startsWith("\"") || value.endsWith("\"")) {
                    return false;
                }
            case INT_TYPE:
                if (!Validations.getValidations().isInteger(value)) {
                    return false;
                }
            case DOUBLE_TYPE:
                if (!Validations.getValidations().isDouble(value)) {
                    return false;
                }
            case CHAR_TYPE:
                if ((!value.startsWith("'") || value.endsWith("'")) &&
                        value.length() == 3) {
                    return false;
                }
            case BOOLEAN_TYPE:
                if (!(value.equals("true") ||
                        value.equals("false") ||
                        Validations.getValidations().isDouble(value))) {
                    return false;
                }
        }
        return true;
    }

    public boolean validValue(String type) {
        return validValue(type, STRING_TYPE) ||
                validValue(type, INT_TYPE) ||
                validValue(type, DOUBLE_TYPE) ||
                validValue(type, CHAR_TYPE) ||
                validValue(type, BOOLEAN_TYPE);
    }

    public Property createProperty(String propertyType, String propertyName,
                                   String propertyValue, boolean isFinal) throws BadFormatException {
        if (!isPropertyType(propertyType) || !validParameterName(propertyName) ||
                !validValue(propertyType, propertyValue)) {
            throw new BadFormatException("b");
        }
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
        return null;
    }
}
