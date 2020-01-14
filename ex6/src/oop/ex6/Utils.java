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


    // finals to use in the Utils
    private static final String STRING_TYPE = "String";
    private static final String INT_TYPE = "int";
    private static final String DOUBLE_TYPE = "double";
    private static final String CHAR_TYPE = "char";
    private static final String BOOLEAN_TYPE = "boolean";
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private static final String OPEN_BRACKET = "{";
    private static final String CLOSE_BRACKET = "}";
    private static final String END_BRACKET = ";";
    private static final String ILLEGAL_CODE = "Illegal code";


    /**
     * Removes all blank spaces from a given String
     * @param str the given String
     * @return String without blank spaces
     */
    public static String RemoveAllSpacesAtEnd(String str) {
        return str.trim();
    }


    /**
     * Verifies if a given String is an integer
     * @param str the given string
     * @return true if the string is an integer, else false
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException | NullPointerException e) {
            return true;
        }
        return false;
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
     * Verifies that the given parameter name is valid according to the S-java definition
     * @param name the given parameter name to verify
     * @param startWithLetter indicates if the name must start with a letter
     * @throws BadFormatException when the name is invalid
     */
    public static void validParameterName(String name, boolean startWithLetter) throws BadFormatException {
        if (name.length() == 0) {
            throw new BadFormatException(ILLEGAL_CODE);
        } else if (Pattern.matches(".*\\W+.*", name)) {
            throw new BadFormatException(ILLEGAL_CODE);
        } else if (Pattern.matches("\\d.*", name)) { //Name start with digit
            throw new BadFormatException(ILLEGAL_CODE);
        } else if (startWithLetter && !(Pattern.matches("[a-zA-Z].*", name))) {
            throw new BadFormatException(ILLEGAL_CODE);
        } else if (name.contains("_")) { // if the name contains _ then it has to contain at least
            // one more letter or digit
            if (!Pattern.matches(".*[a-zA-Z0-9]+.*", name)) {
                throw new BadFormatException(ILLEGAL_CODE);
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
        if (!(type.equals(STRING_TYPE) ||
                type.equals(INT_TYPE) ||
                type.equals(BOOLEAN_TYPE) ||
                type.equals(DOUBLE_TYPE) ||
                type.equals(CHAR_TYPE))) {
            throw new BadFormatException("Invalid parameter type");
        }
    }

    /**
     * Verifies that the value to be assigned into variable is legal according to the variable type
     * @param type the variable type
     * @param value the value to be assigned into the variable
     * @throws BadFormatException if the value does not match the variable type requirements
     */
    public void validValue(String type, String value) throws BadFormatException {
        switch (type) {
            case STRING_TYPE:
                if (!value.startsWith("\"") || value.endsWith("\"")) {
                    throw new BadFormatException(ILLEGAL_CODE);
                }
            case INT_TYPE:
                if (this.isInteger(value)) {
                    throw new BadFormatException(ILLEGAL_CODE);
                }
            case DOUBLE_TYPE:
                if (!this.isDouble(value)) {
                    throw new BadFormatException(ILLEGAL_CODE);
                }
            case CHAR_TYPE:
                if ((!value.startsWith("'") || value.endsWith("'")) &&
                        value.length() == 3) {
                    throw new BadFormatException(ILLEGAL_CODE);
                }
            case BOOLEAN_TYPE:
                if (!(value.equals(TRUE) || value.equals(FALSE) || isDouble(value))) {
                    throw new BadFormatException(ILLEGAL_CODE);
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
        if (!(cleanLine.endsWith(CLOSE_BRACKET) || cleanLine.endsWith(END_BRACKET) ||
                cleanLine.endsWith(OPEN_BRACKET))) {
            throw new BadFormatException(ILLEGAL_CODE);
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
     * Verifies if the given name is an existing property name
     * @param name the given name to verify
     * @param prop the existing properties hash map th verify in
     * @return Property object if exist, else return null
     */
    public static Property existInProperties(String name, HashMap<String, HashMap<String, Property>> prop) {
        for (String type : prop.keySet()) {
            for (String prop_name : prop.get(type).keySet()) {
                if (prop_name.equals(name)) {
                    return prop.get(type).get(name);
                }
            }
        }
        return null;
    }


}
