package oop.ex6.parsers;

import oop.ex6.Utils;
import oop.ex6.code.properties.Property;
import oop.ex6.code.properties.PropertyFactory;
import oop.ex6.exceptions.BadFormatException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Properties;

public class Parser {
    protected final String FINAL_CONSTANT = "final";
    protected final String EMPTY_STRING = "";
    protected final String BLANK_SPACE = " ";
    protected final String COMMA = ",";
    protected final String EQUALS = "=";
    protected final String WHILE_CONSTANT = "while";
    protected final String If_CONSTANT = "if";

    /**
     * TODO
     *
     * @param line
     * @return
     */
    protected boolean isPropertyLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            return PropertyFactory.getInstance().isPropertyType(splitLine[0]) || splitLine[0].equals(FINAL_CONSTANT);
        }
        return false;
    }

    /**
     * Verifies if the given code line is a while loop line
     *
     * @param line the given code line to verify
     * @return true if the code line is a start of a while loop else false
     */
    protected boolean isWhileLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            return splitLine[0].equals(WHILE_CONSTANT);
        }
        return false;
    }

    /**
     * Verifies if the given code line is a if condition line
     *
     * @param line the given code line to verify
     * @return true if the code line is a start of a if condition line else false
     */
    protected boolean isIfLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            return splitLine[0].equals(If_CONSTANT);
        }
        return false;
    }

    /**
     * Verifies if the code line ends with the bracket '}'
     *
     * @param line the given line to verify
     * @return true if the line end with the bracket '}' , else false
     */
    protected boolean isEnd(String line) {
        return line.replace(" ", "").equals("}");
    }

    protected ArrayList<Property> getPropertiesFromLine(String line) throws BadFormatException {
        line = Utils.RemoveAllSpacesAtEnd(line);
        int firstIndex = 0;
        int spaceIndex = line.indexOf(BLANK_SPACE);
        String currentString = line.substring(firstIndex, spaceIndex);
        boolean isFinal = false;
        String type;
        if (currentString.equals(FINAL_CONSTANT)) {
            isFinal = true;
            spaceIndex = line.substring(spaceIndex + 1).indexOf(BLANK_SPACE);
            if (spaceIndex == -1) {
                throw new BadFormatException("BAD PROPERTY LINE");
            }
            line = Utils.RemoveAllSpacesAtEnd(line.substring(6));
        }

        firstIndex = 0;
        spaceIndex = line.indexOf(BLANK_SPACE);
        currentString = line.substring(firstIndex, spaceIndex);

        if (PropertyFactory.getInstance().isPropertyType(currentString)) {
            type = currentString;
            line = Utils.RemoveAllSpacesAtEnd(line.substring(spaceIndex));
            spaceIndex = line.indexOf(BLANK_SPACE);
            if (spaceIndex == -1) {
                throw new BadFormatException("BAD PROPERTY LINE");
            }

        } else {
            throw new BadFormatException("BAD PROPERTY LINE");
        }
        System.out.println(line);
        String[] splitLine = line.split(COMMA);
        ArrayList<String> waitForValue = new ArrayList<>();
        ArrayList<Property> result = new ArrayList<>();
        boolean ended = false;
        for (String item : splitLine) {
            item = Utils.RemoveAllSpacesAtEnd(item);
            if (item.endsWith(";")) {
                if (!Utils.RemoveAllSpacesAtEnd(splitLine[splitLine.length - 1]).equals(item)) {
                    throw new BadFormatException("BAD PROPERTY LINE");
                } else {
                    item = item.substring(0, item.length() - 1);
                    ended = true;
                }
            }
            if (item.contains(EQUALS)) {
                String[] splitItem = item.split(EQUALS);
                if (splitItem.length != 2) {
                    throw new BadFormatException("BAD PROPERTY NAME");
                }
                String name = Utils.RemoveAllSpacesAtEnd(splitItem[0]);
                String value = Utils.RemoveAllSpacesAtEnd(splitItem[1]);
                if (!PropertyFactory.getInstance().validParameterName(name)) {
                    throw new BadFormatException("BAD PROPERTY NAME");
                } else if (!PropertyFactory.getInstance().validValue(type, value)) {
                    System.out.println(value);
                    throw new BadFormatException("BAD PROPERTY VALUE");
                } else {
                    for (String waitItem : waitForValue) {
                        result.add(PropertyFactory.getInstance().createProperty(type, waitItem, value, isFinal));
                    }
                    result.add(PropertyFactory.getInstance().createProperty(type, name, value, isFinal));
                }
            } else {
                if (!PropertyFactory.getInstance().validParameterName(item)) {
                    throw new BadFormatException("BAD PROPERTY NAME");
                } else {
                    waitForValue.add(item);
                }
            }
        }

        if(!ended) {
            throw new BadFormatException("; missing");
        }

        return result;
    }

    public static void main(String[] args) {
        try {
            String x = " double      d     =          3.2;";
            ArrayList<Property> t = new Parser().getPropertiesFromLine(x);
            for (Property t1 : t) {
                System.out.println(t1.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
