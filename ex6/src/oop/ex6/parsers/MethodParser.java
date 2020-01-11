package oop.ex6.parsers;

import oop.ex6.Utils;
import oop.ex6.Validations;
import oop.ex6.code.Block;
import oop.ex6.code.Method;
import oop.ex6.code.properties.Property;
import oop.ex6.code.properties.PropertyFactory;
import oop.ex6.exceptions.BadFormatException;

import java.util.ArrayList;
import java.util.HashMap;

public class MethodParser extends Parser {
    private static final String BAD_METHOD_LINE = "The method line is invalid";
    private HashMap<String, HashMap<String, Property>> globalProperties;
    private HashMap<String, Method> existingMethods;

    private MethodParser() {

    }

    private static MethodParser methodParser = new MethodParser();

    public static MethodParser getInstance() {
        return methodParser;
    }

    public Method parseMethod(ArrayList<String> lines,
                              HashMap<String, HashMap<String, Property>> globalProperties)
            throws BadFormatException {
        String methodName = extractMethodName(lines.get(0));
        verifyMethodLine(methodName);
        String[] methodParamType = getMethodParamType(methodName);
        Method newMethod = new Method(methodParamType);
        this.globalProperties = globalProperties;
        ArrayList<Block> blocks = new ArrayList<>();
        for (int i = 1; i < lines.size() - 1; i++) {
            if (this.isIfLine(lines.get(i))) {
                blocks.add(new Block(false, lines.get(i)));
                ArrayList<String> newBlock = new ArrayList<>();
                newBlock.add(lines.get(i));
            } else if (this.isWhileLine(lines.get(i))) {
                blocks.add(new Block(true, lines.get(i)));
                ArrayList<String> newBlock = new ArrayList<>();
                newBlock.add(lines.get(i));
            }

            if (blocks.size() > 0) {
                blocks.get(blocks.size() - 1).addLine(lines.get(i));
            }

            if (isEnd(lines.get(i))) {
                if (blocks.size() == 0) {
                    throw new BadFormatException("Invalid line");
                }
                blocks.remove(blocks.size() - 1);
            }

            if (this.isPropertyLine(lines.get(i))) {
                addProperties(getPropertyFromLine(lines.get(i)));
            }
        }

        if (!this.isEnd(lines.get(lines.size() - 1))) {
            throw new BadFormatException("Invalid line");
        }

        return newMethod;
    }

    /**
     * Extracts the method name and saves it in the methodName data member
     * @throws BadFormatException when there is no '(' that opens the method parameter section
     */
    private String extractMethodName(String methodLine) throws BadFormatException {
        String[] splitMethodLine = methodLine.split(" ");
        String ret = EMPTY_STRING;
        for (int i = 0; i < splitMethodLine.length; i++) {
            if (splitMethodLine[1].charAt(i) == '(') {
                ret = splitMethodLine[1].substring(0, i);
            }
        }
        if (ret.isEmpty()) {
            throw new BadFormatException(BAD_METHOD_LINE);
        }
        return ret;
    }

