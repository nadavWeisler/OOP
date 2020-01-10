package oop.ex6.code;

import oop.ex6.code.properties.Property;

import java.util.ArrayList;

public class Method {
    protected String name;
    protected ArrayList<Property> parameters;
    protected ArrayList<Property> properties;
    protected ArrayList<Method> executeMethods;

    public Method(String _name, ArrayList<Property> _parameters) {
        this.name = _name;
        this.parameters = _parameters;
        this.properties = new ArrayList<>();
        this.executeMethods = new ArrayList<>();
    }
}
