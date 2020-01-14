package oop.ex6.parsers;

import oop.ex6.Utils;
import oop.ex6.code.Method;
import oop.ex6.code.properties.Property;
import oop.ex6.code.properties.PropertyFactory;
import oop.ex6.exceptions.BadFormatException;

import java.util.ArrayList;
import java.util.HashMap;

public class MethodParser extends Parser {
    private static final String BAD_METHOD_LINE = "The method line is invalid";
    private HashMap<String, HashMap<String, Property>> method_arguments = new HashMap<>();
    private static MethodParser parser = new MethodParser();
    private HashMap<String, Method> existingMethods = new HashMap<>();


    private MethodParser() {
        this.method_arguments.put("int", new HashMap<>());
        this.method_arguments.put("double", new HashMap<>());
        this.method_arguments.put("String", new HashMap<>());
        this.method_arguments.put("char", new HashMap<>());
        this.method_arguments.put("boolean", new HashMap<>());
        this.local_properties.put("int", new HashMap<>());
        this.local_properties.put("double", new HashMap<>());
        this.local_properties.put("String", new HashMap<>());
        this.local_properties.put("char", new HashMap<>());
        this.local_properties.put("boolean", new HashMap<>());
    }

    public static MethodParser getInstance() {
        return parser;
    }

    protected boolean ifAssignMethodPropertyLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            for (String type : this.local_properties.keySet()) {
                if (this.local_properties.get(type).containsKey(splitLine[0]) ||
                        this.local_properties.get(type).containsKey(splitLine[0])) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean methodPropertyExist(Property property) {
        for (String type : method_arguments.keySet()) {
            if (method_arguments.get(type).containsKey(property.getName()) ||
                    local_properties.get(type).containsKey(property.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifies if the method is defined with equal parameter name
     *
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
                        throw new BadFormatException("Equal parameter names in the method deceleration");
                    }
                }
            }
        }
    }

    /**
     * Extracts the method name and saves it in the methodName data member
     *
     * @throws BadFormatException when there is no '(' that opens the method parameter section
     */
    private String[] extractMethodDetails(String methodLine) throws BadFormatException {
        methodLine = methodLine.substring(5);
        StringBuilder methodName = new StringBuilder(EMPTY_STRING);
        boolean endMethodName = false;
        boolean endMethodParams = false;
        StringBuilder methodParams = new StringBuilder(EMPTY_STRING);
        String methodParamsString = EMPTY_STRING;
        for (int i = 0; i < methodLine.length() - 1; i++) {
            if (methodLine.charAt(i) == '(') {
                endMethodName = true;
            } else if (!endMethodName) {
                methodName.append(methodLine.charAt(i));
            } else if (methodLine.charAt(i) == ')') {
                endMethodParams = true;
            } else if (!endMethodParams) {
                methodParams.append(methodLine.charAt(i));
            } else {
                String elseString = methodLine.substring(i);
                if (!Utils.RemoveAllSpacesAtEnd(elseString).equals("{")) {
                    throw new BadFormatException(BAD_METHOD_LINE);
                }
            }
        }

        return new String[]{methodName.toString(), methodParams.toString()};
    }

