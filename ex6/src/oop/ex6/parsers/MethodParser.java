package oop.ex6.parsers;

import oop.ex6.Utils;
import oop.ex6.code.Method;
import oop.ex6.code.properties.Property;
import oop.ex6.code.properties.PropertyFactory;
import oop.ex6.exceptions.BadFormatException;

import javax.xml.stream.events.EndDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Extends Parser and parse the method code lines, uses the singleton design pattern
 */
public class MethodParser extends Parser {

    private static final String BAD_METHOD_LINE = "The method line is invalid";
    private HashMap<String, HashMap<String, Property>> method_arguments = new HashMap<>();
    private static MethodParser parser = new MethodParser();
    private static final String TWO_COMMAS = ",,";
    private static final String ONE_COMMAS = ",";


    /**
     * Constructor of MethodParser
     */
    private MethodParser() {

        // Initiates the method arguments types
        this.method_arguments.put(INT_TYPE, new HashMap<>());
        this.method_arguments.put(DOUBLE_TYPE, new HashMap<>());
        this.method_arguments.put(STRING_TYPE, new HashMap<>());
        this.method_arguments.put(CHAR_TYPE, new HashMap<>());
        this.method_arguments.put(BOOLEAN_TYPE, new HashMap<>());

        // Initiates the block arguments types
        this.local_properties.put(INT_TYPE, new HashMap<>());
        this.local_properties.put(DOUBLE_TYPE, new HashMap<>());
        this.local_properties.put(STRING_TYPE, new HashMap<>());
        this.local_properties.put(CHAR_TYPE, new HashMap<>());
        this.local_properties.put(BOOLEAN_TYPE, new HashMap<>());
    }

    /**
     * Returns the single MethodParser instance - singleton design pattern
     * @return MethodParser instance
     */
    public static MethodParser getInstance() {
        return parser;
    }

