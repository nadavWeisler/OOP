package oop.ex6.code.properties;

/**
 * Super class that represent a variable
 */
public abstract class Property {
    protected String name;
    protected String type;
    protected boolean isFinal;
    protected boolean methodProperty;
    protected boolean isNull;

    /**
     * Constructor of Property
     * @param _name the given variable name
     * @param _type the given variable type
     * @param _isFinal true when the variable is declared final else false
     * @param _method true when the variable is local in a method else false, i.e is global
     */
    public Property(String _name, String _type, boolean _isFinal, boolean _method) {
        this.name = _name;
        this.type = _type;
        this.isFinal = _isFinal;
        this.methodProperty = _method;
    }

    /**
     * returns property name
     * @return property name
     */
    public String getName() {
        return name;
    }

    public boolean isMethodProperty(){
        return this.methodProperty;
    }

    public boolean isFinal(){
        return this.isFinal;
    }

    /**
     * returns property type
     * @return property type
     */
    public String getType() {
        return this.type;
    }
}




