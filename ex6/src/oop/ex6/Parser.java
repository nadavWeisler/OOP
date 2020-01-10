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
    public static HashMap<String, ArrayList<Property>> properties;
    private ArrayList<Method> methods;
    private static Parser parser = new Parser();

    private final String STRING_TYPE = "String";
    private final String INT_TYPE = "int";

    private Parser() {
        this.properties = new ArrayList<>();
        this.methods = new ArrayList<>();
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
            for (Property property : this.properties.get(propertyType)) {
                if (property.getName().equals(name)) {
                    return true;
                }
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

    private Property getPropertyFromLine(String line) throws BadFormatException {
        String[] splitLine = line.split(",");
        String currentType = null;
        boolean currentFinal = false;
        String currentName;
        String currentValue;
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
        }

    }

    private Property createProperty(String propertyType, String propertyName,
                                    String propertyValue, boolean isFinal) throws BadFormatException {
        if(!isPropertyType(propertyType)) {
            throw new BadFormatException("b");
        }
        switch (propertyType) {
            case "String"
        }
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