    /**
     * Extracts the condition text from the condition line (example: if(condition){)
     * @return extracted method parameter text
     * @throws BadFormatException if there is no '()" for the condition
     */
    private String getMethodParameters(String methodLine) throws BadFormatException {
        for (int i = 0; i < methodLine.length(); i++) {
            if (methodLine.charAt(i) == '(') {
                for (int j = i; j < methodLine.length(); j++) {
                    if (methodLine.charAt(i) == ')') {
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
     * @param methodLine the given method deceleration line
     * @return an array of string that represents the parameter types of the method
     * @throws BadFormatException when the the parameters line is invalid
     */
    private String[] getMethodParamType(String methodLine) throws BadFormatException {
        methodLine = getMethodParameters(methodLine);
        String[] parameters = methodLine.split(",");
        String[] methodParamType = new String[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            String[] singleParam = parameters[i].split(" ");
            if (singleParam.length == 3) { // has final deceleration
                methodParamType[i] = singleParam[1];
            } else { // does not have final deceleration
                methodParamType[i] = singleParam[0];
            }
        }
        return methodParamType;

    }

    /**
     * Verifies the method parameter type and name is according to syntax
     * @param type the given parameter type to verify
     * @param name the given parameter name to verify
     * @throws BadFormatException if the type or name are invalid
     */
    private void verifyTypeName(String type, String name) throws BadFormatException {

        Validations.getValidations().validParameterType(type);
        Validations.getValidations().validParameterName(name, true);

    }

    /**
     * Verifies if the method line is valid
     * @throws BadFormatException if a syntax error is found
     */
    private void verifyMethodLine(String methodLine) throws BadFormatException {
        String methodParameters = getMethodParameters(methodLine);

        if (!Utils.RemoveAllSpacesAtEnd(methodLine).endsWith("{")) {
            throw new BadFormatException(BAD_METHOD_LINE);
        }

        if (methodParameters.contains(",,")) {
            throw new BadFormatException(BAD_METHOD_LINE);
        }

        String[] singleParameters = methodParameters.split(",");

        for (String parameter : singleParameters) {
            boolean isFinal = false;
            String[] currentParameter = parameter.split(" ");
            if (currentParameter.length > 3 || currentParameter.length == 1) {
                throw new BadFormatException(BAD_METHOD_LINE);
            } else if (currentParameter.length == 3) {
                if ((!currentParameter[0].equals("final"))) {
                    throw new BadFormatException(BAD_METHOD_LINE);
                }
                isFinal = true;
                verifyTypeName(currentParameter[1], currentParameter[2]);
                currentParameter = new String[]{currentParameter[1], currentParameter[2]};
            } else { // currentParameter.length == 2
                verifyTypeName(currentParameter[0], currentParameter[1]);
            }
            Property newProperty = PropertyFactory.getInstance().createMethodProperty(currentParameter[0],
                    currentParameter[1], isFinal);
            HashMap<String, HashMap<String, Property>> newHash = new HashMap<>();
            HashMap<String, Property> oneNewHash = new HashMap<>();
            oneNewHash.put(newProperty.getName(), newProperty);
            newHash.put(newProperty.getType(), oneNewHash);
            addProperties(newHash);
        }
    }

    /**
     * Verifies if the given code line is a call to an existing method
     * @param line the given code line to verify
     * @param method the method that the code line is inside
     * @return true if the code line is a valid method call, else false
     * @throws BadFormatException if the code line is an invalid existing method call
     */
    private boolean isCallToExistingMethod(String line, Method method) throws BadFormatException {
        line = line.replace(" ", "");
        String[] methodCall = line.split("\\(");
        if (existingMethods.containsKey(methodCall[0])) { // The method exist
            if (!(line.endsWith(");"))) {
                throw new BadFormatException("The method call is invalid");
            }
            String methodParameters = getMethodParameters(line);
            String[] parametersArray = methodParameters.split(",");

            for (int i = 0; i < parametersArray.length; i++) {
                String expectedParamType = method.getMethodParameterType()[i];
                // verifies if the parameter exist in the method
                if (method.getProperties().containsKey(expectedParamType)) {
                    if (!(method.getProperties().get(expectedParamType).containsKey(parametersArray[i]))) {
                        // the parameter does not exist in the method, verifies if it exist in the global scope
                        if (globalProperties.containsKey(expectedParamType)) {
                            if (!(globalProperties.get(expectedParamType).containsKey(parametersArray[i]))) {
                                // the parameter does not exist in the global scope either
                                throw new BadFormatException("The method call is invalid");
                            }
                        }
                    }
                } else { // the parameter type does not exist in the method
                    if (globalProperties.containsKey(expectedParamType)) {
                        if (!(globalProperties.get(expectedParamType).containsKey(parametersArray[i]))) {
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

    private boolean isReturn(ArrayList<String> methodLines, int lineIndex) throws BadFormatException {
        String returnLine = methodLines.get(lineIndex).replace(" ", "");
        if (returnLine.equals("return;")) {
            if (methodLines.size() == lineIndex + 2) {
                String nextLine = methodLines.get(lineIndex + 1).replace(" ", "");
                if (!(nextLine.equals("}"))) {
                    throw new BadFormatException("The method end is invalid");
                }
            } else { // There is more then one line after the return statement
                // (after the return statement there should be one more line)
                throw new BadFormatException("The method end is invalid");
            }
            return true;
        } else {
            if (returnLine.contains("return")) {
                throw new BadFormatException("The return statement is invalid");
            }
            return false;
        }

    }
}