    /**
     * Verifies if the method code line is an assignment line
     * @param line the given line to verify
     * @return true if the line is an assignment, else false
     */
    protected boolean ifAssignMethodPropertyLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            for (String type : this.method_arguments.keySet()) {
                if (this.method_arguments.get(type).containsKey(splitLine[0]) ||
                        this.method_arguments.get(type).containsKey(splitLine[0])) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Verifies if the method is defined with equal parameter name
     * @throws BadFormatException if the method has at least two parameters with the same name
     */
    private void sameMethodParametersName(ArrayList<String> parameters) throws BadFormatException {
        if (parameters.size() > 0) {
            String[] parametersName = new String[parameters.size()];
            for (int i = 0; i < parameters.size(); i++) {
                String[] singleParameter = parameters.get(i).split(" ");
                if (singleParameter.length == 2) {
                    parametersName[i] = singleParameter[1];
                } else { // length == 3 (with final)
                    parametersName[i] = singleParameter[2];
                }
            }

            for (int i = 0; i < parametersName.length; i++) {
                for (int j = i + 1; j < parametersName.length; j++) {
                    if (parametersName[i].equals(parametersName[j])) {
                        throw new BadFormatException(ILLEGAL_CODE);
                    }
                }
            }
        }
    }

    /**
     * Extracts the method name and saves it in the methodName data member
     * @throws BadFormatException when there is no '(' that opens the method parameter section
     */
    private String[] extractMethodDetails(String methodLine) throws BadFormatException {
        methodLine = methodLine.substring(5);
        StringBuilder methodName = new StringBuilder(EMPTY_STRING);
        boolean endMethodName = false;
        boolean endMethodParams = false;
        StringBuilder methodParams = new StringBuilder(EMPTY_STRING);
        for (int i = 0; i < methodLine.length() - 1; i++) {
            if (methodLine.charAt(i) == OPEN_BRACKET) {
                endMethodName = true;
            } else if (!endMethodName) {
                methodName.append(methodLine.charAt(i));
            } else if (methodLine.charAt(i) == CLOSE_BRACKET) {
                endMethodParams = true;
            } else if (!endMethodParams) {
                methodParams.append(methodLine.charAt(i));
            } else {
                String elseString = methodLine.substring(i);
                if (!Utils.RemoveAllSpacesAtEnd(elseString).equals(OPEN_SECTION)) {
                    throw new BadFormatException(BAD_METHOD_LINE);
                }
            }
        }

        return new String[]{methodName.toString(), methodParams.toString()};
    }

    /**
     * Verifies the method parameter type and name is according to syntax
     * @param type the given parameter type to verify
     * @param name the given parameter name to verify
     * @throws BadFormatException if the type or name are invalid
     */
    private void verifyTypeName(String type, String name) throws BadFormatException {
        Utils.validParameterType(type);
        Utils.validParameterName(name, true);

    }

    /**
     * Verifies if the method line is valid
     * @throws BadFormatException if a syntax error is found
     */
    private void verifyMethodLine(String methodName, String methodParams) throws BadFormatException {
        methodName = Utils.RemoveAllSpacesAtEnd(methodName);
        Utils.validParameterName(methodName, true);

        if (methodParams == null) {
            return;
        }

        if (methodParams.contains(TWO_COMMAS)) {
            throw new BadFormatException(BAD_METHOD_LINE);
        }

        boolean isFinal = false;
        methodParams = Utils.getOnlyOneBlank(methodParams);
        ArrayList<String> splitMethodParams = Utils.cleanWhiteSpace(methodParams.split(ONE_COMMAS));
        sameMethodParametersName(splitMethodParams);

        for (String parameter : splitMethodParams) {
            ArrayList<String> currentParameter = Utils.cleanWhiteSpace(parameter.split(" "));
            if (currentParameter.size() > 3 || currentParameter.size() == 1) {
                throw new BadFormatException(BAD_METHOD_LINE);
            } else if (currentParameter.size() == 3) {
                if ((!currentParameter.get(0).equals(FINAL_CONSTANT))) {
                    throw new BadFormatException(BAD_METHOD_LINE);
                }
                isFinal = true;
                verifyTypeName(currentParameter.get(1), currentParameter.get(2));
                ArrayList<String> toHelp = new ArrayList<String>();
                toHelp.add(currentParameter.get(1));
                toHelp.add(currentParameter.get(2));
                currentParameter = toHelp;
            } else { // currentParameter.length == 2
                verifyTypeName(currentParameter.get(0), currentParameter.get(1));
            }
            Property newProperty = PropertyFactory.getInstance().createMethodProperty(currentParameter.get(0),
                    currentParameter.get(1), isFinal);
            HashMap<String, Property> oneNewHash = new HashMap<>();
            oneNewHash.put(newProperty.getName(), newProperty);
            method_arguments.put(newProperty.getType(), oneNewHash);
        }
    }

    /**
     * Extracts the condition text from the condition line (example: if(condition){)
     * @return extracted method parameter text
     * @throws BadFormatException if there is no '()" for the condition
     */
    private String getMethodParameters(String methodLine) throws BadFormatException {
        for (int i = 0; i < methodLine.length(); i++) {
            if (methodLine.charAt(i) == OPEN_BRACKET) {
                for (int j = methodLine.length() - 1; j > i; j--) {
                    if (methodLine.charAt(j) == CLOSE_BRACKET) {
                        if (i == j - 1) {
                            return null;
                        }
                        return methodLine.substring(i + 1, j); // returns the method parameters
                    }
                }
                // The condition does not end with a closing bracket
                throw new BadFormatException(BAD_METHOD_LINE);
            }
        }
        //The line does not have an opening condition bracket
        throw new BadFormatException(BAD_METHOD_LINE);
    }

    /**
     * extract the method parameter types according to the order in the method deceleration
     * @return an array of string that represents the parameter types of the method
     * @throws BadFormatException when the the parameters line is invalid
     */
    private ArrayList<String> getMethodParamType(String line) throws BadFormatException {
        ArrayList<String> result = new ArrayList<>();
        String[] splitLine = line.split(COMMA);
        for (String var : splitLine) {
            var = var.replace("  ", " ");
            String[] varSplit = var.split(" ");
            if (varSplit.length == 3) {
                result.add(Utils.RemoveAllSpacesAtEnd(varSplit[1]));
            } else if (varSplit.length == 2) {
                result.add(varSplit[0]);
            }
        }
        return result;
    }


    /**
     * Verifies if the given code line is a call to an existing method
     * @param line the given code line to verify
     * @return true if the code line is a valid method call, else false
     * @throws BadFormatException if the code line is an invalid existing method call
     */
    private boolean isCallToExistingMethod(String line, HashMap<String, Method> existingMethods,
                                           ArrayList<BlockParser> blocks)
            throws BadFormatException {
        line = line.replace(" ", "");
        String[] methodCall = line.split("\\(");

        if (!line.endsWith(END_BRACKET)) {
            throw new BadFormatException(ILLEGAL_CODE);
        }

        if (!(existingMethods.containsKey(methodCall[0]))) { // verify the method exist
            throw new BadFormatException(ILLEGAL_CODE);
        } else {// The method was found
            Method existingMethod = existingMethods.get(methodCall[0]);
            ArrayList<String> methodParameters = existingMethod.getMethodParameterType();
            String currentMethodParameters = getMethodParameters(line);
            String[] currentParameterArray;
            if (currentMethodParameters == null || currentMethodParameters.isEmpty()) {
                currentParameterArray = new String[0];
            } else {
                currentParameterArray = currentMethodParameters.split(ONE_COMMAS);
            }

            if (currentParameterArray.length != methodParameters.size()) {
                throw new BadFormatException(ILLEGAL_CODE);
            }
            // verify that the parameters are from the same type
            for (int i = 0; i < currentParameterArray.length; i++) {
                boolean found = false;
                for (int j = blocks.size() - 1; j >= 0; j--) {
                    if (blocks.get(j).localPropertyExist(currentParameterArray[i])) {
                        found = true;
                        if (!PropertyFactory.getInstance().validTypesTo(methodParameters.get(i),
                                getParameterType(currentParameterArray[i], blocks.get(j).local_properties))) {
                            throw new BadFormatException(ILLEGAL_CODE);
                        }
                    }
                }
                if (!found) {
                    if (localPropertyExist(currentParameterArray[i])) {
                        found = true;
                        if (!PropertyFactory.getInstance().validTypesTo(methodParameters.get(i),
                                getParameterType(currentParameterArray[i], local_properties))) {
                            throw new BadFormatException(ILLEGAL_CODE);
                        }
                    }
                }
                if (!found) {
                    if (methodPropertyExist(currentParameterArray[i])) {
                        found = true;
                        if (!PropertyFactory.getInstance().validTypesTo(methodParameters.get(i),
                                getParameterType(currentParameterArray[i], method_arguments))) {
                            throw new BadFormatException(ILLEGAL_CODE);
                        }
                    }
                }
                if (!found) {
                    if (globalPropertyExist(currentParameterArray[i])) {
                        found = true;
                        if (!PropertyFactory.getInstance().validTypesTo(methodParameters.get(i),
                                getParameterType(currentParameterArray[i],
                                        FileParser.getInstance().global_properties))) {
                            throw new BadFormatException(ILLEGAL_CODE);
                        }
                    }
                }
                if (!found) {
                    if (!PropertyFactory.getInstance().validValue(methodParameters.get(i),
                            currentParameterArray[i])) {
                        throw new BadFormatException(ILLEGAL_CODE);
                    }
                }
            }
        }
        return true;
    }

    /**
     * Parse the method code lines and verifies its validity according to the S-java definition
     * @param methodLines the given code lines of the method to verify
     * @param existingMethods the existing methods in the code
     * @return Method object if the method code lines are valid
     * @throws BadFormatException when the method code lines are invalid
     */
    public void parseMethod(ArrayList<String> methodLines, HashMap<String, Method> existingMethods)
            throws BadFormatException {
        String line;
        ArrayList<BlockParser> blocks = new ArrayList<BlockParser>();
        boolean insideBlock = false;
        boolean doneSomething = false;
        for (int i = 1; i < methodLines.size() - 1; i++) {
            line = Utils.RemoveAllSpacesAtEnd(methodLines.get(i));
            if (this.isIfLine(line)) {
                ArrayList<HashMap<String, HashMap<String, Property>>> allProperties =
                        getAllProperties(blocks, i);
                blocks.add(new BlockParser(false, line, allProperties));
                insideBlock = true;
                doneSomething = true;
            } else if (this.isWhileLine(methodLines.get(i))) {
                ArrayList<HashMap<String, HashMap<String, Property>>> allProperties =
                        getAllProperties(blocks, i);
                blocks.add(new BlockParser(true, line, allProperties));
                insideBlock = true;
                doneSomething = true;
            }

            if (doneSomething) {
                doneSomething = false;
                continue;
            }

            if (isEnd(line)) {
                if (blocks.size() == 0) {
                    throw new BadFormatException(ILLEGAL_CODE);
                }
                blocks.remove(blocks.size() - 1);
                doneSomething = true;
            } else if (this.isPropertyLine(line)) {
                ArrayList<HashMap<String, HashMap<String, Property>>> allProperties = getAllBlocksProperty(blocks);
                allProperties.add(this.method_arguments);
                allProperties.add(FileParser.getInstance().global_properties);
                ArrayList<Property> newProperties = getPropertiesFromLine(line, allProperties);
                if (insideBlock) {
                    if (blocks.get(blocks.size() - 1).propertiesExistInBlock(newProperties)) {
                        throw new BadFormatException(ILLEGAL_CODE);
                    } else {
                        blocks.get(blocks.size() - 1).addPropertiesToBlock(newProperties);
                        doneSomething = true;
                    }
                } else {
                    for (Property newProperty : newProperties) {
                        if (this.localPropertyExist(newProperty.getName()) ||
                                this.methodPropertyExist(newProperty.getName())) {
                            throw new BadFormatException(ILLEGAL_CODE);
                        } else {
                            this.local_properties.get(newProperty.getType()).put(newProperty.getName(), newProperty);
                        }
                    }
                    doneSomething = true;
                }
            } else {
                boolean assign = false;
                if (insideBlock) {
                    for (BlockParser blockParser : blocks) {
                        if (blockParser.ifAssignLocalPropertyLine(line)) {
                            blockParser.AssignValueToLocalProperties(line);
                            assign = true;
                            break;
                        }
                    }
                }
                if (!assign) {
                    if (this.ifAssignLocalPropertyLine(line)) {
                        AssignValueToLocalProperties(line);
                        doneSomething = true;
                    } else if (this.ifAssignMethodPropertyLine(line)) {
                        AssignValueToMethodProperties(line);
                        doneSomething = true;
                    } else if (this.ifAssignGlobalPropertyLine(line)) {
                        AssignValueToGlobalProperties(line);
                        doneSomething = true;
                    }
                } else {
                    doneSomething = true;
                }
            }

            if (!doneSomething) {
                if (this.isReturn(methodLines, i)) {
                    break;
                }

                if (!this.isCallToExistingMethod(line, existingMethods, blocks)) {
                    throw new BadFormatException(ILLEGAL_CODE);
                }
            } else {
                doneSomething = false;
            }
        }

        if (!this.isEnd(methodLines.get(methodLines.size() - 1))) {
            throw new BadFormatException(ILLEGAL_CODE);
        }
    }

    private ArrayList<HashMap<String, HashMap<String, Property>>> getAllProperties
            (ArrayList<BlockParser> blocks, int i) {
        ArrayList<HashMap<String, HashMap<String, Property>>> allProperties = new ArrayList<>();
        for (int j = blocks.size() - 1; j >= 0; j--) {
            allProperties.add(blocks.get(i).local_properties);
        }
        allProperties.add(local_properties);
        allProperties.add(method_arguments);
        allProperties.add(FileParser.getInstance().global_properties);
        return allProperties;
    }

    /**
     * Verifies that the method deceleration code line is valid
     *
     * @param methodLines the given method code lines to verify
     * @return Method object if the deceleration line is valid
     * @throws BadFormatException when the method deceleration line is invalid
     */
    public Method parseMethodLine(ArrayList<String> methodLines) throws BadFormatException {
        String line = Utils.RemoveAllSpacesAtEnd(methodLines.get(0));
        String[] methodDetails = extractMethodDetails(line);
        verifyMethodLine(methodDetails[0], methodDetails[1]);
        ArrayList<String> methodParamType = this.getMethodParamType(methodDetails[1]);
        return new Method(methodParamType, methodDetails[0]);
    }

    /**
     * Verifies if the given method code line is a return statement line
     * @param methodLines the given method code lines to verify
     * @param lineIndex   the given code line to verify that it is a return statement line in the method
     * @return true if the code line is a valid return statement line, else false
     * @throws BadFormatException when the code line is recognized as a return statement line but is invalid
     */
    private boolean isReturn(ArrayList<String> methodLines, int lineIndex) throws BadFormatException {
        String returnLine = Utils.RemoveAllSpacesAtEnd(methodLines.get(lineIndex));
        if (returnLine.equals("return;")) {
            // if (methodLines.size() == lineIndex + 2) {
            String nextLine = methodLines.get(lineIndex + 1).replace(" ", "");
            if (!(nextLine.equals(CLOSE_SECTION))) {
                throw new BadFormatException(ILLEGAL_CODE);
            }
            // } else { // There is more then one line after the return statement
            // (after the return statement there should be one more line)
            //throw new BadFormatException("The method end is invalid");
            return true;
        } else {
            if (returnLine.contains("return")) {
                throw new BadFormatException(ILLEGAL_CODE);
            }
            return false;
        }

    }

    /**
     * Assigns values to method properties
     * @param line the given code line the assignment happens
     * @throws BadFormatException when the assignment is invalid
     */
    private void AssignValueToMethodProperties(String line) throws BadFormatException {
        line = Utils.RemoveAllSpacesAtEnd(line);
        String[] splitLine = line.split(EQUALS);
        if (splitLine.length != 2) {
            throw new BadFormatException(ILLEGAL_CODE);
        }
        String value = Utils.RemoveAllSpacesAtEnd(splitLine[1]);
        String name = Utils.RemoveAllSpacesAtEnd(splitLine[0]);
        if (!this.methodPropertyExist(name)) {
            throw new BadFormatException(ILLEGAL_CODE);
        }
        if (value.endsWith(END_BRACKET)) {
            value = value.substring(0, value.length() - 1);
        }

        String propertyType = this.getParameterType(name, this.method_arguments);
        if (!PropertyFactory.getInstance().validValue(propertyType, value)) {
            throw new BadFormatException(ILLEGAL_CODE);
        }

        Property currentProperty = PropertyFactory.getInstance().getUpdatedProperty(
                this.method_arguments.get(propertyType).get(name), value);

        this.method_arguments.get(propertyType).put(name, currentProperty);
    }

    /**
     * Verifies if the given property exist according to a given property name
     * @param name the given property name to verify
     * @return true if the property exist else false
     */
    public boolean methodPropertyExist(String name) {
        for (String type : this.method_arguments.keySet()) {
            if (this.method_arguments.get(type).containsKey(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns all the blocks properties
     * @param allBlocks the given blocks to extract their properties
     * @return an all the blocks properties
     */
    private ArrayList<HashMap<String, HashMap<String, Property>>> getAllBlocksProperty(
            ArrayList<BlockParser> allBlocks) {
        ArrayList<HashMap<String, HashMap<String, Property>>> ret = new ArrayList<>();
        for (BlockParser block : allBlocks) {
            ret.add(block.local_properties);
        }
        return ret;
    }
}
