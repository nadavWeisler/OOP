package oop.ex6.parsers;

import oop.ex6.Utils;
import oop.ex6.code.properties.Property;
import oop.ex6.code.properties.PropertyFactory;
import oop.ex6.exceptions.BadFormatException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Extends Parser and parse the if/while code lines, uses the singleton design pattern
 */
public class BlockParser extends Parser {
    public enum blockType {IF_CONDITION, WHILE_LOOP}

    private BlockParser.blockType type;
    private String conditionLine;
    private HashMap<String, HashMap<String, Property>> method_property = new HashMap<>();
    private String[] booleanTypes = new String[]{"boolean", "int", "double"};

    /**
     * Returns the block type, i.e if block or while block
     *
     * @return
     */
    public BlockParser.blockType getType() {
        return type;
    }

    /**
     * Constructor for Block
     *
     * @param isWhile       defines the type of the block, if true then the block is a while loop, else the block
     *                      is an if condition
     * @param conditionLine the given conditionLine for the block
     */
    public BlockParser(boolean isWhile, String conditionLine,
                       ArrayList<HashMap<String, HashMap<String, Property>>> allProperties)
            throws BadFormatException {
        this.local_properties.put("int", new HashMap<>());
        this.local_properties.put("double", new HashMap<>());
        this.local_properties.put("String", new HashMap<>());
        this.local_properties.put("char", new HashMap<>());
        this.local_properties.put("boolean", new HashMap<>());
        if (isWhile) {
            type = BlockParser.blockType.WHILE_LOOP;
        } else {
            type = BlockParser.blockType.IF_CONDITION;
        }
        this.conditionLine = conditionLine;
        this.verifyCondition(allProperties);
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
    private void verifyCondition(ArrayList<HashMap<String, HashMap<String, Property>>> allProperties)
            throws BadFormatException {

        String conditionLine = this.conditionLine.replace(" ", "");

        // verifies that the condition line has the expected format
        conditionLine = conditionLine.replace(" ", "");
        if (Pattern.matches("(if|while)\\s?.*[(].*[)]\\s?.*[{]", conditionLine)) {
            String condition = getCondition(conditionLine);

            verifyConditionOperators(condition);

            //replace the OR condition to an AND condition in order to split according to '&&'
            condition = condition.replace("||", "&&");
            String[] conditionParameters = condition.split("&&");

            for (String parameter : conditionParameters) {
                parameter = Utils.RemoveAllSpacesAtEnd(parameter);
                // if the parameter is no the saved words 'true' 'false'
                Property currentProperty;
                if (!(parameter.equals("true") || parameter.equals("false")) && !Utils.isDouble(parameter)) {
                    boolean exist = false;
                    currentProperty = Utils.existInProperties(parameter, this.local_properties);
                    if (currentProperty != null) {
                        if (!PropertyFactory.getInstance().validTypesTo("boolean",
                                currentProperty.getType()) ||
                                PropertyFactory.getInstance().getValueFromProperty(currentProperty) == null) {
                            throw new BadFormatException("The block condition is invalid");
                        }
                        exist = true;
                    }
                    if (!exist) {
                        for (int i = allProperties.size() - 1; i >= 0; i--) {
                            currentProperty = Utils.existInProperties(parameter, allProperties.get(i));
                            if (currentProperty != null) {
                                if (!PropertyFactory.getInstance().validTypesTo("boolean",
                                        currentProperty.getType()) ||
                                        PropertyFactory.getInstance().getValueFromProperty(currentProperty) == null) {
                                    throw new BadFormatException("The block condition is invalid");
                                }
                                exist = true;
                            }
                        }
                    }
                    if (!exist) {
                        throw new BadFormatException("The block condition is invalid");
                    }
                }
            }
        } else {
            throw new BadFormatException("The block condition is invalid");
        }

    }

    /**
     * TODO
     *
     * @param properties
     * @throws BadFormatException
     */
    public void addPropertiesToBlock(ArrayList<Property> properties) throws BadFormatException {
        for (Property property : properties) {
            if (!localPropertyExist(property.getName())) {
                this.local_properties.get(property.getType()).put(property.getName(), property);
            } else {
                throw new BadFormatException("Property Already Exist");
            }
        }
    }

    /**
     * TODO
     *
     * @param newProperties
     * @return
     */
    public boolean propertiesExistInBlock(ArrayList<Property> newProperties) {
        for (Property property : newProperties) {
            if (this.localPropertyExist(property.getName())) {
                return true;
            }
        }
        return false;
    }
}
