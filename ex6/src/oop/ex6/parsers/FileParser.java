package oop.ex6.parsers;

import oop.ex6.Utils;
import oop.ex6.code.Method;
import oop.ex6.code.properties.Property;
import oop.ex6.code.properties.PropertyFactory;
import oop.ex6.exceptions.BadFormatException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Extends Parser and parse the text file that contains code lines, uses the singleton design pattern
 */
public class FileParser extends Parser {

    protected final Pattern COMMENT_PATTERN = Pattern.compile("//.*");

    protected HashMap<String, HashMap<String, Property>> global_properties = new HashMap<>();

    private static FileParser parser = new FileParser();
    private HashMap<String, Method> methods = new HashMap<>();

    /**
     * Constructor of FileParser
     */
    private FileParser() {

        // Initiates the properties type in the has map
        this.global_properties.put("int", new HashMap<>());
        this.global_properties.put("double", new HashMap<>());
        this.global_properties.put("String", new HashMap<>());
        this.global_properties.put("char", new HashMap<>());
        this.global_properties.put("boolean", new HashMap<>());
    }

    /**
     * Returns FileParser single instance - singleton desigm pattern
     * @return FileParser instance
     */
    public static FileParser getInstance() {
        return parser;
    }

    /**
     * Verifies if the code line is a comment line
     * @param line the given line to verify
     * @return true if the code line is comment line, else false
     */
    protected boolean isComment(String line) {
        return COMMENT_PATTERN.matcher(line).matches();
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
     * Verifies if the code line is a method deceleration line
     * @param line the given line to verify
     * @return true if the code line is a method deceleration line, else false
     */
    public boolean isMethodLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            return splitLine[0].equals(VOID_CONSTANT);
        }
        return false;
    }

    /**
     * Verifies if the given method already exist in the program
     * @param method the given method to verify
     * @return true if the method exist, else false
     */
    private boolean methodExist(Method method) {
        return this.methods.containsKey(method.getMethodName());
    }

    /**
     * Parse the file into data section and verifies its validity according to the S-java definition
     * @param fileName the given file to verify
     * @throws IOException when the file is not found
     * @throws BadFormatException  when the file code lines are invalid
     */
    public void ParseFile(String fileName) throws IOException, BadFormatException {
        boolean insideMethod = false;
        ArrayList<Integer> conditionSwitch = new ArrayList<>();
        ArrayList<Integer> whileSwitch = new ArrayList<>();
        int switchCount = 0;
        Method newMethod;
        ArrayList<String> methodList = new ArrayList<>();
        ArrayList<ArrayList<String>> methodParsers = new ArrayList<>();
        //Get file array list
        ArrayList<String> fileList = Utils.fileToArrayList(fileName);
        int lineCount = -1;
        for (String line : fileList) {
            lineCount++;
            line = Utils.RemoveAllSpacesAtEnd(line);
            //Empty or Comment line
            if (this.isEmpty(line) || this.isComment(line)) {
                continue;
            }

            //The line has a valid suffix
            Utils.hasCodeSuffix(line);

            //Inside a method
            if (insideMethod) {
                methodList.add(line);
                if (this.isIfLine(line)) {
                    conditionSwitch.add(switchCount);
                    switchCount++;
                } else if (this.isWhileLine(line)) {
                    whileSwitch.add(switchCount);
                    switchCount++;
                } else if (this.isEnd(line)) {
                    if (conditionSwitch.isEmpty()) {
                        if (whileSwitch.isEmpty()) {
                            insideMethod = false;
                            methodParsers.add(methodList);
                            methodList = new ArrayList<>();
                        } else {
                            whileSwitch.remove(whileSwitch.size() - 1);
                        }
                    } else {
                        if (whileSwitch.isEmpty()) {
                            conditionSwitch.remove(conditionSwitch.size() - 1);
                        } else {
                            int lastCondition = conditionSwitch.get(conditionSwitch.size() - 1);
                            int lastWhile = whileSwitch.get(whileSwitch.size() - 1);
                            if (lastCondition > lastWhile) {
                                conditionSwitch.remove(conditionSwitch.size() - 1);
                            } else {
                                whileSwitch.remove(whileSwitch.size() - 1);
                            }
                        }
                    }
                }
            } else if (isPropertyLine(line)) {
                ArrayList<HashMap<String, HashMap<String, Property>>> arr = new ArrayList<>();
                arr.add(global_properties);
                ArrayList<Property> newProperties = this.getPropertiesFromLine(line, arr);
                for (Property property : newProperties) {
                    String currentType = property.getType();
                    if (this.global_properties.get(currentType).containsKey(property.getName())) {
                        throw new BadFormatException(ILLEGAL_CODE);
                    } else {
                        this.global_properties.get(currentType).put(property.getName(), property);
                    }
                }
            } else if (isMethodLine(line)) {
                methodList.add(line);
                insideMethod = true;
            } else if (ifAssignGlobalPropertyLine(line)) {
                AssignValueToGlobalProperties(line);
            } else {
                if (!(this.isEnd(line) && lineCount == fileList.size() - 1)) {
                    throw new BadFormatException(ILLEGAL_CODE);
                }
            }
        }
        //If file ended without close the method
        if (insideMethod) {
            throw new BadFormatException(ILLEGAL_CODE);
        }

        HashMap<String, Method> existingMethods = new HashMap<>();
        for (ArrayList<String> methodParser : methodParsers) {
            newMethod = MethodParser.getInstance().parseMethodLine(methodParser);
            if (this.methodExist(newMethod)) {
                throw new BadFormatException(ILLEGAL_CODE);
            } else {
                existingMethods.put(newMethod.getMethodName(), newMethod);
            }
        }

        for (ArrayList<String> methodParser : methodParsers) {
            MethodParser.getInstance().parseMethod(methodParser, existingMethods);
        }
    }
}
