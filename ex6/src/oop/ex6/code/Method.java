package oop.ex6.code;

import oop.ex6.Parser;
import oop.ex6.code.properties.Property;
import oop.ex6.exceptions.BadFormatException;

import java.util.ArrayList;
import java.util.HashMap;

public class Method {
    protected String name;
    protected HashMap<String, HashMap<String, Property>> properties;
    protected ArrayList<Method> executeMethods;

    public Method(String _name, HashMap<String, HashMap<String, Property>> _parameters) {
        this.name = _name;
        this.properties = _parameters;
        this.executeMethods = new ArrayList<>();
    }

    public void LoadMethodLine(ArrayList<String> lines) throws BadFormatException {
        for (String line : lines) {
            if(Parser.getInstance().isPropertyLine(line)) {
                HashMap<String, HashMap<String, Property>> lineProperties =
                        Parser.getInstance().getPropertyFromLine(line.substring(5));
            }
        }
    }
}
