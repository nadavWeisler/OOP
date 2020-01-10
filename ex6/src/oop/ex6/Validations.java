package oop.ex6;

import oop.ex6.exceptions.BadFormatException;

import javax.xml.xpath.XPath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Validations {
    private static Validations validations = new Validations();

    boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    private Validations() {
    }

    public static Validations getValidations() {
        return validations;
    }

    public boolean isComment(String line) throws BadFormatException {
        if (line.startsWith("//")) {
            return true;
        } else {
            String[] splitLine = line.split(" ");
            if (splitLine.length > 0 && splitLine[0].startsWith("//")) {
                throw new BadFormatException("Illegal comment line");
            }
        }
        return false;
    }

    public void hasCodeSuffix(String line) throws BadFormatException {
        String[] splitLine = line.split(" ");
        if (!(splitLine[splitLine.length - 1].equals("}") ||
                splitLine[splitLine.length - 1].equals(";") ||
                splitLine[splitLine.length - 1].equals("{"))) {
            throw new BadFormatException("Code line has no valid suffix");
        }
    }

    public void validParameterName(String name, boolean startWithLetter) throws BadFormatException {
        if (name.length() == 0) {
            throw new BadFormatException("Empty name");
        } else if (Pattern.matches(".*\\W+.*", name)) {
            throw new BadFormatException("Invalid name character exists");
        } else if (Pattern.matches("\\d.*", name)) { //Name start with digit
            throw new BadFormatException("Parameter Name cannot start with a digit");
        } else if (startWithLetter && !(Pattern.matches("[a-zA-Z].*", name))) {
            throw new BadFormatException("Method name must start with letter");
        } else if (name.contains("_")) { // if the name contains _ then it has to contain at least
            // one more letter or digit
            if (!Pattern.matches(".*[a-zA-Z0-9]+.*", name)) {
                throw new BadFormatException("The parameter name contains only '_' characters");
            }
        }
    }

    public void validParameterType(String type) throws BadFormatException {
        if (!(type.equals("String") ||
                type.equals("int") ||
                type.equals("boolean") ||
                type.equals("double") ||
                type.equals("char"))) {
            throw new BadFormatException("Invalid parameter type");
        }
    }

    public void validValue(String type, String value) throws BadFormatException {
        switch (type) {
            case "String":
                if (!value.startsWith("\"") || value.endsWith("\"")) {
                    throw new BadFormatException("String value is invalid");
                }
            case "int":
                if (!this.isInteger(value)) {
                    throw new BadFormatException("int value is invalid");
                }
            case "double":
                if (!this.isDouble(value)) {
                    throw new BadFormatException("double value is invalid");
                }
            case "char":
                if ((!value.startsWith("'") || value.endsWith("'")) &&
                        value.length() == 3) {
                    throw new BadFormatException("char value is invalid");
                }
            case "boolean":
                if (!(value.equals("true") || value.equals("false") || isDouble(value))) {
                    throw new BadFormatException("Boolean value is invalid");
                }
        }
    }

    public void validMethodParameters(String parameters) throws BadFormatException {
        String[] splitParameters = parameters.split(",");
        for (String parameter : splitParameters) {
            String[] splitParam = parameter.split(" ");
            if (splitParam[0].equals("final")) {
                if (splitParam.length != 3) {
                    throw new BadFormatException("Invalid method parameters");
                }
                splitParam = new String[]{
                        splitParam[1],
                        splitParam[2]
                };
            }
            if (splitParam.length != 2) {
                throw new BadFormatException("Invalid method parameters");
            }
            this.validParameterType(splitParam[0]);
            this.validValue(splitParam[0], splitParam[1]);
        }
    }

    public boolean isChar(String str) {
        return (str.startsWith("'") && !str.endsWith("'")) ||
                str.length() != 3;
    }

    public boolean isString(String str) {
        return str.startsWith("\"") && !str.endsWith("\"");
    }

    public boolean isBoolean(String str) {
        return str.equals("true") || str.equals("false");
    }
}
