import oop.ex3.spaceship.Item;

import java.util.Iterator;
import java.util.Set;

public class Utils {
    public static boolean StringInStringArray(String str, String[] strArray) {
        for (String s :
                strArray) {
            if (s.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return true if int array contains int
     *
     * @param num      int
     * @param numArray int array
     * @return boolean
     */
    static boolean IntInIntArray(int num, int[] numArray) {
        for (int n :
                numArray) {
            if (n == num) {
                return true;
            }
        }
        return false;
    }

    static boolean ItemContainInArray(Item[] array, Item item) {
        for (Item i :
                array) {
            if (i == item) {
                return true;
            }
        }
        return false;
    }

    static String GenerateTestString(int count, String obj, String func) {
        return obj + ": " + "Function: " + func + " Test " + count + " failed.";
    }

    static int[] IntSetToArray(Set<Integer> intSet){
        int[] result = new int[intSet.size()];
        int count = 0;
        for (Integer num : intSet) {
            result[count] = num;
            count++;
        }
        return result;
    }

}
