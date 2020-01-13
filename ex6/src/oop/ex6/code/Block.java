package oop.ex6.code;

import oop.ex6.Utils;
import oop.ex6.code.properties.Property;
import oop.ex6.parsers.FileParser;
import oop.ex6.exceptions.BadFormatException;
import oop.ex6.parsers.OldParser;

import java.util.HashMap;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Block represent if/while section
 */
public class Block extends OldParser {

    public enum blockType {IF_CONDITION, WHILE_LOOP}

    private blockType type;
    private String conditionLine;
    private HashMap<String, HashMap<String, Property>> method_property = new HashMap<>();
    private String[] booleanTypes = new String[]{"boolean", "int", "double"};

    /**
     * Returns the block type, i.e if block or while block
     *
     * @return
     */
    public blockType getType() {
        return type;
    }

    /**
     * Constructor for Block
     *
     * @param isWhile       defines the type of the block, if true then the block is a while loop, else the block
     *                      is an if condition
     * @param conditionLine the given conditionLine for the block
     */
    public Block(boolean isWhile, String conditionLine, HashMap<String, HashMap<String, Property>> methodProperties)
            throws BadFormatException {
        if (isWhile) {
            type = blockType.WHILE_LOOP;
        } else {
            type = blockType.IF_CONDITION;
        }
        this.conditionLine = conditionLine;
        this.method_property = methodProperties;
        this.verifyCondition();
    }

    /**
     * Extracts the condition text from the condition line (example: if(condition){)
     *
     * @param line the given condition line
     * @return extracted condition text
     * @throws BadFormatException if there is no '()" for the condition
     */
    private String getCondition(String line) throws BadFormatException {
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '(') {
                for (int j = i; j < line.length(); j++) {
                    if (line.charAt(j) == ')') {
                        return line.substring(i + 1, j); // returns the condition itself
                    }
                }
                // The condition does not end with a closing bracket
                throw new BadFormatException("The block condition is invalid");
            }
        }
        //The line does not have an opening condition bracket
        throw new BadFormatException("The block condition is invalid");
    }

    /**
     * Verifies the operators in the condition
     *
     * @param condition thr given condition line to verify
     * @throws BadFormatException when found that the operator '&&' or '||' in an invalid way
     */
    private void verifyConditionOperators(String condition) throws BadFormatException {
        if (condition.contains("\"")) {
            throw new BadFormatException(" The condition contains string");
        }
        // Verifies if the condition line contains '||&&' or '&&||'
        // Verifies if the condition starts with '||' or '&&'
        if (Pattern.matches(".*(\\|\\|\\&\\&).*+", condition) ||
                Pattern.matches(".*(\\&\\&\\|\\|).*+", condition) ||
                Pattern.matches("(\\|\\|).*", condition) ||
                Pattern.matches("(\\&\\&).*", condition) ||
                Pattern.matches(".*(\\|\\|)", condition) ||
                Pattern.matches(".*(\\&\\&)", condition)) {
            throw new BadFormatException("The block condition is invalid");
        } else { // Verifies if the start/ends with '|' or '&'
            if (Pattern.matches("(\\&)[a-zA-Z0-9]+.*", condition) ||
                    Pattern.matches("(\\|)[a-zA-Z0-9]+.*", condition) ||
                    Pattern.matches(".*[a-zA-Z0-9]+(\\|)", condition) ||
                    Pattern.matches(".*[a-zA-Z0-9]+(\\&)", condition)) {
                throw new BadFormatException("The block condition is invalid");
            } else { // verifies if the condition contains a single '|' or '&'
                if (Pattern.matches(".*[a-zA-Z0-9]+(\\&)[a-zA-Z0-9]+.*", condition) ||
                        Pattern.matches(".*[a-zA-Z0-9]+(\\|)[a-zA-Z0-9]+.*", condition)) {
                    throw new BadFormatException("The block condition is invalid");
                }
            }

        }
    }

    /**
     * Verifies the block condition is valid
     *
     * @throws BadFormatException when the block condition is invalid
     */
    private void verifyCondition() throws BadFormatException {

        String conditionLine = this.conditionLine.replace(" ", "");

        // verifies that the condition line has the expected format
        if (Pattern.matches("(if|while)[(].*[)]\\s?[{]", conditionLine)) {
            String condition = getCondition(conditionLine);

            verifyConditionOperators(condition);

            //replace the OR condition to an AND condition in order to split according to '&&'
            condition = condition.replace("||", "&&");
            String[] conditionParameters = condition.split("&&");

            for (String parameter : conditionParameters) {
                // if the parameter is not a number
                if (!(Utils.isDouble(parameter) || Utils.isInteger(parameter))) {
                    // if the parameter is no the saved words 'true' 'false'
                    if (!(parameter.equals("true") || parameter.equals("false")) && !Utils.isDouble(parameter)) {
                        // if the parameter does not exist as a boolean,int or double
//                        if (!(FileParser.getInstance().propertyExistWithSomeTypes(booleanTypes, parameter) ||
//                                methodPropertyExistWithSomeTypes(booleanTypes, parameter) ||
//                                FileParser.getInstance().globalPropertyExistWithSomeTypes(
//                                        booleanTypes, parameter))) {
//                            throw new BadFormatException("The block condition is invalid");
//                        }
                    }
                }
            }
        } else {
            throw new BadFormatException("The block condition is invalid");
        }

    }

    private boolean methodPropertyExistWithType(String propertyType, String name) {
        if (this.method_property.containsKey(propertyType)) {
            Set<String> propertiesKeySet = this.method_property.keySet();
            return propertiesKeySet.contains(name);
        }
        return false;
    }

    public boolean methodPropertyExistWithSomeTypes(String[] types, String name) {
        for (String type : types) {
            if (methodPropertyExistWithType(type, name)) {
                return true;
            }
        }
        return false;
    }

    public void addPropertiesToBlock(HashMap<String, HashMap<String, Property>> toAdd) throws BadFormatException {
        addProperties(toAdd);
    }
}
