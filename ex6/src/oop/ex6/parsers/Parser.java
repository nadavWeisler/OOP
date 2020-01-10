package oop.ex6.parsers;

import oop.ex6.code.properties.Property;
import oop.ex6.code.properties.PropertyFactory;
import oop.ex6.exceptions.BadFormatException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public abstract class Parser {
    protected final String FINAL_CONSTANT = "final";
    protected final String EMPTY_STRING = "";
    protected final String BLANK_SPACE = " ";
    protected final String COMMA = ",";
    protected final String EQUALS = "=";
    protected final String WHILE_CONSTANT = "while";
    protected final String If_CONSTANT = "if";

    protected HashMap<String, HashMap<String, Property>> properties = new HashMap<>();

    protected boolean isCommentLine(String line) {
        return line.startsWith("// ");
    }

    protected boolean isEmptyLine(String line) {
        return line.isBlank() || line.isEmpty();
    }

    protected ArrayList<String> GetPropertyTypeOptions(String name) {
        ArrayList<String> options = new ArrayList<>();
        for (String type : this.properties.keySet()) {
            if (this.properties.get(type).containsKey(name)) {
                options.add(type);
            }
        }
        return options;
    }

    protected boolean ifUpdatePropertyLine(String line) {
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

    public boolean propertyExist(String propertyType, String name) {
        if (this.properties.containsKey(propertyType)) {
            Set<String> propertiesKeySet = this.properties.keySet();
            return propertiesKeySet.contains(name);
        }
        return false;
    }

    protected HashMap<String, HashMap<String, Property>> getPropertyFromLine(String line) throws BadFormatException {
        HashMap<String, HashMap<String, Property>> result = new HashMap<>();
        String[] splitLine = line.split(COMMA);
        String currentType = null;
        boolean currentFinal = false;
        String currentName;
        String currentValue = null;
        for (String arg : splitLine) {
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
                currentType = splitArgs[0];
                splitArgs = Arrays.copyOfRange(splitArgs, 1, splitArgs.length);
            } else {
                if (currentFinal || currentType == null ||
                        this.propertyExist(currentType, splitArgs[0])) {
                    throw new BadFormatException("b");
                }
            }

            String joinedArgs = String.join(COMMA, Arrays.asList(splitArgs));
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
            newProperties.put(currentName,
                    PropertyFactory.getInstance().createProperty(currentType,
                            currentName, currentValue, currentFinal));
            result.put(currentType, newProperties);
        }
        return result;
    }

    protected void replaceProperty(String name, String type, Property newProperty) {
        HashMap<String, Property> newHash = this.properties.get(type);
        newHash.put(name, newProperty);
        this.properties.put(type, newHash);
    }

    protected String getSameType(String name1, String name2) {
        ArrayList<String> options1 = this.GetPropertyTypeOptions(name1);
        ArrayList<String> options2 = this.GetPropertyTypeOptions(name2);
        for (String option : options1) {
            if (options2.contains(option)) {
                return option;
            }
        }
        return EMPTY_STRING;
    }

    protected boolean isPropertyLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            return PropertyFactory.getInstance().isPropertyType(splitLine[0]) || splitLine[0].equals(FINAL_CONSTANT);
        }
        return false;
    }

    protected boolean isWhileLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            return splitLine[0].equals(WHILE_CONSTANT);
        }
        return false;
    }

    protected boolean isIfLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            return splitLine[0].equals(If_CONSTANT);
        }
        return false;
    }

    protected boolean isEnd(String line) {
        return line.replace(" ", "").equals("}");
    }
}
