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
    private final String VOID_CONSTANT = "void";
    private final String WHILE_CONSTANT = "while";
    private final String If_CONSTANT = "if";
    private final String FINAL_CONSTANT = "final";
    private final String EMPTY_STRING = "";
    private final String BLANK_SPACE = " ";

    /**
     *
     */
    private Parser() {

    }

    /**
     *
     * @return
     */
    public static Parser getInstance() {
        return parser;
    }

    /**
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    private ArrayList<String> fileToArrayList(String fileName) throws IOException {
        ArrayList<String> result = new ArrayList<String>();

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        while (br.ready()) {
            result.add(br.readLine());
        }

        return result;
    }

    /**
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public int ParseFile(String fileName) throws IOException {
        try {
            //Get file array list
            ArrayList<String> fileList = fileToArrayList(fileName);

            for (String line : fileList) {
                if (this.isEmptyLine(line) || this.isCommentLine(line)) {
                    continue;
                }

                Validations.getValidations().hasCodeSuffix(line);

                if (this.isPropertyLine(line)) {
                    addProperties(getPropertyFromLine(line));
                }

                if (this.isMethodLine(line)) {

                }

                if (ifUpdatePropertyLine(line)) {
                    String noneBlankLine = line.replace(BLANK_SPACE, EMPTY_STRING);
                    String[] splitLine = noneBlankLine.split("=");
                    if (splitLine.length != 2) {
                        throw new BadFormatException("Bad initialize of parameter");
                    }
                    if (this.getValueType(splitLine[1]).equals(EMPTY_STRING)) {
                        if (GetPropertyTypeOptions(splitLine[1]).size() > 0) {
                            String type = getSameType(splitLine[0], splitLine[1]);
                            if (type.equals(EMPTY_STRING)) {
                                throw new BadFormatException("Bad Parameter");
                            } else {

                            }
                        }
                    }
                }
            }
        } catch (BadFormatException exp) {
            System.err.println(exp.getMessage());
        }

        return 0;
    }

    private void updateParam(String type, String name, String value) {
        HashMap<String, Property> newPropertyType = this.properties.get(type);
        Property newProperty = newPropertyType.get(name);

    }

    private String getSameType(String name1, String name2) {
        ArrayList<String> options1 = this.GetPropertyTypeOptions(name1);
        ArrayList<String> options2 = this.GetPropertyTypeOptions(name2);
        for (String option : options1) {
            if (options2.contains(option)) {
                return option;
            }
        }
        return EMPTY_STRING;
    }

    private boolean isPropertyType(String str) {
        return str.equals("double") ||
                str.equals("boolean") ||
                str.equals("int") ||
                str.equals("String") ||
                str.equals("char");
    }

    public boolean isPropertyLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            return isPropertyType(splitLine[0]) || splitLine[0].equals(FINAL_CONSTANT);
        }
        return false;
    }

    public boolean isMethodLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            return splitLine[0].equals(VOID_CONSTANT);
        }
        return false;
    }

    public boolean isWhileLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            return splitLine[0].equals(WHILE_CONSTANT);
        }
        return false;
    }

    public boolean isIfLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            return splitLine[0].equals(If_CONSTANT);
        }
        return false;
    }

    public String getValueType(String value) {
        if (Validations.getValidations().isDouble(value)) {
            return DOUBLE_TYPE;
        } else if (Validations.getValidations().isInteger(value)) {
            return INT_TYPE;
        } else if (Validations.getValidations().isChar(value)) {
            return CHAR_TYPE;
        } else if (Validations.getValidations().isBoolean(value)) {
            return BOOLEAN_TYPE;
        } else if (Validations.getValidations().isString(value)) {
            return STRING_TYPE;
        }
        return EMPTY_STRING;
    }

    public ArrayList<String> GetPropertyTypeOptions(String name) {
        ArrayList<String> options = new ArrayList<>();
        for (String type : this.properties.keySet()) {
            if (this.properties.get(type).containsKey(name)) {
                options.add(type);
            }
        }
        return options;
    }

    public boolean ifUpdatePropertyLine(String line) {
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

    public boolean isCommentLine(String line) {
        return line.startsWith("// ");
    }

    public boolean isEmptyLine(String line) {
        return line.isBlank() || line.isEmpty();
    }

    private void addProperties(HashMap<String, HashMap<String, Property>> newProperties) throws BadFormatException {
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
            if (propertiesKeySet.contains(name)) {
                return true;
            }
        }
        return false;
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

    public HashMap<String, HashMap<String, Property>> getPropertyFromLine(String line) throws BadFormatException {
        HashMap<String, HashMap<String, Property>> result = new HashMap<>();
        String[] splitLine = line.split(",");
        String currentType = null;
        boolean currentFinal = false;
        String currentName;
        String currentValue = null;
        for (int i = 0; i < splitLine.length; i++) {
            String arg = splitLine[i];
            String currentArg = arg;
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

            HashMap<String, Property> newProperties = new HashMap<>();
            if (result.containsKey(currentType)) {
                if (result.containsKey(currentName)) {
                    throw new BadFormatException("b");
                }
                newProperties.putAll(result.get(currentType));
            }
            newProperties.put(currentName, createProperty(currentType, currentName, currentValue, currentFinal));
            result.put(currentType, newProperties);
        }
        return result;
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

}