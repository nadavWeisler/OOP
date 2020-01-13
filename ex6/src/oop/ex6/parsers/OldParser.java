package oop.ex6.parsers;

import oop.ex6.Utils;
import oop.ex6.code.properties.Property;
import oop.ex6.code.properties.PropertyFactory;
import oop.ex6.exceptions.BadFormatException;

import java.io.File;
import java.io.FileFilter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Super class that contains different method to use when parsing the text file into data sections
 */
public abstract class OldParser {

    // Creating finals for the parsing usage
    protected final String FINAL_CONSTANT = "final";
    protected final String EMPTY_STRING = "";
    protected final String BLANK_SPACE = "\\s";
    protected final String COMMA = ",";
    protected final String EQUALS = "=";
    protected final String WHILE_CONSTANT = "while";
    protected final String If_CONSTANT = "if";


    // Saves all the file variables
    protected HashMap<String, HashMap<String, Property>> local_properties = new HashMap<>();



    /**
     * TODO
     *
     * @param name
     * @return
     */
    protected ArrayList<String> GetPropertyTypeOptions(String name,
                                                       HashMap<String, HashMap<String, Property>> currentProperties) {
        ArrayList<String> options = new ArrayList<>();
        for (String type : currentProperties.keySet()) {
            if (currentProperties.get(type).containsKey(name)) {
                options.add(type);
            }
        }
        return options;
    }

