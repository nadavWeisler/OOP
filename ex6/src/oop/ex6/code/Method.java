package oop.ex6.code;

import oop.ex6.Utils;
import oop.ex6.Validations;
import oop.ex6.code.properties.Property;
import oop.ex6.code.properties.PropertyFactory;
import oop.ex6.exceptions.BadFormatException;

import java.util.ArrayList;
import java.util.HashMap;

public class Method {
    protected String methodLine;
    protected String methodName;
    protected HashMap<String, HashMap<String, Property>> properties;
    protected ArrayList<Method> executeMethods;
    protected ArrayList<Block> methodBlocks;

    public String getMethodName() {
        return methodName;
    }


    /**
     * Constructor of method
     */
    public Method() {
        this.properties = new HashMap<>();
        this.executeMethods = new ArrayList<>();
    }
}
