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

    private MethodParser() {

    }

    private static MethodParser methodParser = new MethodParser();

    public static MethodParser getInstance() {
        return methodParser;
    }

    public Method parseMethod(ArrayList<String> lines,
                              HashMap<String, HashMap<String, Property>> globalProperties)
            throws BadFormatException {
        Method newMethod = new Method();
        this.globalProperties = globalProperties;
        String methodName = extractMethodName(lines.get(0));
        verifyMethodLine(methodName);
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
     *
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
     *
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
     * Verifies the method parameter type and name is according to syntax
     *
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
     *
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
}
