package oop.ex6.parsers;

import oop.ex6.Utils;
import oop.ex6.code.properties.Property;
import oop.ex6.code.properties.PropertyFactory;
import oop.ex6.exceptions.BadFormatException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 * Super class that contains different method to use when parsing the text file into data sections
 */
public abstract class Parser {

    // Creating finals for the parsing usage
    protected final String FINAL_CONSTANT = "final";
    protected final String EMPTY_STRING = "";
    protected final String BLANK_SPACE = " ";
    protected final String COMMA = ",";
    protected final String EQUALS = "=";
    protected final String WHILE_CONSTANT = "while";
    protected final String If_CONSTANT = "if";

    // Saves all the file variables
    protected HashMap<String, HashMap<String, Property>> properties = new HashMap<>();

    /**
     * Verifies if the code line is a comment line
     * @param line the given line to verify
     * @return true if the code line is comment line, else false
     */
    protected boolean isComment(String line) {
        return line.startsWith("//");
    }

    /**
     * Verifies if the code line is an empty line
     * @param line the given line to verify
     * @return true if the code line is empty line else, false
     */
    protected boolean isEmpty(String line) {
        return line.isBlank() || line.isEmpty();
    }

    /**
     * TODO
     * @param name
     * @return
     */
    protected ArrayList<String> GetPropertyTypeOptions(String name) {
        ArrayList<String> options = new ArrayList<>();
        for (String type : this.properties.keySet()) {
            if (this.properties.get(type).containsKey(name)) {
                options.add(type);
            }
        }
        return options;
    }

