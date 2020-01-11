package oop.ex6.parsers;

import oop.ex6.Utils;
import oop.ex6.code.*;
import oop.ex6.exceptions.BadFormatException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class FileParser extends Parser {
    private HashMap<String, Method> methods = new HashMap<>();
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

    private boolean methodExist(Method method) {
        return this.methods.containsKey(method.getMethodName());
    }

    public int ParseFile(String fileName) throws IOException, BadFormatException {
        boolean insideMethod = false;
        ArrayList<Integer> conditionSwitch = new ArrayList<>();
        ArrayList<Integer> whileSwitch = new ArrayList<>();
        int switchCount = 0;
        Method newMethod;
        ArrayList<String> methodList = new ArrayList<>();

        //Get file array list
        ArrayList<String> fileList = fileToArrayList(fileName);

        for (String line : fileList) {
            //Empty or Comment line
            if (this.isEmpty(line) || this.isComment(line)) {
                continue;
            }

            //The line has a valid suffix
            Utils.hasCodeSuffix(line);

            //Inside a method
            if (insideMethod) {
                methodList.add(line);
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
                            newMethod = MethodParser.getInstance().parseMethod(methodList, this.properties);
                            if (this.methodExist(newMethod)) {
                                throw new BadFormatException("The method already exist");
                            } else {
                                this.methods.put(newMethod.getMethodName(), newMethod);
                                methodList = new ArrayList<>();
                            }
                        } else {
                            whileSwitch.remove(whileSwitch.size() - 1);
                        }
                    } else {
                        if (whileSwitch.isEmpty()) {
                            conditionSwitch.remove(conditionSwitch.size() - 1);
                        } else {
                            int lastCondition = conditionSwitch.get(conditionSwitch.size() - 1);
                            int lastWhile = whileSwitch.get(whileSwitch.size() - 1);
                            if (lastCondition > lastWhile) {
                                conditionSwitch.remove(conditionSwitch.size() - 1);
                            } else {
                                whileSwitch.remove(whileSwitch.size() - 1);
                            }
                        }
                    }
                }
            } else if (this.isPropertyLine(line)) {
                addProperties(getPropertyFromLine(line));
            } else if (this.isMethodLine(line)) {
                methodList.add(line);
                insideMethod = true;
            } else {
                throw new BadFormatException("Invalid line");
            }

            if (ifAssignPropertyLine(line)) {
                this.assignProperty(line);
            }
        }

        if(insideMethod) {
            throw new BadFormatException("Method did not close");
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