//package oop.ex6.parsers;
//
//import oop.ex6.Utils;
//import oop.ex6.code.Block;
//import oop.ex6.code.Method;
//import oop.ex6.code.properties.Property;
//import oop.ex6.code.properties.PropertyFactory;
//import oop.ex6.exceptions.BadFormatException;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
///**
// * Extends Parser and parse the methods from the original file , uses the singleton design pattern
// * The parser is activated from the FileParser class
// */
//public class OldMethodParser extends OldParser {
//
//
//    private static final String BAD_METHOD_LINE = "The method line is invalid";
//
//    // Represents all the global properties in the code file
//    private HashMap<String, HashMap<String, Property>> globalProperties;
//    // Represents all the existing methods in the code line
//    private HashMap<String, Method> existingMethods = new HashMap<>();
//
//    /**
//     * MethodParser private constructor - singleton design pattern
//     */
//    private OldMethodParser() {
//    }
//
//    // Creating a single instance of MethodParser - singleton design pattern
//    private static OldMethodParser oldMethodParser = new OldMethodParser();
//
//    /**
//     * Returns the single instance of MethodParser - singleton design pattern
//     *
//     * @return
//     */
//    public static OldMethodParser getInstance() {
//        return oldMethodParser;
//    }
//
//    /**
//     * Parse the method into data section and verifies the validity of the method according to the
//     * S-java definition
//     *
//     * @param lines The methods code lines are gathered in an array list
//     * @return a method object if the method is valid
//     * @throws BadFormatException when the method code is invalid
//     */
//    public Method parseMethod(ArrayList<String> lines)
//            throws BadFormatException {
//        this.local_properties = new HashMap<>();
//        String line = Utils.RemoveAllSpacesAtEnd(lines.get(0));
//        String[] methodDetails = extractMethodName(line);
//        verifyMethodLine(methodDetails[0], methodDetails[1]);
//        String[] methodParamType = getMethodParamType(lines.get(0));
////        Method newMethod = new Method(methodParamType, methodDetails[0]);
////        this.globalProperties = FileParser.global_properties;
//        ArrayList<Block> blocks = new ArrayList<>();
//        boolean startBlock;
//        for (int i = 1; i < lines.size() - 1; i++) {
//            line = Utils.RemoveAllSpacesAtEnd(lines.get(i));
//            if (this.isIfLine(line)) {
//                blocks.add(new Block(false, line, this.local_properties));
//                ArrayList<String> newBlock = new ArrayList<>();
//                newBlock.add(line);
//                startBlock = true;
//            } else if (this.isWhileLine(lines.get(i))) {
//                blocks.add(new Block(true, line, this.local_properties));
//                ArrayList<String> newBlock = new ArrayList<>();
//                newBlock.add(line);
//                startBlock = true;
//            } else {
//                startBlock = false;
//            }
//
//
//            if (!startBlock) {
//                if (isEnd(line)) {
//                    if (blocks.size() == 0) {
//                        throw new BadFormatException("Invalid line");
//                    }
//                    for (String type : blocks.get(blocks.size() - 1).local_properties.keySet()) {
//                        for (String name : blocks.get(blocks.size() - 1).local_properties.get(type).keySet()) {
//                            this.local_properties.get(type).remove(name);
//                        }
//                    }
//                    blocks.remove(blocks.size() - 1);
//                } else if (this.isPropertyLine(line)) {
//                    HashMap<String, HashMap<String, Property>> toAddProperties = getPropertyFromLine(line);
//                    addProperties(toAddProperties);
//                    if (blocks.size() > 0) {
//                        blocks.get(blocks.size() - 1).addProperties(toAddProperties);
//                    }
//                } else if (this.ifAssignPropertyLine(line)) {
//                    this.assignProperty(line);
////                } else if (this.isReturn(lines, i)) {
////                    break;
////                } else if (!this.isCallToExistingMethod(line, newMethod)) {
////                    throw new BadFormatException("Bad Formant");
////                }
//            }
//        }
//
//        if (!this.isEnd(lines.get(lines.size() - 1))) {
//            throw new BadFormatException("Invalid line");
//        }
//
//        return newMethod;
//    }
//
//    /**
//     * Extracts the method name and saves it in the methodName data member
//     *
//     * @throws BadFormatException when there is no '(' that opens the method parameter section
//     */
//    private String[] extractMethodName(String methodLine) throws BadFormatException {
//        System.out.println(methodLine);
//        String[] splitMethodLine = methodLine.split("\\s");
//        ArrayList<String> splitMethodLineArray = Utils.cleanWhiteSpace(splitMethodLine);
//        System.out.println(String.join("_", splitMethodLineArray));
//        String methodName = null;
//        StringBuilder methodParams = new StringBuilder(EMPTY_STRING);
//        String methodParamsString = null;
//        boolean doneWithParams = false;
//        for (int i = 1; i < splitMethodLineArray.size() - 1; i++) {
//            if (splitMethodLineArray.get(i).equals("(")) {
//                methodName = splitMethodLineArray.get(i - 1);
//            } else if (splitMethodLineArray.get(i).equals(")") && !doneWithParams) {
//                methodParamsString = methodParams.toString();
//                doneWithParams = true;
//            } else if (methodName == null) {
//                continue;
//            } else if (!doneWithParams) {
//                methodParams.append(splitMethodLineArray.get(i)+" ");
//            } else {
//                throw new BadFormatException(BAD_METHOD_LINE);
//            }
//        }
//
//        if(!splitMethodLineArray.get(splitMethodLineArray.size() - 1).equals("{")) {
//            throw new BadFormatException(BAD_METHOD_LINE);
//        }
//
//        return new String[] {methodName, methodParamsString};
//    }
//
//    /**
//     * Extracts the condition text from the condition line (example: if(condition){)
//     *
//     * @return extracted method parameter text
//     * @throws BadFormatException if there is no '()" for the condition
//     */
//    private String getMethodParameters(String methodLine) throws BadFormatException {
//        for (int i = 0; i < methodLine.length(); i++) {
//            if (methodLine.charAt(i) == '(') {
//                for (int j = methodLine.length() - 1; j > i; j--) {
//                    if (methodLine.charAt(j) == ')') {
//                        if (i == j - 1) {
//                            return null;
//                        }
//                        return methodLine.substring(i + 1, j); // returns the method parameters
//                    }
//                }
//                // The condition does not end with a closing bracket
//                throw new BadFormatException(BAD_METHOD_LINE);
//            }
//        }
//        //The line does not have an opening condition bracket
//        throw new BadFormatException(BAD_METHOD_LINE);
//    }
//
//    /**
//     * extract the method parameter types according to the order in the method deceleration
//     *
//     * @param methodLine the given method deceleration line
//     * @return an array of string that represents the parameter types of the method
//     * @throws BadFormatException when the the parameters line is invalid
//     */
//    private String[] getMethodParamType(String methodLine) throws BadFormatException {
//        if (getMethodParameters(methodLine) == null) {
//            return new String[0];
//        }
//        String methodParams = getMethodParameters(methodLine);
//        String[] parameters = methodParams.split(",");
//        String[] methodParamType = new String[parameters.length];
//        for (int i = 0; i < parameters.length; i++) {
//            String[] singleParam = parameters[i].split(" ");
//            if (singleParam.length == 3) { // has final deceleration
//                methodParamType[i] = singleParam[1];
//            } else { // does not have final deceleration
//                methodParamType[i] = singleParam[0];
//            }
//        }
//        return methodParamType;
//
//    }
//
//    /**
//     * Verifies the method parameter type and name is according to syntax
//     *
//     * @param type the given parameter type to verify
//     * @param name the given parameter name to verify
//     * @throws BadFormatException if the type or name are invalid
//     */
//    private void verifyTypeName(String type, String name) throws BadFormatException {
//        Utils.validParameterType(type);
//        Utils.validParameterName(name, true);
//
//    }
//
//    /**
//     * Verifies if the method line is valid
//     *
//     * @throws BadFormatException if a syntax error is found
//     */
//    private void verifyMethodLine(String methodName, String methodParams) throws BadFormatException {
//        if (methodParams == null) {
//            return;
//        }
//
//        if (methodParams.contains(",,")) {
////            System.out.println("nina");
//            throw new BadFormatException(BAD_METHOD_LINE);
//        }
//
//        sameMethodParametersName(methodParams);
//
//        String[] singleParameters = methodParams.split(",");
//        for (String parameter : singleParameters) {
//            boolean isFinal = false;
//            ArrayList<String> currentParameter = Utils.cleanWhiteSpace(parameter.split(" "));
//            if (currentParameter.size() > 3 || currentParameter.size() == 1) {
////                System.out.println("nina");
//                throw new BadFormatException(BAD_METHOD_LINE);
//            } else if (currentParameter.size() == 3) {
////                Utils.printList(currentParameter);
//                if ((!currentParameter.get(0).equals("final"))) {
//                    throw new BadFormatException(BAD_METHOD_LINE);
//                }
//                isFinal = true;
//                verifyTypeName(currentParameter.get(1), currentParameter.get(2));
//                ArrayList<String> toHelp = new ArrayList<String>();
//                toHelp.add(currentParameter.get(1));
//                toHelp.add(currentParameter.get(2));
//                currentParameter = toHelp;
//            } else { // currentParameter.length == 2
//                verifyTypeName(currentParameter.get(0), currentParameter.get(1));
//            }
//            Property newProperty = PropertyFactory.getInstance().createMethodProperty(currentParameter.get(0),
//                    currentParameter.get(1), isFinal);
//            HashMap<String, HashMap<String, Property>> newHash = new HashMap<>();
//            HashMap<String, Property> oneNewHash = new HashMap<>();
//            oneNewHash.put(newProperty.getName(), newProperty);
//            newHash.put(newProperty.getType(), oneNewHash);
//            addProperties(newHash);
//        }
//    }
//
//    /**
//     * Verifies if the method is defined with equal parameter name
//     *
//     * @param line the given method parameters to verify
//     * @throws BadFormatException if the method has at least two parameters with the same name
//     */
//    private void sameMethodParametersName(String line) throws BadFormatException {
//        System.out.println(line);
//        String[] parameters = line.split(",");
//        String[] parametersName = new String[parameters.length];
//        for (int i = 0; i < parameters.length; i++) {
//            String[] singleParameter = parameters[i].split(" ");
//            if (singleParameter.length == 2) {
//                parametersName[i] = singleParameter[1];
//            } else { // length == 3 (with final)
//                parametersName[i] = singleParameter[2];
//            }
//        }
//
//        for (int i = 0; i < parametersName.length; i++) {
//            for (int j = i + 1; j < parametersName.length; j++) {
//                if (parametersName[i].equals(parametersName[j])) {
//                    throw new BadFormatException("Equal parameter names in the method deceleration");
//                }
//            }
//        }
//
//    }
//
//    /**
//     * Verifies if the given code line is a call to an existing method
//     *
//     * @param line   the given code line to verify
//     * @param method the method that the code line is inside
//     * @return true if the code line is a valid method call, else false
//     * @throws BadFormatException if the code line is an invalid existing method call
//     */
//    private boolean isCallToExistingMethod(String line, Method method) throws BadFormatException {
//        line = line.replace(" ", "");
//        String[] methodCall = line.split("\\(");
//        System.out.println(methodCall[0]);
//        System.out.println((method.getMethodName()));
//        if (methodCall[0].equals(method.getMethodName())) {
//            return true;
//        }
//        if (existingMethods.containsKey(methodCall[0])) { // The method exist
//            if (!(line.endsWith(");"))) {
//                throw new BadFormatException("The method call is invalid");
//            }
//
//            String methodParameters = getMethodParameters(line);
//            String[] parametersArray = methodParameters.split(",");
//
//            for (int i = 0; i < parametersArray.length; i++) {
//                String expectedParamType = method.getMethodParameterType()[i];
//                // verifies if the parameter exist in the method
//                if (method.getProperties().containsKey(expectedParamType)) {
//                    if (!(method.getProperties().get(expectedParamType).containsKey(parametersArray[i]))) {
//                        // the parameter does not exist in the method, verifies if it exist in the global scope
//                        if (globalProperties.containsKey(expectedParamType)) {
//                            if (!(globalProperties.get(expectedParamType).containsKey(parametersArray[i]))) {
//                                // the parameter does not exist in the global scope either
//                                throw new BadFormatException("The method call is invalid");
//                            }
//                        }
//                    }
//                } else { // the parameter type does not exist in the method
//                    if (globalProperties.containsKey(expectedParamType)) {
//                        if (!(globalProperties.get(expectedParamType).containsKey(parametersArray[i]))) {
//                            // the parameter does not exist in the global scope either
//                            throw new BadFormatException("The method call is invalid");
//                        }
//                    } else { // the parameter type does not exist in the global scope either
//                        throw new BadFormatException("The method call is invalid");
//                    }
//
//                }
//            }
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * TODO
//     *
//     * @param methodLines
//     * @param lineIndex
//     * @return
//     * @throws BadFormatException
//     */
//    private boolean isReturn(ArrayList<String> methodLines, int lineIndex) throws BadFormatException {
//        String returnLine = Utils.RemoveAllSpacesAtEnd(methodLines.get(lineIndex));
//        if (returnLine.equals("return;")) {
//            // if (methodLines.size() == lineIndex + 2) {
//            String nextLine = methodLines.get(lineIndex + 1).replace(" ", "");
//            if (!(nextLine.equals("}"))) {
//                throw new BadFormatException("The method return statement is invalid");
//            }
//            // } else { // There is more then one line after the return statement
//            // (after the return statement there should be one more line)
//            //throw new BadFormatException("The method end is invalid");
//            return true;
//        } else {
//            if (returnLine.contains("return")) {
//                throw new BadFormatException("The return statement is invalid");
//            }
//            return false;
//        }
//
//    }
//
//    /**
//     * Verifies if the code line is a block line - if/while condition
//     *
//     * @param line the given code line
//     * @return true if the line is an if or while line, else return false
//     */
//    private boolean isBlock(String line) {
//        line = line.replace(" ", "");
//        String[] blockLine = line.split("\\(");
//        if (blockLine[0].equals("if")) {
//            return true;
//        } else return blockLine[0].equals("while");
//    }
//
//    /**
//     * The given line is a block line, verifies if it is a while block line
//     *
//     * @param line the given block line
//     * @return true for a while condition, else false which means it is an if condition.
//     */
//    private boolean isWhile(String line) {
//        line = line.replace(" ", "");
//        String[] blockLine = line.split("\\(");
//        return blockLine[0].equals("while");
//    }
//}