    /**
     * TODO
     * @param line
     * @return
     */
    protected boolean ifAssignPropertyLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            for (String type : this.properties.keySet()) {
                if (this.properties.get(type).containsKey(splitLine[0])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * TODO
     * @param newProperties
     * @throws BadFormatException
     */
    protected void addProperties(HashMap<String, HashMap<String, Property>> newProperties) throws BadFormatException {
        for (String type : newProperties.keySet()) {
            if (this.properties.containsKey(type)) {
                HashMap<String, Property> newTypeProperties = newProperties.get(type);
                for (String currentProperty : newTypeProperties.keySet()) {
                    if (this.properties.get(type).containsKey(currentProperty)) {
                        throw new BadFormatException("Already contain this key!");
                    }
                }
            } else {
                this.properties.put(type, newProperties.get(type));
            }
        }
    }

    public boolean propertyExistWithType(String propertyType, String name) {
        if (this.properties.containsKey(propertyType)) {
            Set<String> propertiesKeySet = this.properties.keySet();
            return propertiesKeySet.contains(name);
        }
        return false;
    }

    /**
     * Verifies if the given property exist according to a given property name
     * @param name the given property name to verify
     * @return true if the property exist else false
     */
    public boolean propertyExist(String name) {
        for (String type : this.properties.keySet()) {
            if (this.properties.get(type).containsKey(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * TODO
     * @param line
     * @return
     * @throws BadFormatException
     */
    protected HashMap<String, HashMap<String, Property>> getPropertyFromLine(String line) throws BadFormatException {
        HashMap<String, HashMap<String, Property>> result = new HashMap<>();
        line = line.substring(0, line.length() - 1);
        String[] splitLine = line.split(COMMA);
        String currentType = null;
        boolean currentFinal = false;
        String currentName;
        String currentValue = null;
        for (String arg : splitLine) {
            arg = Utils.RemoveAllSpacesAtEnd(arg);
            System.out.println("Arg: " + arg);
            String[] splitArgs = arg.split(BLANK_SPACE);
            if (splitArgs.length == 0) {
                continue;
            }

            if (splitArgs[0].equals("final")) {
                currentFinal = true;
                splitArgs = Arrays.copyOfRange(splitArgs, 1, splitArgs.length);
            }

            if (splitArgs.length == 0) {
                throw new BadFormatException("b");
            }

            if (PropertyFactory.getInstance().isPropertyType(splitArgs[0])) {
                if(currentType != null) {
                    throw new BadFormatException("Type already declared");
                }
                currentType = splitArgs[0];
                splitArgs = Arrays.copyOfRange(splitArgs, 1, splitArgs.length);
            } else {
                if (currentFinal || currentType == null ||
                        this.propertyExistWithType(currentType, splitArgs[0])) {
                    throw new BadFormatException("b");
                }
            }

            String joinedArgs = String.join(EMPTY_STRING, Arrays.asList(splitArgs));
            splitArgs = joinedArgs.split(EQUALS);
            if (splitArgs.length > 2 || splitArgs.length == 0) {
                throw new BadFormatException("n");
            } else {
                currentName = splitArgs[0];
                if (splitArgs.length > 1) {
                    currentValue = splitArgs[1];
                }
            }

            HashMap<String, Property> newProperties = new HashMap<>();
            if (result.containsKey(currentType)) {
                if (result.containsKey(currentName)) {
                    throw new BadFormatException("b");
                }
                newProperties.putAll(result.get(currentType));
            }
            if (currentFinal && currentValue == null) {
                throw new BadFormatException("Final property must contain value");
            }
            if (currentValue != null && currentValue.endsWith(";")) {
                currentValue = currentValue.substring(0, currentValue.length() - 1);
            }

            if (propertyExist(currentValue)) {
                boolean changed = false;
                ArrayList<String> valueTypes = GetPropertyTypeOptions(currentValue);
                if (valueTypes.contains(currentType)) {
                    currentValue = PropertyFactory.getInstance().getValueFromProperty(
                            this.properties.get(currentType).get(currentValue));
                    changed = true;
                } else {
                    for (String option : valueTypes) {
                        if (PropertyFactory.getInstance().validTypesTo(currentType, option)) {
                            currentValue = PropertyFactory.getInstance().getValueFromProperty(
                                    this.properties.get(option).get(currentValue));
                            changed = true;
                            break;
                        }
                    }
                }
                if (currentValue == null) {
                    throw new BadFormatException("Null parameter");
                }
                if (!changed) {
                    throw new BadFormatException("Invalid parameter");
                }
            }
            newProperties.put(currentName,
                    PropertyFactory.getInstance().createProperty(currentType,
                            currentName, currentValue, currentFinal));
            result.put(currentType, newProperties);
        }
        return result;
    }

    /**
     * TODO
     * @param name
     * @param type
     * @param newProperty
     */
    protected void replaceProperty(String name, String type, Property newProperty) {
        HashMap<String, Property> newHash = this.properties.get(type);
        newHash.put(name, newProperty);
        this.properties.put(type, newHash);
    }

    /**
     * TODO
     * @param name
     * @param type
     * @param value
     * @throws BadFormatException
     */
    protected void replaceProperty(String name, String type, String value) throws BadFormatException {
        Property newProperty = PropertyFactory.getInstance().updatePropertyFromString(
                this.properties.get(type).get(name), value);
        replaceProperty(name, type, newProperty);
    }

    /**
     * TODO
     * @param line
     * @return
     */
    protected boolean isPropertyLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            return PropertyFactory.getInstance().isPropertyType(splitLine[0]) || splitLine[0].equals(FINAL_CONSTANT);
        }
        return false;
    }

    /**
     * Verifies if the given code line is a while loop line
     * @param line the given code line to verify
     * @return true if the code line is a start of a while loop else false
     */
    protected boolean isWhileLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            return splitLine[0].equals(WHILE_CONSTANT);
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
            return splitLine[0].equals(If_CONSTANT);
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
     * TODO
     * @param name
     * @param value
     * @throws BadFormatException
     */
    protected void updatePropertyValue(String name, String value) throws BadFormatException {
        for (String type : this.properties.keySet()) {
            if (this.properties.get(type).containsKey(name)) {
                if (PropertyFactory.getInstance().validValue(type, value)) {
                    this.replaceProperty(name, type, value);
                    return;
                }
            }
        }
    }

    /**
     * Sets an existing property value to the value of another existing property according to the properties
     * names
     * @param nameToUpdate the property to set the value in
     * @param nameFromUpdate the property to assign the value from
     * @throws BadFormatException TODO
     */
    protected void updatePropertyValueByPropertyName(String nameToUpdate, String nameFromUpdate) throws BadFormatException {
        ArrayList<String> typesToUpdate = this.GetPropertyTypeOptions(nameToUpdate);
        ArrayList<String> typesFromUpdate = this.GetPropertyTypeOptions(nameFromUpdate);
        for (String type : typesFromUpdate) {
            if (typesFromUpdate.contains(type)) {
                Property newProperty = PropertyFactory.getInstance().updatePropertyFromOtherProperty(
                        this.properties.get(type).get(nameToUpdate),
                        this.properties.get(type).get(nameFromUpdate));
                this.replaceProperty(nameToUpdate, type, newProperty);
                return;
            }
        }

        for (String typeTo : typesToUpdate) {
            for (String typeFrom : typesFromUpdate) {
                if (PropertyFactory.getInstance().validTypesTo(typeTo, typeFrom)) {
                    Property newProperty = PropertyFactory.getInstance().updatePropertyFromOtherProperty(
                            this.properties.get(typeTo).get(nameToUpdate),
                            this.properties.get(typeFrom).get(nameFromUpdate));
                    this.replaceProperty(nameToUpdate, typeTo, newProperty);
                }
            }
        }
    }

    /**
     * TODO
     * @param line
     * @throws BadFormatException
     */
    protected void assignProperty(String line) throws BadFormatException {
        int firstEqualIndex = line.indexOf(EQUALS);
        if (firstEqualIndex == -1 || firstEqualIndex == line.length() - 1) {
            throw new BadFormatException("Bad assignment");
        }

        String propertyName = Utils.RemoveAllSpacesAtEnd(line.substring(0, firstEqualIndex));
        String propertyValue = Utils.RemoveAllSpacesAtEnd(line.substring(firstEqualIndex + 1));

        if (!this.propertyExist(propertyName)) {
            throw new BadFormatException("Property is not declared");
        }

        if (this.propertyExist(propertyValue)) {
            this.updatePropertyValueByPropertyName(propertyName, propertyValue);
        } else {
            this.updatePropertyValue(propertyName, propertyValue);
        }
    }
}
