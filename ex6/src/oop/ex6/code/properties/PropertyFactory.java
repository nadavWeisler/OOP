package oop.ex6.code.properties;

import oop.ex6.Utils;
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

    public boolean validValue(String type, String value) {
        if (value == null) {
            return true;
        }
        switch (type) {
            case STRING_TYPE:
                if (!(value.startsWith("\"") && value.endsWith("\""))) {
                    return false;
                }
                break;
            case DOUBLE_TYPE:
                if (!Utils.isDouble(value)) {
                    return false;
                }
                break;
            case INT_TYPE:
                if (!Utils.isInteger(value)) {
                    return false;
                }
                break;
            case CHAR_TYPE:
                if (value.length() != 3 || (!(value.startsWith("'") && value.endsWith("'")))) {
                    return false;
                }
                break;
            case BOOLEAN_TYPE:
                if (!(value.equals("true") ||
                        value.equals("false") ||
                        Utils.isDouble(value))) {
                    return false;
                }
        }
        return true;
    }

    public String getValueFromProperty(Property property) {
        if(property.isNull) {
            return null;
        }
        switch (property.getType()) {
            case STRING_TYPE:
                return "\"" + ((StringProperty)property).getValue() + "\"";
            case INT_TYPE:
                return String.valueOf(((IntProperty)property).getValue());
            case DOUBLE_TYPE:
                return String.valueOf(((DoubleProperty)property).getValue());
            case CHAR_TYPE:
                return "'" + ((CharProperty)property).getValue() + "'";
            case BOOLEAN_TYPE:
                return String.valueOf(((BooleanProperty)property).getValue());
        }
        return null;
    }

    public boolean validTypesTo(String toType, String fromType) {
        if (toType.equals(fromType)) {
            return true;
        } else {
            if (toType.equals(this.BOOLEAN_TYPE)) {
                return fromType.equals(DOUBLE_TYPE) || fromType.equals(INT_TYPE);
            } else {
                return toType.equals(this.DOUBLE_TYPE) && fromType.equals(this.INT_TYPE);
            }
        }
    }

    public Property createProperty(String propertyType, String propertyName,
                                   String propertyValue, boolean isFinal) throws BadFormatException {
        System.out.println(propertyType + " " + propertyName + " " + propertyValue);
        System.out.println(!isPropertyType(propertyType) + " " + !validParameterName(propertyName)
                + " " + !validValue(propertyType, propertyValue));
        if (!isPropertyType(propertyType) || !validParameterName(propertyName) ||
                !validValue(propertyType, propertyValue)) {
            throw new BadFormatException("Property fields are invalid");
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
                    System.out.println(propertyValue);
                    return new CharProperty(propertyName,
                            propertyType, isFinal, false, propertyValue.charAt(1));
            }
        }
        return null;
    }

    public Property createMethodProperty(String propertyType, String propertyName, boolean isFinal) {
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

    public Property updatePropertyFromString(Property oldProperty, String value) throws BadFormatException {
        Property newProperty = oldProperty;
        try {
            switch (oldProperty.type) {
                case STRING_TYPE:
                    ((StringProperty) newProperty).setValue(value.substring(1, value.length() - 1));
                    break;
                case INT_TYPE:
                    ((IntProperty) newProperty).setValue(Integer.parseInt(value));
                    break;
                case DOUBLE_TYPE:
                    ((DoubleProperty) newProperty).setValue(Double.parseDouble(value));
                    break;
                case CHAR_TYPE:
                    ((CharProperty) newProperty).setValue(value.charAt(1));
                    break;
                case BOOLEAN_TYPE:
                    if (Utils.isDouble(value)) {
                        ((BooleanProperty) newProperty).setValue(Double.parseDouble(value) != 0);
                    } else {
                        ((BooleanProperty) newProperty).setValue(Boolean.parseBoolean(value));
                    }
            }
        } catch (Exception exp) {
            throw new BadFormatException("Bad value");
        }

        return newProperty;
    }

    public Property updatePropertyFromOtherProperty(Property toUpdate, Property fromUpdate) throws BadFormatException {
        Property newProperty = toUpdate;
        if (!this.validTypesTo(toUpdate.getType(), fromUpdate.getType())) {
            throw new BadFormatException("Invalid update");
        }
        try {
            switch (toUpdate.getType()) {
                case STRING_TYPE:
                    ((StringProperty) newProperty).setValue(((StringProperty) fromUpdate).getValue());
                    break;
                case INT_TYPE:
                    ((IntProperty) newProperty).setValue(((IntProperty) fromUpdate).getValue());
                    break;
                case DOUBLE_TYPE:
                    if (fromUpdate.getType().equals(INT_TYPE)) {
                        ((DoubleProperty) newProperty).setValue(((IntProperty) fromUpdate).getValue());
                    } else {
                        ((DoubleProperty) newProperty).setValue(((DoubleProperty) fromUpdate).getValue());
                    }
                    break;
                case CHAR_TYPE:
                    ((CharProperty) newProperty).setValue(((CharProperty) fromUpdate).getValue());
                    break;
                case BOOLEAN_TYPE:
                    if (fromUpdate.getType().equals(INT_TYPE)) {
                        ((BooleanProperty) newProperty).setValue(((IntProperty) fromUpdate).getValue() != 0);
                    } else if (fromUpdate.getType().equals(DOUBLE_TYPE)) {
                        ((BooleanProperty) newProperty).setValue(((DoubleProperty) fromUpdate).getValue() != 0);
                    } else {
                        ((BooleanProperty) newProperty).setValue(((BooleanProperty) fromUpdate).getValue());
                    }
            }
        } catch (Exception exp) {
            throw new BadFormatException("Bad value");
        }

        return newProperty;
    }
}