    /**
     * TODO
     *
     * @param line
     * @return
     */
    protected boolean ifAssignPropertyLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            for (String type : this.local_properties.keySet()) {
                if (this.local_properties.get(type).containsKey(splitLine[0]) ||
                        local_properties.get(type).containsKey(splitLine[0])) {
                    return true;
                }
            }
//            for (String type : FileParser.global_properties.keySet()) {
//                if (FileParser.global_properties.get(type).containsKey(splitLine[0]) ||
//                        FileParser.global_properties.get(type).containsKey(splitLine[0])) {
//                    return true;
//                }
//            }
        }
        return false;
    }

    /**
     * TODO
     *
     * @param newProperties
     * @throws BadFormatException
     */
    protected void addProperties(HashMap<String, HashMap<String, Property>> newProperties) throws BadFormatException {
        for (String type : newProperties.keySet()) {
            for (String currentProperty : newProperties.get(type).keySet()) {
                if (propertyExist(currentProperty)) {
                    throw new BadFormatException("Already contain this key!");
                }
            }
            this.local_properties.put(type, newProperties.get(type));
        }
    }


    public boolean globalPropertyExistWithType(String propertyType, String name) {
//        if (FileParser.global_properties.containsKey(propertyType)) {
//            Set<String> propertiesKeySet = FileParser.global_properties.get(propertyType).keySet();
//            for (String p : propertiesKeySet) {
//                System.out.println(p);
//            }
//            return propertiesKeySet.contains(name);
//        }

        return false;
    }

    public boolean propertyExistWithSomeTypes(String[] types, String name) {
        for (String type : types) {
            if (propertyExistWithType(type, name)) {
                return true;
            }
        }
        return false;
    }

    public boolean globalPropertyExistWithSomeTypes(String[] types, String name) {
        for (String type : types) {
            if (globalPropertyExistWithType(type, name)) {
                return true;
            }
        }
        return false;
    }

    public boolean propertyExistWithType(String propertyType, String name) {
        System.out.println(propertyType + "-" + name);
        if (this.local_properties.containsKey(propertyType)) {
            Set<String> propertiesKeySet = this.local_properties.get(propertyType).keySet();
            return propertiesKeySet.contains(name);
        }
        return false;
    }

    /**
     * Verifies if the given property exist according to a given property name
     *
     * @param name the given property name to verify
     * @return true if the property exist else false
     */
    public boolean propertyExist(String name) {
        for (String type : this.local_properties.keySet()) {
            if (this.local_properties.get(type).containsKey(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean propertyExistGlobal(String name) {
//        for (String type : FileParser.global_properties.keySet()) {
//            if (FileParser.global_properties.get(type).containsKey(name)) {
//                return true;
//            }
//        }
        return false;
    }

    /**
     * TODO
     *
     * @param line
     * @return
     * @throws BadFormatException
     */
    protected HashMap<String, HashMap<String, Property>> getPropertyFromLine(String line)
            throws BadFormatException {
        HashMap<String, HashMap<String, Property>> result = new HashMap<>();
        line = line.substring(0, line.length() - 1);
        String[] splitLine = line.split(COMMA);
        if(splitLine.length < 2 && !line.contains(EQUALS)) {
            throw new BadFormatException("Bad property line");
        }
        String currentType = null;
        boolean currentFinal = false;
        String currentName;
        String currentValue = null;
        for (String arg : splitLine) {
            arg = Utils.RemoveAllSpacesAtEnd(arg);
            System.out.println("Arg: " + arg);
            String[] splitArgs = arg.split(BLANK_SPACE);
            List<String> cleanArgs = Utils.cleanWhiteSpace(splitArgs);
            System.out.println(cleanArgs.size());
            if (cleanArgs.size() == 0) {
                continue;
            }

            if (cleanArgs.get(0).equals("final")) {
                currentFinal = true;
                cleanArgs = (ArrayList<String>) cleanArgs.subList(1, cleanArgs.size());
            }

            if (cleanArgs.size() == 0) {
                throw new BadFormatException("b");
            }

            if (PropertyFactory.getInstance().isPropertyType(cleanArgs.get(0))) {
                if (currentType != null) {
                    throw new BadFormatException("Type already declared");
                }
                currentType = cleanArgs.get(0);
                cleanArgs = cleanArgs.subList(1, cleanArgs.size());
            } else {
                if (currentFinal || currentType == null ||
                        this.propertyExistWithType(currentType, cleanArgs.get(0))) {
                    throw new BadFormatException("b");
                }
            }

            String joinedArgs = String.join(" ", cleanArgs);

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
                currentValue = getCurrentValueFromProperty(currentValue, currentType, this.local_properties);
            } else if (propertyExistGlobal(currentValue)) {
//                currentValue = getCurrentValueFromProperty(currentValue, currentType, FileParser.global_properties);
            }

            if (this.propertyExist(currentName)) {
                throw new BadFormatException("Property already exist");
            }
            if (currentValue == null) {
                newProperties.put(currentName,
                        PropertyFactory.getInstance().createMethodProperty(currentType,
                                currentName, currentFinal));
            } else {
                newProperties.put(currentName,
                        PropertyFactory.getInstance().createProperty(currentType,
                                currentName, currentValue, currentFinal));
            }
            result.put(currentType, newProperties);
        }
        Utils.printProperties(result);
        return result;
    }

    private String getCurrentValueFromProperty(String currentValue, String currentType,
                                               HashMap<String, HashMap<String, Property>> currentProperties)
            throws BadFormatException {
        boolean changed = false;
        ArrayList<String> valueTypes = GetPropertyTypeOptions(currentValue, currentProperties);
        if (valueTypes.contains(currentType)) {
            if (currentProperties.get(currentType).get(currentValue).isMethodProperty()) {
                return null;
            }
            currentValue = PropertyFactory.getInstance().getValueFromProperty(
                    currentProperties.get(currentType).get(currentValue));
            changed = true;
        } else {
            for (String option : valueTypes) {
                if (PropertyFactory.getInstance().validTypesTo(currentType, option)) {
                    if (currentProperties.get(option).get(currentValue).isMethodProperty()) {
                        return null;
                    }
                    currentValue = PropertyFactory.getInstance().getValueFromProperty(
                            currentProperties.get(option).get(currentValue));
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
        return currentValue;
    }

    /**
     * TODO
     *
     * @param name
     * @param type
     * @param newProperty
     */
    protected void replaceProperty(String name, String type, Property newProperty) throws BadFormatException {
        if (newProperty.isFinal()) {
            throw new BadFormatException("Assign to final property");
        }
        HashMap<String, Property> newHash = this.local_properties.get(type);
        newHash.put(name, newProperty);
        this.local_properties.put(type, newHash);
    }

    protected void replacePropertyGlobal(String name, String type, Property newProperty) throws BadFormatException {
        if (newProperty.isFinal()) {
            throw new BadFormatException("Assign to final property");
        }
//        HashMap<String, Property> newHash = FileParser.global_properties.get(type);
//        newHash.put(name, newProperty);
//        FileParser.global_properties.put(type, newHash);
    }

    /**
     * TODO
     *
     * @param name
     * @param type
     * @param value
     * @throws BadFormatException
     */
    protected void replaceProperty(String name, String type, String value) throws BadFormatException {
        Property newProperty = PropertyFactory.getInstance().updatePropertyFromString(
                this.local_properties.get(type).get(name), value);
        if (newProperty.isFinal()) {
            throw new BadFormatException("Assign to final property");
        }
        replaceProperty(name, type, newProperty);
    }

    protected void replacePropertyGlobal(String name, String type, String value) throws BadFormatException {
        Property newProperty = PropertyFactory.getInstance().updatePropertyFromString(
                this.local_properties.get(type).get(name), value);
        if (newProperty.isFinal()) {
            throw new BadFormatException("Assign to final property");
        }
        replaceProperty(name, type, newProperty);
    }

    /**
     * TODO
     *
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
     *
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
     *
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
     *
     * @param line the given line to verify
     * @return true if the line end with the bracket '}' , else false
     */
    protected boolean isEnd(String line) {
        return line.replace(" ", "").equals("}");
    }

    /**
     * TODO
     *
     * @param name
     * @param value
     * @throws BadFormatException
     */
    protected void updatePropertyValue(String name, String value)
            throws BadFormatException {
        for (String type : this.local_properties.keySet()) {
            if (this.local_properties.get(type).containsKey(name)) {
                if (this.local_properties.get(type).get(name).isFinal()) {
                    throw new BadFormatException("Assign final property");
                }
                if (PropertyFactory.getInstance().validValue(type, value)) {
                    this.replaceProperty(name, type, value);
                    return;
                }
            }
        }

//        for (String type : FileParser.global_properties.keySet()) {
//            if (FileParser.global_properties.get(type).containsKey(name)) {
//                if (FileParser.global_properties.get(type).get(name).isFinal()) {
//                    throw new BadFormatException("Assign final property");
//                }
//                if (PropertyFactory.getInstance().validValue(type, value)) {
//                    this.replacePropertyGlobal(name, type, value);
//                    return;
//                }
//            }
//        }


    }

    /**
     * Sets an existing property value to the value of another existing property according to the properties
     * names
     *
     * @param nameToUpdate   the property to set the value in
     * @param nameFromUpdate the property to assign the value from
     * @throws BadFormatException TODO
     */
    protected void updatePropertyValueByPropertyName(String nameToUpdate, String nameFromUpdate)
            throws BadFormatException {
        ArrayList<String> typesToUpdate = this.GetPropertyTypeOptions(nameToUpdate, this.local_properties);
        ArrayList<String> typesFromUpdate = this.GetPropertyTypeOptions(nameFromUpdate, this.local_properties);
        for (String type : typesFromUpdate) {
            if (typesToUpdate.contains(type)) {
                Property newProperty = PropertyFactory.getInstance().updatePropertyFromOtherProperty(
                        this.local_properties.get(type).get(nameToUpdate),
                        this.local_properties.get(type).get(nameFromUpdate));
                if (newProperty.isFinal()) {
                    throw new BadFormatException("Assign final property");
                }
                this.replaceProperty(nameToUpdate, type, newProperty);
                return;
            }
        }

        for (String typeTo : typesToUpdate) {
            for (String typeFrom : typesFromUpdate) {
                if (PropertyFactory.getInstance().validTypesTo(typeTo, typeFrom)) {
                    Property newProperty = PropertyFactory.getInstance().updatePropertyFromOtherProperty(
                            this.local_properties.get(typeTo).get(nameToUpdate),
                            this.local_properties.get(typeFrom).get(nameFromUpdate));
                    if (newProperty.isFinal()) {
                        throw new BadFormatException("Assign final property");
                    }
                    this.replaceProperty(nameToUpdate, typeTo, newProperty);
                }
            }
        }
    }

    protected void updatePropertyValueByPropertyNameGlobal(String nameToUpdate, String nameFromUpdate)
            throws BadFormatException {
//        ArrayList<String> typesToUpdate = this.GetPropertyTypeOptions(nameToUpdate, FileParser.global_properties);
//        ArrayList<String> typesFromUpdate = this.GetPropertyTypeOptions(nameFromUpdate, FileParser.global_properties);
//        for (String type : typesFromUpdate) {
//            if (typesToUpdate.contains(type)) {
//                Property newProperty = PropertyFactory.getInstance().updatePropertyFromOtherProperty(
//                        FileParser.global_properties.get(type).get(nameToUpdate),
//                        FileParser.global_properties.get(type).get(nameFromUpdate));
//                if (newProperty.isFinal()) {
//                    throw new BadFormatException("Assign final property");
//                }
//                this.replaceProperty(nameToUpdate, type, newProperty);
//                return;
//            }
//        }
    }

    /**
     * TODO
     *
     * @param line
     * @throws BadFormatException
     */
    protected void assignProperty(String line)
            throws BadFormatException {
        int firstEqualIndex = line.indexOf(EQUALS);
        if (firstEqualIndex == -1 || firstEqualIndex == line.length() - 1) {
            throw new BadFormatException("Bad assignment");
        }

        String propertyName = Utils.RemoveAllSpacesAtEnd(line.substring(0, firstEqualIndex));
        String propertyValue = Utils.RemoveAllSpacesAtEnd(line.substring(firstEqualIndex + 1));

        if (!this.propertyExist(propertyName) && !this.propertyExistGlobal(propertyName)) {
            throw new BadFormatException("Property is not declared");
        }

        if (this.propertyExist(propertyValue)) {
            this.updatePropertyValueByPropertyName(propertyName, propertyValue);
        } else if (this.propertyExistGlobal(propertyValue)) {
            this.updatePropertyValueByPropertyNameGlobal(propertyName, propertyValue);
        } else {
            this.updatePropertyValue(propertyName, propertyValue);
        }
    }
}
