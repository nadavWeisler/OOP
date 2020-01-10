package oop.ex6.parsers;

import oop.ex6.Validations;
import oop.ex6.code.*;
import oop.ex6.code.properties.*;
import oop.ex6.exceptions.BadFormatException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;


public class FileParser extends Parser {
    private HashMap<String, Method> methods;
    private static FileParser parser = new FileParser();
    private final String VOID_CONSTANT = "void";
    private final String FINAL_CONSTANT = "final";
    private final String EMPTY_STRING = "";
    private final String BLANK_SPACE = " ";
    private final String COMMA = ",";
    private final String EQUALS = "=";


    private FileParser() {
    }

    public static FileParser getInstance() {
        return parser;
    }

    private ArrayList<String> fileToArrayList(String fileName) throws IOException {
        ArrayList<String> result = new ArrayList<String>();

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        while (br.ready()) {
            result.add(br.readLine());
        }

        return result;
    }

    public int ParseFile(String fileName) throws IOException {
        boolean insideMethod = false;
        ArrayList<Integer> conditionSwitch = new ArrayList<>();
        ArrayList<Integer> whileSwitch = new ArrayList<>();
        int switchCount = 0;
        ArrayList<String> currentMethod = new ArrayList<>();
        try {
            //Get file array list
            ArrayList<String> fileList = fileToArrayList(fileName);

            for (String line : fileList) {
                //Empty or Comment line
                if (this.isEmptyLine(line) || this.isCommentLine(line)) {
                    continue;
                }

                //The line has a valid suffix
                Validations.getValidations().hasCodeSuffix(line);

                if (this.isPropertyLine(line)) {
                    addProperties(getPropertyFromLine(line));
                }

                //Inside a method
                if (insideMethod) {
                    currentMethod.add(line);
                    if (this.isIfLine(line)) {
                        conditionSwitch.add(switchCount);
                        switchCount++;
                    } else if (this.isWhileLine(line)) {
                        whileSwitch.add(switchCount);
                        switchCount++;
                    } else if (this.isEnd(line)) {
                        if (conditionSwitch.isEmpty()) {
                            if (whileSwitch.isEmpty()) {
                                insideMethod = false;
                            } else {
                                whileSwitch.remove(whileSwitch.size() - 1);
                            }
                        } else {
                            if (whileSwitch.isEmpty()) {
                                conditionSwitch.remove(conditionSwitch.size() - 1);
                            } else {
                                int lastCondition = conditionSwitch.get(conditionSwitch.size() - 1);
                                int lastWhile = whileSwitch.get(whileSwitch.size() - 1);
                                if(lastCondition > lastWhile) {
                                    conditionSwitch.remove(conditionSwitch.size() - 1);
                                } else {
                                    whileSwitch.remove(whileSwitch.size() - 1);
                                }
                            }
                        }
                    }
                }

                if (this.isMethodLine(line)) {
                    currentMethod.add(line);
                    insideMethod = true;
                }

                if (ifUpdatePropertyLine(line)) {
                    String noneBlankLine = line.replace(BLANK_SPACE, EMPTY_STRING);
                    String[] splitLine = noneBlankLine.split(EQUALS);
                    if (splitLine.length != 2) {
                        throw new BadFormatException("Bad initialize of parameter");
                    }

                    if (PropertyFactory.getInstance().validValue(splitLine[1])) {

                    } else {
                        if (GetPropertyTypeOptions(splitLine[1]).size() > 0) {
                            String type = getSameType(splitLine[0], splitLine[1]);
                            if (type.equals(EMPTY_STRING)) {
                                throw new BadFormatException("Bad Parameter");
                            } else {
                                Property toUpdate = this.properties.get(type).get(splitLine[0]);
                                Property fromUpdate = this.properties.get(type).get(splitLine[1]);
                                replaceProperty(splitLine[0], type,
                                        PropertyFactory.getInstance().updatePropertyFromOtherProperty(toUpdate,
                                                fromUpdate));
                            }
                        } else {
                            throw new BadFormatException("Not Same Type");
                        }
                    }
                }
            }
        } catch (BadFormatException exp) {
            System.err.println(exp.getMessage());
        }

        return 0;
    }



    public boolean isMethodLine(String line) {
        String[] splitLine = line.split(BLANK_SPACE);
        if (splitLine.length > 0) {
            return splitLine[0].equals(VOID_CONSTANT);
        }
        return false;
    }




}