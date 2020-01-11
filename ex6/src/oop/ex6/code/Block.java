package oop.ex6.code;

import oop.ex6.Utils;
import oop.ex6.parsers.FileParser;
import oop.ex6.Validations;
import oop.ex6.exceptions.BadFormatException;

import java.util.regex.Pattern;

public class Block {
    private Utils.blockType type;
    private String conditionLine;

    public Utils.blockType getType() {
        return type;
    }

    public boolean addLine(String line) {
        return false;
    }

    /**
     * Constructor for Block
     * @param isWhile defines the type pf the block, if true then the block is a while loop, else the block
     * is an if condition
     * @param conditionLine the given conditionLine for the block
     */
    public Block(boolean isWhile, String conditionLine) {
        if (isWhile) {
            type = Utils.blockType.WHILE_LOOP;
        } else {
            type = Utils.blockType.IF_CONDITION;
        }
        this.conditionLine = conditionLine;
    }

    /**
     * Extracts the condition text from the condition line (example: if(condition){)
     * @param line the given condition line
     * @return extracted condition text
     * @throws BadFormatException if there is no '()" for the condition
     */
    private String getCondition(String line) throws BadFormatException {
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '(') {
                for (int j = i; j < line.length(); j++) {
                    if (line.charAt(i) == ')') {
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
     * @param condition thr given condition line to verify
     * @throws BadFormatException when found that the operator '&&' or '||' in an invalid way
     */
    private void verifyConditionOperators(String condition) throws BadFormatException {

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


    private void verifyCondition() throws oop.ex6.exceptions.BadFormatException {

        String conditionLine = this.conditionLine.replace(" ", "");

        // verifies that the condition line has the expected format
        if (Pattern.matches("[if(].*[){]", conditionLine)) {
            String condition = getCondition(conditionLine);

            verifyConditionOperators(condition);

            //replace the OR condition to an AND condition in order to split according to '&&'
            condition = condition.replace("||", "&&");
            String[] conditionParameters = condition.split("&&");

            for (String parameter : conditionParameters) {
                // if the parameter is not a number
                if (!(Validations.getValidations().isDouble(parameter) ||
                        Validations.getValidations().isInteger(parameter))) {
                    // if the parameter is no the saved words 'true' 'false'
                    if (!(parameter.equals("true") || parameter.equals("false"))) {
                        // if the parameter does not exist as a boolean,int or double
                        if (!(FileParser.getInstance().propertyExistWithType("boolean", parameter) ||
                                FileParser.getInstance().propertyExistWithType("int", parameter) ||
                                FileParser.getInstance().propertyExistWithType("double", parameter))) {
                            throw new BadFormatException("The block condition is invalid");
                        }
                    }
                }
            }
        } else {
            throw new BadFormatException("The block condition is invalid");
        }
    }
}
