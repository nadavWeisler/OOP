package oop.ex6;

public class Utils {
    public static String RemoveAllSpacesAtEnd(String str){
        return str.replaceAll("([\\n\\r]+\\s*)*$", "");
    }

    public static enum blockType {IF_CONDITION, WHILE_LOOP}

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
