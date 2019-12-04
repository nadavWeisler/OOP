import java.util.Set;

public class Utils {
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

    /**
     * Generate error string for tests
     * @param count Test count
     * @param obj Test Class
     * @param func Test function
     * @return String
     */
    static String GenerateTestString(int count, String obj, String func) {
        return obj + ": " + "Function: " + func + " Test " + count + " failed.";
    }

    /**
     * Convert double set to double array
     * @param doubleSet Double set
     * @return Double array
     */
    static double[] DoubleSetToArray(Set<Double> doubleSet){
        double[] result = new double[doubleSet.size()];
        int count = 0;
        for (Double num : doubleSet) {
            result[count] = num;
            count++;
        }
        return result;
    }

    /**
     * Convert int set to in array
     * @param intSet Int set
     * @return Int array
     */
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
