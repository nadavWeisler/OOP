package oop.ex6.code;

import oop.ex6.code.properties.Property;

import java.util.ArrayList;
import java.util.HashMap;

public class Method {
    protected String methodLine;
    protected String methodName;
    protected HashMap<String, HashMap<String, Property>> properties;
    protected ArrayList<Method> executeMethods;
    protected ArrayList<Block> methodBlocks;
    protected String [] parameterType;

    public String getMethodName() {
        return methodName;
    }


    /**
     * Constructor of method
     */
    public Method(String [] methodParameterType) {
        this.properties = new HashMap<>();
        this.executeMethods = new ArrayList<>();
        this.parameterType = methodParameterType;
    }

    public HashMap<String, HashMap<String, Property>> getProperties (){
        return properties;
    }

    public String [] getMethodParameterType (){
        return parameterType;
    }
}
