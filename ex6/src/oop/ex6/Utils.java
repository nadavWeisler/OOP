package oop.ex6;

import oop.ex6.code.properties.Property;
import oop.ex6.exceptions.BadFormatException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Utils class, contains different method for the usage of all the classes
 */
public class Utils {

    /**
     * Removes all blank spaces from a given String
     * @param str the given String
     * @return String without blank spaces
     */
    public static String RemoveAllSpacesAtEnd(String str) {
        return str.trim();
    }


    public static void printList(String[] lst) {
        for (String line : lst) {
            System.out.print(line + " ");
        }
    }

//
//    public static void printProperties(HashMap<String, HashMap<String, Property>> p) {
//        for (String type : p.keySet()) {
//            System.out.println("Type:" + type);
//            for (String name : p.get(type).keySet()) {
//                System.out.println("_" + name);
//            }
//        }
//    }

    /**
     * Verifies if a given String is an integer
     * @param str the given string
     * @return true if the string is an integer, else false
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Verifies if a given String is a double
     * @param str the given string
     * @return true if the string is a double, else false
     */
    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * Verifies if a given String is a char
     * @param str the given string
     * @return true if the string is a char, else false
     */
    public boolean isChar(String str) {
        return (str.startsWith("'") && !str.endsWith("'")) ||
                str.length() != 3;
    }

    /**
     * Verifies if a given String is a String
     * @param str the given string
     * @return true if the string is a String, else false
     */
    public boolean isString(String str) {
        return str.startsWith("\"") && !str.endsWith("\"");
    }

    /**
     * Verifies if a given String is a boolean value
     * @param str the given string
     * @return true if the string is a boolean value, else false
     */
    public boolean isBoolean(String str) {
        return str.equals("true") || str.equals("false");
    }

    /**
     * Verifies that the given parameter name is valid according to the S-java definition
     * @param name the given parameter name to verify
     * @param startWithLetter indicates if the name must start with a letter
     * @throws BadFormatException when the name is invalid
     */
    public static void validParameterName(String name, boolean startWithLetter) throws BadFormatException {
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

    /**
     * Verifies that the given parameter type is valid, i.e it is one of the following:
     * String, char, boolean, int, double
     * @param type the given String to verify
     * @throws BadFormatException when the type is invalid
     */
    public static void validParameterType(String type) throws BadFormatException {
        if (!(type.equals("String") ||
                type.equals("int") ||
                type.equals("boolean") ||
                type.equals("double") ||
                type.equals("char"))) {
            throw new BadFormatException("Invalid parameter type");
        }
    }

    /**
     * Verifies that the value to be assigned into variable is legal according to the variable type
     * @param type  the variable type
     * @param value the value to be assigned into the variable
     * @throws BadFormatException if the value does not match the variable type requirements
     */
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

    /**
     * Verifies if the code line has one of the following suffix: '}','{',';'
     * @param line the given code line to verify
     * @throws BadFormatException when the code line does not end with one of the necessary suffix
     */
    public static void hasCodeSuffix(String line) throws BadFormatException {
        String cleanLine = Utils.RemoveAllSpacesAtEnd(line);
        if (!(cleanLine.endsWith("}") || cleanLine.endsWith(";") || cleanLine.endsWith("{"))) {
            throw new BadFormatException("Code line has no valid suffix");
        }
    }

    /**
     * Clears the code line array list from empty code lines
     * @param strings the code lines array list
     * @return code lines array list without emty code lines
     */
    public static ArrayList<String> cleanWhiteSpace(String[] strings) {
        ArrayList<String> result = new ArrayList<>();
        for (String str : strings) {
            if (!str.isEmpty() && !str.isBlank() && !str.equals("\n") && !str.equals("\r") &&
                    !Pattern.matches("(\\s|\\n|\\r)+", str)) {
                result.add(str);
            }
        }
        return result;
    }

    /**
     * TODO
     * @param parameters
     * @throws BadFormatException
     */
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

    /**
     * Converts the text file into an array list of String, each element represent a code line of the file
     * @param fileName the given file name to convert to an array list
     * @return an array list of String code lines from the file
     * @throws IOException if the given file is invalid
     */
    public static ArrayList<String> fileToArrayList(String fileName) throws IOException {
        ArrayList<String> result = new ArrayList<String>();

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        while (br.ready()) {
            result.add(br.readLine());
        }

        return result;
    }

    /**
     * Returns a String with only one blank space between words
     * @param str the given string to perform the action on
     * @return a String with only one blank spaces between words
     */
    public static String getOnlyOneBlank(String str) {
        return str.replace("  ", " ");
    }

    /**
     * TODO
     * @param name
     * @param prop
     * @return
     */
    public static Property existInProperties(String name, HashMap<String, HashMap<String, Property>> prop) {
        for (String type : prop.keySet()) {
            for (String prop_name : prop.get(type).keySet()) {
                if(prop_name.equals(name)) {
                    return prop.get(type).get(name);
                }
            }
        }
        return null;
    }


}
