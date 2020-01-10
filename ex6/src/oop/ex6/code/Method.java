package oop.ex6.code;

import oop.ex6.Validations;
import oop.ex6.code.properties.Property;
import oop.ex6.exceptions.BadFormatException;

import java.util.ArrayList;

public class Method {
    protected String methodLine;
    protected String methodName;
    protected ArrayList<Property> parameters;
    protected ArrayList<Property> properties;
    protected ArrayList<Method> executeMethods;
    protected ArrayList<Block> methodBlocks;
    private static final String BAD_METHOD_LINE = "The method line is invalid";


    /**
     * Constructor of method, verifies the method deceleration is valid.
     * @param methodLine the given method deceleration line
     * @param parameters
     * @throws BadFormatException
     */
    public Method(String methodLine, ArrayList<Property> parameters) throws BadFormatException {
        this.methodLine = methodLine;
        this.parameters = parameters;
        this.properties = new ArrayList<>();
        this.executeMethods = new ArrayList<>();
        extractMethodName();
        verifyMethodLine();
    }

    /**
     * Extracts the method name and saves it in the methodName data member
     * @throws BadFormatException when there is no '(' that opens the method parameter section
     */
    private void extractMethodName() throws BadFormatException {
        String[] splitMethodLine = this.methodLine.split(" ");

        for (int i = 0; i < splitMethodLine.length; i++) {
            if (splitMethodLine[1].charAt(i) == '(') {
                this.methodName = splitMethodLine[1].substring(0, i);
            }
        }
        if (this.methodName.isEmpty()) {
            throw new BadFormatException(BAD_METHOD_LINE);
        }
    }

    /**
     * Extracts the condition text from the condition line (example: if(condition){)
     * @return extracted method parameter text
     * @throws BadFormatException if there is no '()" for the condition
     */
    private String getMethodParameters() throws BadFormatException {
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
     * @param type the given parameter type to verify
     * @param name the given parameter name to verify
     * @throws BadFormatException if the type or name are invalid
     */
    private void verifyTypeName (String type, String name) throws BadFormatException {

        Validations.getValidations().validParameterType(type);
        Validations.getValidations().validParameterName(name, true);

    }

    /**
     * Verifies if the method line is valid
     * @throws BadFormatException if a syntax error is found
     */
    private void verifyMethodLine() throws BadFormatException {
        String methodParameters = getMethodParameters();

        if (methodParameters.contains(",,")) {
            throw new BadFormatException(BAD_METHOD_LINE);
        }

        String[] singleParameters = methodParameters.split(",");

        for (String parameter : singleParameters) {
            String[] currentParameter = parameter.split(" ");
            if(currentParameter.length > 3 || currentParameter.length ==1){
                throw new BadFormatException(BAD_METHOD_LINE);
            }else if(currentParameter.length == 3){
                if((!currentParameter[0].equals("final"))){
                    throw new BadFormatException(BAD_METHOD_LINE);
                }
                verifyTypeName(currentParameter [1],currentParameter[2]);
            }else{ // currentParameter.length == 2
                verifyTypeName(currentParameter[0],currentParameter[1]);
            }
        }
    }
}