    /**
     * Verifies the method parameter type and name is according to syntax
     *
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
     *
     * @throws BadFormatException if a syntax error is found
     */
    private void verifyMethodLine(String methodName, String methodParams) throws BadFormatException {
        Utils.validParameterName(methodName, true);

        if (methodParams == null) {
            return;
        }

        if (methodParams.contains(",,")) {
            throw new BadFormatException(BAD_METHOD_LINE);
        }

        boolean isFinal = false;
        methodParams = Utils.getOnlyOneBlank(methodParams);
        ArrayList<String> splitMethodParams = Utils.cleanWhiteSpace(methodParams.split(","));
        sameMethodParametersName(splitMethodParams);

        for (String parameter : splitMethodParams) {
            ArrayList<String> currentParameter = Utils.cleanWhiteSpace(parameter.split(" "));
            if (currentParameter.size() > 3 || currentParameter.size() == 1) {
                throw new BadFormatException(BAD_METHOD_LINE);
            } else if (currentParameter.size() == 3) {
                if ((!currentParameter.get(0).equals("final"))) {
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
     *
     * @return extracted method parameter text
     * @throws BadFormatException if there is no '()" for the condition
     */
    private String getMethodParameters(String methodLine) throws BadFormatException {
        for (int i = 0; i < methodLine.length(); i++) {
            if (methodLine.charAt(i) == '(') {
                for (int j = methodLine.length() - 1; j > i; j--) {
                    if (methodLine.charAt(j) == ')') {
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
     *
     * @return an array of string that represents the parameter types of the method
     * @throws BadFormatException when the the parameters line is invalid
     */
    private ArrayList<String> getMethodParamType() throws BadFormatException {
        ArrayList<String> result = new ArrayList<>();
        for (String type : this.method_arguments.keySet()) {
            if (!this.method_arguments.get(type).isEmpty()) {
                result.add(type);
            }
        }
        return result;
    }

    /**
     * Verifies if the given code line is a call to an existing method
     *
     * @param line   the given code line to verify
     * @param method the method that the code line is inside
     * @return true if the code line is a valid method call, else false
     * @throws BadFormatException if the code line is an invalid existing method call
     */
    private boolean isCallToExistingMethod(String line, HashMap<String, Method> existingMethods)
            throws BadFormatException {
        line = line.replace(" ", "");
        String[] methodCall = line.split("\\(");

        if (existingMethods.containsKey(methodCall[0])) { // The method exist
            Method method = existingMethods.get(methodCall[0]);
            if (!(line.endsWith(") ;"))) {
                throw new BadFormatException("The method call is invalid");
            }

            String methodParameters = getMethodParameters(line);
            String[] parametersArray = methodParameters.split(",");

            for (int i = 0; i < parametersArray.length; i++) {
                String expectedParamType = method.getMethodParameterType().get(i);
                // verifies if the parameter exist in the method
                if (method.getProperties().containsKey(expectedParamType)) {
                    if (!(method.getProperties().get(expectedParamType).containsKey(parametersArray[i]))) {
                        // the parameter does not exist in the method, verifies if it exist in the global scope
                        if (FileParser.getInstance().global_properties.containsKey(expectedParamType)) {
                            if (!(FileParser.getInstance().global_properties.get(expectedParamType).containsKey(
                                    parametersArray[i]))) {
                                // the parameter does not exist in the global scope either
                                throw new BadFormatException("The method call is invalid");
                            }
                        }
                    }
                } else { // the parameter type does not exist in the method
                    if (FileParser.getInstance().global_properties.containsKey(expectedParamType)) {
                        if (!(FileParser.getInstance().global_properties.get(expectedParamType).containsKey(parametersArray[i]))) {
                            // the parameter does not exist in the global scope either
                            throw new BadFormatException("The method call is invalid");
                        }
                    } else { // the parameter type does not exist in the global scope either
                        throw new BadFormatException("The method call is invalid");
                    }

                }
            }
            return true;
        }

        return false;
    }

    public Method parseMethod(ArrayList<String> methodLines, HashMap<String, Method> existingMethods)
            throws BadFormatException {
        String line = Utils.RemoveAllSpacesAtEnd(methodLines.get(0));
        String[] methodDetails = extractMethodDetails(line);
        verifyMethodLine(methodDetails[0], methodDetails[1]);
        ArrayList<String> methodParamType = this.getMethodParamType();
        Method newMethod = new Method(methodParamType, methodDetails[0]);
        ArrayList<BlockParser> blocks = new ArrayList<BlockParser>();
        boolean insideBlock = false;

        for (int i = 1; i < methodLines.size() - 1; i++) {
            line = Utils.RemoveAllSpacesAtEnd(methodLines.get(i));
            if (this.isIfLine(line)) {
                blocks.add(new BlockParser(false, line));
                insideBlock = true;
                continue;
            } else if (this.isWhileLine(methodLines.get(i))) {
                blocks.add(new BlockParser(true, line));
                insideBlock = true;
                continue;
            }

            if (isEnd(line)) {
                if (blocks.size() == 0) {
                    throw new BadFormatException("Invalid line");
                }
                blocks.remove(blocks.size() - 1);
                continue;
            } else if (this.isPropertyLine(line)) {
                ArrayList<HashMap<String, HashMap<String, Property>>> allProperties = getAllBlocksProperty(blocks);
                allProperties.add(this.method_arguments);
                allProperties.add(FileParser.getInstance().global_properties);
                ArrayList<Property> newProperties = getPropertiesFromLine(line, allProperties);
                if (insideBlock) {
                    if (blocks.get(blocks.size() - 1).propertiesExistInBlock(newProperties)) {
                        throw new BadFormatException("Properties already exist");
                    } else {
                        blocks.get(blocks.size() - 1).addPropertiesToBlock(newProperties);
                        continue;
                    }
                } else {
                    for (Property newProperty : newProperties) {
                        if (this.localPropertyExist(newProperty.getName()) ||
                                this.methodPropertyExist(newProperty.getName())) {
                            throw new BadFormatException("Property already Exist");
                        } else {
                            this.local_properties.get(newProperty.getType()).put(newProperty.getName(), newProperty);
                        }
                    }
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
                    } else if (this.ifAssignMethodPropertyLine(line)) {
                        AssignValueToMethodProperties(line);
                    } else if (this.ifAssignGlobalPropertyLine(line)) {
                        AssignValueToGlobalProperties(line);
                    }
                }
            }

            if (this.isReturn(methodLines, i)) {
                break;
            }

            if (!this.isCallToExistingMethod(line, newMethod, existingMethods)) {
                throw new BadFormatException("Bad Formant");
            }


        }

        if (!this.isEnd(methodLines.get(methodLines.size() - 1))) {
            throw new BadFormatException("Invalid line");
        }
        return newMethod;
    }

    public Method parseMethodLine(ArrayList<String> methodLines) throws BadFormatException {
        String line = Utils.RemoveAllSpacesAtEnd(methodLines.get(0));
        String[] methodDetails = extractMethodDetails(line);
        verifyMethodLine(methodDetails[0], methodDetails[1]);
        ArrayList<String> methodParamType = this.getMethodParamType();
        return new Method(methodParamType, methodDetails[0]);
    }

    private boolean isReturn(ArrayList<String> methodLines, int lineIndex) throws BadFormatException {
        String returnLine = Utils.RemoveAllSpacesAtEnd(methodLines.get(lineIndex));
        if (returnLine.equals("return;")) {
            // if (methodLines.size() == lineIndex + 2) {
            String nextLine = methodLines.get(lineIndex + 1).replace(" ", "");
            if (!(nextLine.equals("}"))) {
                throw new BadFormatException("The method return statement is invalid");
            }
            // } else { // There is more then one line after the return statement
            // (after the return statement there should be one more line)
            //throw new BadFormatException("The method end is invalid");
            return true;
        } else {
            if (returnLine.contains("return")) {
                throw new BadFormatException("The return statement is invalid");
            }
            return false;
        }

    }

    private void AssignValueToMethodProperties(String line) throws BadFormatException {
        line = Utils.RemoveAllSpacesAtEnd(line);
        String[] splitLine = line.split(EQUALS);
        if (splitLine.length != 2) {
            throw new BadFormatException("Assign is invalid");
        }
        String value = Utils.RemoveAllSpacesAtEnd(splitLine[1]);
        String name = Utils.RemoveAllSpacesAtEnd(splitLine[0]);
        if (!this.methodPropertyExist(name)) {
            throw new BadFormatException("Property does not exist");
        }
        String propertyType = this.getParameterType(name, this.method_arguments);
        if (!PropertyFactory.getInstance().validValue(propertyType, value)) {
            throw new BadFormatException("Property value is invalid");
        }

        Property currentProperty = PropertyFactory.getInstance().getUpdatedProperty(
                this.method_arguments.get(propertyType).get(name), value);

        this.method_arguments.get(propertyType).put(name, currentProperty);
    }

    /**
     * Verifies if the given property exist according to a given property name
     *
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

    private ArrayList<HashMap<String, HashMap<String, Property>>> getAllBlocksProperty(
            ArrayList<BlockParser> allBlocks) {
        ArrayList<HashMap<String, HashMap<String, Property>>> ret = new ArrayList<>();
        for (BlockParser block : allBlocks) {
            ret.add(block.local_properties);
        }
        return ret;
    }
}
