package oop.ex6.parsers;

import oop.ex6.Utils;
import oop.ex6.code.properties.Property;
import oop.ex6.code.properties.PropertyFactory;
import oop.ex6.exceptions.BadFormatException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Super class for the specific parsers
 */
public class Parser {
    // Creates the finals to use in the parser
    protected static final String FINAL_CONSTANT = "final";
    protected static final String EMPTY_STRING = "";
    protected static final String BLANK_SPACE = " ";
    protected static final String COMMA = ",";
    protected static final String EQUALS = "=";
    protected static final String WHILE_CONSTANT = "while";
    protected static final String If_CONSTANT = "if";
    protected static final String VOID_CONSTANT = "void";
    protected static final String ILLEGAL_CODE = "Illegal code";
    protected static final String END_BRACKET = ";";
    protected static final String OPEN_SECTION = "{";
    protected static final String CLOSE_SECTION = "}";
    protected static final String STRING_TYPE = "String";
    protected static final String INT_TYPE = "int";
    protected static final String DOUBLE_TYPE = "double";
    protected static final String CHAR_TYPE = "char";
    protected static final String BOOLEAN_TYPE = "boolean";
    protected static final char CLOSE_BRACKET = ')';
    protected static final char OPEN_BRACKET = '(';
    protected static final String TRUE = "true";
    protected static final String FALSE = "false";
    protected HashMap<String, HashMap<String, Property>> local_properties = new HashMap<>();

