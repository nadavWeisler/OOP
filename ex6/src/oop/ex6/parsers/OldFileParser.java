package oop.ex6.parsers;

import oop.ex6.Utils;
import oop.ex6.code.Method;
import oop.ex6.code.properties.Property;
import oop.ex6.code.properties.PropertyFactory;
import oop.ex6.exceptions.BadFormatException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Extends Parser and parse the text file that contains code lines, uses the singleton design pattern
 */
public class OldFileParser extends OldParser {

    // Represent all the methods in the file
    private HashMap<String, Method> methods = new HashMap<>();

    // Creates a single instance of FileParser - singleton design
    private static OldFileParser parser = new OldFileParser();

    // Saves all the file variables
    protected static HashMap<String, HashMap<String, Property>> global_properties = new HashMap<>();


    // Creating finals for the parse process
    private final String VOID_CONSTANT = "void";
    private final String FINAL_CONSTANT = "final";
    private final String EMPTY_STRING = "";
    private final String BLANK_SPACE = " ";
    private final String COMMA = ",";
    private final String EQUALS = "=";


    /**
     * Private constructor of FileParser - singleton design pattern
     */
    private OldFileParser() {
    }

    /**
     * Returns the single instance of FileParser - singleton design pattern
     *
     * @return FileParser instance
     */
    public static OldFileParser getInstance() {
        return parser;
    }



    /**
     * Verifies if the given method already exist in the program
     *
     * @param method the given method to verify
     * @return true if the method exist, else false
     */
    private boolean methodExist(Method method) {
        return this.methods.containsKey(method.getMethodName());
    }

    /**
     * Parse the file into data section and verifies the validity of the file according to the
     * S-java definition
     *
     * @param fileName the given file to parse and verify its validity
     * @return 0 if the code is legal, else throws an exception the the main method
     * @throws IOException        when illegal file name was found
     * @throws BadFormatException if the code is illegal
     */
    public int ParseFileOld(String fileName) throws IOException, BadFormatException {
        boolean insideMethod = false;
        ArrayList<Integer> conditionSwitch = new ArrayList<>();
        ArrayList<Integer> whileSwitch = new ArrayList<>();
        int switchCount = 0;
        Method newMethod;
        ArrayList<String> methodList = new ArrayList<>();
        ArrayList<ArrayList<String>> methodParsers = new ArrayList<>();
        //Get file array list
//        ArrayList<String> fileList = fileToArrayList(fileName);
        ArrayList<String> fileList = new ArrayList<>();
        for (String line : fileList) {
            line = Utils.RemoveAllSpacesAtEnd(line);
            //Empty or Comment line
//            if (this.isEmpty(line) || this.isComment(line)) {
//                continue;
//            }

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
            } else if (this.isPropertyLine(line)) {
                this.addGlobalProperties(getPropertyFromLine(line));
            } else if (this.isMethodLine(line)) {
                methodList.add(line);
                insideMethod = true;
            } else if (ifAssignPropertyLine(line)) {
                this.assignProperty(line);
            } else {
                throw new BadFormatException("Invalid line");
            }
        }

        //If file ended without close the method
        if (insideMethod) {
            throw new BadFormatException("Method did not close");
        }

        for (ArrayList<String> methodParser : methodParsers) {
            newMethod = MethodParser.getInstance().parseMethod(methodParser);
            if (this.methodExist(newMethod)) {
                throw new BadFormatException("The method already exist");
            } else {
                this.methods.put(newMethod.getMethodName(), newMethod);

            }
        }

        return 0;
    }


    protected void addGlobalProperties(HashMap<String, HashMap<String, Property>> newProperties)
            throws BadFormatException {
        for (String type : newProperties.keySet()) {
            if (global_properties.containsKey(type)) {
                HashMap<String, Property> newTypeProperties = newProperties.get(type);
                for (String currentProperty : newTypeProperties.keySet()) {
                    if (global_properties.get(type).containsKey(currentProperty)) {
                        throw new BadFormatException("Already contain this key!");
                    }
                }
            } else {
                global_properties.put(type, newProperties.get(type));
            }
        }
    }

    /**
     * Verifies if the code line is a method deceleration line
     *
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

    @Override
    protected void updatePropertyValue(String name, String value)
            throws BadFormatException {
        for (String type : global_properties.keySet()) {
            if (global_properties.get(type).containsKey(name)) {
                if (PropertyFactory.getInstance().validValue(type, value)) {
                    this.replaceProperty(name, type, value);
                    return;
                }
            }
        }
    }

    @Override
    protected void updatePropertyValueByPropertyName(String nameToUpdate, String nameFromUpdate)
            throws BadFormatException {
        ArrayList<String> typesToUpdate = this.GetPropertyTypeOptions(nameToUpdate, global_properties);
        ArrayList<String> typesFromUpdate = this.GetPropertyTypeOptions(nameFromUpdate, global_properties);
        for (String type : typesFromUpdate) {
            if (typesToUpdate.contains(type)) {
                Property newProperty = PropertyFactory.getInstance().updatePropertyFromOtherProperty(
                        global_properties.get(type).get(nameToUpdate),
                        global_properties.get(type).get(nameFromUpdate));
                if (newProperty.isFinal()) {
                    throw new BadFormatException("Assign final property");
                }
                this.replaceProperty(nameToUpdate, type, newProperty);
                return;
            }
        }

    }
}