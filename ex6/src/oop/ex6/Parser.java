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
    private HashMap<String, Method> methods;
    private static Parser parser = new Parser();
    private final String VOID_CONSTANT = "void";
    private final String WHILE_CONSTANT = "while";
    private final String If_CONSTANT = "if";
    private final String FINAL_CONSTANT = "final";
    private final String EMPTY_STRING = "";
    private final String BLANK_SPACE = " ";
    private final String COMMA = ",";
    private final String EQUALS = "=";


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
                    String[] splitLine = noneBlankLine.split(EQUALS);
                    if (splitLine.length != 2) {
                        throw new BadFormatException("Bad initialize of parameter");
                    }
                    if (PropertyFactory.getInstance().validValue(splitLine[1])) {
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

    public boolean isPropertyLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            return PropertyFactory.getInstance().isPropertyType(splitLine[0]) || splitLine[0].equals(FINAL_CONSTANT);
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

    private boolean propertyExist(String propertyType, String name) {
        if (this.properties.containsKey(propertyType)) {
            Set<String> propertiesKeySet = this.properties.keySet();
            if (propertiesKeySet.contains(name)) {
                return true;
            }
        }
        return false;
    }

    public HashMap<String, HashMap<String, Property>> getPropertyFromLine(String line) throws BadFormatException {
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

}