    /**
     * Verifies if the given code line is a property creation line
     * @param line the given line to verify
     * @return true if the line is a property line , else false
     */
    protected boolean isPropertyLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            return PropertyFactory.getInstance().isPropertyType(splitLine[0]) ||
                    splitLine[0].equals(FINAL_CONSTANT);
        }
        return false;
    }

    /**
     * Verifies if the given code line is a while loop line
     * @param line the given code line to verify
     * @return true if the code line is a start of a while loop else false
     */
    protected boolean isWhileLine(String line) {
        line = Utils.RemoveAllSpacesAtEnd(line);
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            return splitLine[0].startsWith(WHILE_CONSTANT + OPEN_BRACKET) ||
                    splitLine[0].equals(WHILE_CONSTANT);
        }
        return false;
    }

    /**
     * Verifies if the given code line is a if condition line
     * @param line the given code line to verify
     * @return true if the code line is a start of a if condition line else false
     */
    protected boolean isIfLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            return splitLine[0].startsWith(If_CONSTANT + OPEN_BRACKET) ||
                    splitLine[0].equals(If_CONSTANT);
        }
        return false;
    }

    /**
     * Verifies if the code line ends with the bracket '}'
     * @param line the given line to verify
     * @return true if the line end with the bracket '}' , else false
     */
    protected boolean isEnd(String line) {
        return line.replace(" ", "").equals("}");
    }

    /**
     * Returns an array of the properties in the given code line
     * @param line the given line to extract the properties from
     * @param properties the existing properties to verify
     * @return an array of properties in the code line
     * @throws BadFormatException when the code line is illegal
     */
    protected ArrayList<Property> getPropertiesFromLine(String line,
                                                        ArrayList<HashMap<String, HashMap<String, Property>>>
                                                                properties)
            throws BadFormatException {
        line = Utils.RemoveAllSpacesAtEnd(line);
        int firstIndex = 0;
        int spaceIndex = line.indexOf(BLANK_SPACE);
        String currentString = line.substring(firstIndex, spaceIndex);
        boolean isFinal = false;
        String type;
        if (currentString.equals(FINAL_CONSTANT)) {
            isFinal = true;
            spaceIndex = line.substring(6).indexOf(BLANK_SPACE);
            if (spaceIndex == -1) {
                throw new BadFormatException(ILLEGAL_CODE);
            }
            line = Utils.RemoveAllSpacesAtEnd(line.substring(6));
        }
        firstIndex = 0;
        spaceIndex = line.indexOf(BLANK_SPACE);
        currentString = line.substring(firstIndex, spaceIndex);

        if (PropertyFactory.getInstance().isPropertyType(currentString)) {
            type = currentString;
        } else {
            throw new BadFormatException(ILLEGAL_CODE);
        }

        line = Utils.RemoveAllSpacesAtEnd(line.substring(spaceIndex));
        String[] splitLine = line.split(COMMA);
        ArrayList<String> waitForValue = new ArrayList<>();
        ArrayList<Property> result = new ArrayList<>();
        boolean ended = false;
        for (String item : splitLine) {
            item = Utils.RemoveAllSpacesAtEnd(item);
            if (item.endsWith(END_BRACKET)) {
                if (!Utils.RemoveAllSpacesAtEnd(splitLine[splitLine.length - 1]).equals(item)) {
                    throw new BadFormatException(ILLEGAL_CODE);
                } else {
                    item = item.substring(0, item.length() - 1);
                    ended = true;
                }
            }
            if (item.contains(EQUALS)) {
                String[] splitItem = item.split(EQUALS);
                if (splitItem.length != 2) {
                    throw new BadFormatException(ILLEGAL_CODE);
                }
                String name = Utils.RemoveAllSpacesAtEnd(splitItem[0]);
                String value = Utils.RemoveAllSpacesAtEnd(splitItem[1]);

                if (PropertyFactory.getInstance().validParameterName(name)) {
                    throw new BadFormatException(ILLEGAL_CODE);
                } else {
                    for (HashMap<String, HashMap<String, Property>> properties_hash : properties) {
                        Property existProp = Utils.existInProperties(value, properties_hash);
                        if (existProp != null) {
                            value = PropertyFactory.getInstance().getValueFromProperty(existProp);
                            if (value == null && !existProp.isMethodProperty()) {
                                throw new BadFormatException(ILLEGAL_CODE);
                            }
                        }
                    }
                    if (PropertyFactory.getInstance().validValue(type, value)) {
                        throw new BadFormatException(ILLEGAL_CODE);
                    } else {
                        for (String waitItem : waitForValue) {
                            result.add(PropertyFactory.getInstance().createProperty(type, waitItem, value, isFinal));
                        }
                        result.add(PropertyFactory.getInstance().createProperty(type, name, value, isFinal));
                    }
                }
            } else {
                if (PropertyFactory.getInstance().validParameterName(item)) {
                    throw new BadFormatException(ILLEGAL_CODE);
                } else {
                    waitForValue.add(item);
                }
            }
        }

        if (!ended) {
            throw new BadFormatException(ILLEGAL_CODE);
        }

        if (waitForValue.size() > 0) {
            for (String name : waitForValue) {
                if (isFinal) {
                    throw new BadFormatException(ILLEGAL_CODE);
                }
                result.add(PropertyFactory.getInstance().createProperty(type, name,
                        null, false));
            }
        }

        return result;
    }

    /**
     * Verifies if the code line is an assignment line for global properties
     * @param line the given line to verify
     * @return true if the line is an assignment to global properties, else false
     */
    protected boolean ifAssignGlobalPropertyLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            for (String type : FileParser.getInstance().global_properties.keySet()) {
                if (FileParser.getInstance().global_properties.get(type).containsKey(splitLine[0]) ||
                        FileParser.getInstance().global_properties.get(type).containsKey(splitLine[0])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verifies if the code line is an assignment line for local properties
     * @param line the given line to verify
     * @return true if the line is an assignment to local properties, else false
     */
    protected boolean ifAssignLocalPropertyLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            for (String type : this.local_properties.keySet()) {
                if (this.local_properties.get(type).containsKey(splitLine[0]) ||
                        this.local_properties.get(type).containsKey(splitLine[0])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verifies if the given property exist according to a given property name
     * @param name the given property name to verify
     * @return true if the property exist else false
     */
    protected boolean globalPropertyExist(String name) {
        for (String type : FileParser.getInstance().global_properties.keySet()) {
            if (FileParser.getInstance().global_properties.get(type).containsKey(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifies if the given property exist according to a given property name
     * @param name the given property name to verify
     * @return true if the property exist else false
     */
    protected boolean localPropertyExist(String name) {
        for (String type : this.local_properties.keySet()) {
            if (this.local_properties.get(type).containsKey(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the property type according to the property name
     * @param name the given property name to verify
     * @param properties all the existing properties to verify in
     * @return the given property type, if the property was not found then returns an empty string
     */
    protected String getParameterType(String name,
                                      HashMap<String, HashMap<String, Property>> properties) {
        for (String type : properties.keySet()) {
            for (String propertyName : properties.get(type).keySet()) {
                if (name.equals(propertyName)) {
                    return type;
                }
            }
        }
        return EMPTY_STRING;
    }

    /**
     * Assign a value to a local property
     * @param line the code line with the assignment
     * @throws BadFormatException when the assignment is invalid
     */
    protected void AssignValueToLocalProperties(String line) throws BadFormatException {
        line = Utils.RemoveAllSpacesAtEnd(line);
        String[] splitLine = line.split(EQUALS);
        if (splitLine.length != 2) {
            throw new BadFormatException(ILLEGAL_CODE);
        }
        String value = Utils.RemoveAllSpacesAtEnd(splitLine[1]);
        String name = Utils.RemoveAllSpacesAtEnd(splitLine[0]);

        if (!this.localPropertyExist(name)) {
            throw new BadFormatException(ILLEGAL_CODE);
        }
        if (value.endsWith(END_BRACKET)) {
            value = value.substring(0, value.length() - 1);
        }
        String propertyType = this.getParameterType(name, this.local_properties);
        if (PropertyFactory.getInstance().validValue(propertyType, value)) {
            throw new BadFormatException(ILLEGAL_CODE);
        }

        Property currentProperty = PropertyFactory.getInstance().getUpdatedProperty(
                this.local_properties.get(propertyType).get(name), value);

        this.local_properties.get(propertyType).put(name, currentProperty);
    }

    /**
     * Assign a value to a global property
     * @param line the code line with the assignment
     * @throws BadFormatException when the assignment is invalid
     */
    protected void AssignValueToGlobalProperties(String line) throws BadFormatException {
        line = Utils.RemoveAllSpacesAtEnd(line);
        String[] splitLine = line.split(EQUALS);
        if (splitLine.length != 2) {
            throw new BadFormatException(ILLEGAL_CODE);
        }
        String value = Utils.RemoveAllSpacesAtEnd(splitLine[1]);
        String name = Utils.RemoveAllSpacesAtEnd(splitLine[0]);

        if (!this.globalPropertyExist(name)) {
            throw new BadFormatException(ILLEGAL_CODE);
        }
        String propertyType = this.getParameterType(name, FileParser.getInstance().global_properties);
        if (value.endsWith(END_BRACKET)) {
            value = value.substring(0, value.length() - 1);
        }
        if (PropertyFactory.getInstance().validValue(propertyType, value)) {
            throw new BadFormatException(ILLEGAL_CODE);
        }

        Property currentProperty = PropertyFactory.getInstance().getUpdatedProperty(
                FileParser.getInstance().global_properties.get(propertyType).get(name), value);

        FileParser.getInstance().global_properties.get(propertyType).put(name, currentProperty);
    }
}
