package oop.ex6;

import oop.ex6.code.*;
import oop.ex6.code.properties.*;
import oop.ex6.exceptions.BadFormatException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class Parser {
    private HashMap<String, HashMap<String, Property>> properties = new HashMap<>();
    private ArrayList<Method> methods;
    private static Parser parser = new Parser();

    private final String STRING_TYPE = "String";
    private final String INT_TYPE = "int";
    private final String DOUBLE_TYPE = "double";
    private final String CHAR_TYPE = "char";
    private final String BOOLEAN_TYPE = "boolean";

    private Parser() {

    }

    public static Parser getInstance() {
        return parser;
    }

    private ArrayList<String> fileToArrayList(String fileName) throws IOException {
        ArrayList<String> result = new ArrayList<String>();

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        while (br.ready()) {
            result.add(br.readLine());
        }

        return result;
    }

    public int ParseFile(String fileName) throws IOException {
        try {
            ArrayList<String> fileList = fileToArrayList(fileName);

            for (String line : fileList) {
                if (line.isEmpty() || line.isBlank()) {
                    continue;
                }

                if (Validations.getValidations().isComment(line)) {
                    continue;
                }

                Validations.getValidations().hasCodeSuffix(line);

                if(line.startsWith("void ")) {
                    getPropertyFromLine(line.substring(5));
                }


            }
        } catch (BadFormatException exp) {
            System.err.println(exp.getMessage());
        }

        return 0;
    }

    private boolean isPropertyType(String str) {
        return str.equals("double") ||
                str.equals("boolean") ||
                str.equals("int") ||
                str.equals("String") ||
                str.equals("char");
    }

    private boolean propertyExist(String propertyType, String name) {
        if (this.properties.containsKey(propertyType)) {
            Set<String> propertiesKeySet = this.properties.keySet();
            if (propertiesKeySet.contains(name)) {
                return true;
            }
        }
        return false;
    }

    private void addProperty(String propertyType, Property property) {
        ArrayList<Property> newProperties = new ArrayList<>();
        if (this.properties.containsKey(propertyType)) {
            newProperties.addAll(this.properties.get(propertyType));
        }
        newProperties.add(property);
        this.properties.put(propertyType, newProperties);
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

    private void getPropertyFromLine(String line) throws BadFormatException {
        String[] splitLine = line.split(",");
        String currentType = null;
        boolean currentFinal = false;
        String currentName;
        String currentValue = null;
        for (int i = 0; i < splitLine.length; i++) {
            String arg = splitLine[i];
            String currentArg = arg;
            String[] splitArgs = arg.split(" ");
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

            if (this.isPropertyType(splitArgs[0])) {
                currentType = splitArgs[0];
                splitArgs = Arrays.copyOfRange(splitArgs, 1, splitArgs.length);
            } else {
                if (currentFinal || currentType == null || !validParameterName(splitArgs[0]) ||
                        this.propertyExist(currentType, splitArgs[0])) {
                    throw new BadFormatException("b");
                }
            }

            String joinedArgs = String.join(",", Arrays.asList(splitArgs));
            splitArgs = joinedArgs.split("=");

            if (splitArgs.length > 2 || splitArgs.length == 0) {
                throw new BadFormatException("n");
            } else {
                currentName = splitArgs[0];
                if (splitArgs.length > 1) {
                    currentValue = splitArgs[1];
                }
            }

            addProperty(currentType,
                    createProperty(currentType, currentName, currentValue, currentFinal));
        }
    }

    public boolean validValue(String type, String value) {
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

    private Property createProperty(String propertyType, String propertyName,
                                    String propertyValue, boolean isFinal) throws BadFormatException {
        if (!isPropertyType(propertyType) ||
                !validParameterName(propertyName) || !validValue(propertyType, propertyValue)) {
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

    private void AddPropertyByType(String prevType, boolean prevFinal, String propertyName) {
        switch (prevType) {
            case "String":
                this.addProperty(prevType,
                        new StringProperty(propertyName, prevType, prevFinal, false, null));
                break;
            case "int":
                this.addProperty(prevType,
                        new IntProperty(propertyName, prevType, prevFinal, false, null));
                break;
            case "double":
                this.addProperty(prevType,
                        new DoubleProperty(propertyName, prevType, prevFinal, false, null));
                break;
            case "boolean":
                this.addProperty(prevType,
                        new BooleanProperty(propertyName, prevType, prevFinal, false, null));
                break;
            case "char":
                this.addProperty(prevType,
                        new CharProperty(propertyName, prevType, prevFinal, false, null));
                break;
        }
    }
}
