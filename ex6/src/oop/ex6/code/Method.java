package oop.ex6.code;

import oop.ex6.code.properties.Property;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Method class represent a method section
 */
public class Method {

    protected String methodLine;
    protected String methodName;
    protected HashMap<String, HashMap<String, Property>> properties;
    protected ArrayList<Method> executeMethods;
    protected ArrayList<Block> methodBlocks;
    protected String [] parameterType;

    /**
     * Returns method name
     * @return method name
     */
    public String getMethodName() {
        return methodName;
    }


    /**
     * Constructor of Method
     * @param methodParameterType an array that represent the method parameter types according to the order
     * in the method deceleration
     * @param name the method name
     */
    public Method(String [] methodParameterType, String name) {

        this.properties = new HashMap<>();
        this.executeMethods = new ArrayList<>();
        this.parameterType = methodParameterType;
        this.methodName = name;
    }


    /**
     * Returns the methods properties
     * @return method properties
     */
    public HashMap<String, HashMap<String, Property>> getProperties (){
        return properties;
    }

    /**
     * Returns the method parameter type array
     * @return method parameter type array according to the order of deceleration in the method
     */
    public String [] getMethodParameterType (){
        return parameterType;
    }
}
