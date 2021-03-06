package filesprocessing;

import java.io.*;
import java.util.ArrayList;

public class Utils {
    public static final String GREATER_THEN_FILTER = "greater_than";
    public static final String BETWEEN_FILTER = "between";
    public static final String SMALLER_THAN_FILTER = "smaller_than";
    public static final String FILE_FILTER = "file";
    public static final String CONTAINS_FILTER = "contains";
    public static final String PREFIX_FILTER = "prefix";
    public static final String SUFFIX_FILTER = "suffix";
    public static final String WRITABLE_FILTER = "writable";
    public static final String EXECUTABLE_FILTER = "executable";
    public static final String HIDDEN_FILTER = "hidden";
    public static final String ALL_FILTER = "all";

    public static final String ABS_ORDER = "abs";
    public static final String SIZE_ORDER = "size";
    public static final String TYPE_ORDER = "type";

    public static final String NOT_SUFFIX = "NOT";
    public static final String REVERSE_SUFFIX = "REVERSE";
    public static final String SEPARATOR = "#";

    public static final String FILTER_SECTION = "FILTER";
    public static final String ORDER_SECTION = "ORDER";

    public static final String Yes = "YES";
    public static final String No = "NO";

    /**
     * Check if string is integer
     *
     * @param str String
     * @return True if s is integer, false otherwise_
     */
    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
        } catch (NullPointerException e) {
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Get file extension
     *
     * @param fileName File name
     * @return Get file extension
     */
    public static String getFileExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }

        return extension;
    }

    /**
     * Reverse files array list
     *
     * @param lst Files array list
     */
    public static void reverseFilesArrayList(ArrayList<File> lst) {
        for (int i = 0; i < lst.size() / 2; i++) {
            File temp = lst.get(i);
            lst.set(i, lst.get(lst.size() - i - 1));
            lst.set(lst.size() - i - 1, temp);
        }
    }

    /**
     * Sort string array
     *
     * @param strArray String array
     */
    public static void BubbleSortStringArray(String[] strArray) {
        String temp;
        boolean switched = true;
        for (int i = 0; i < strArray.length - 1 && switched; ++i) {
            switched = false;
            for (int j = 0; j < strArray.length - i - 1; ++j) {
                if (strArray[j].compareTo(strArray[j + 1]) > 0) {
                    temp = strArray[j];
                    strArray[j] = strArray[j + 1];
                    strArray[j + 1] = temp;
                    switched = true;
                }
            }
        }
    }

    /**
     * Long bubble sort
     *
     * @param numArray Long array
     */
    public static void longBubbleSort(Long[] numArray) {
        long n = numArray.length;
        long temp;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (numArray[j - 1] > numArray[j]) {
                    temp = numArray[j - 1];
                    numArray[j - 1] = numArray[j];
                    numArray[j] = temp;
                }
            }
        }
    }

    /**
     * Return all lines of file in string array list
     *
     * @param fileName File name
     * @return String array list
     */
    public static ArrayList<String> fileToArrayList(String fileName) {
        ArrayList<String> result = new ArrayList<String>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            while (br.ready()) {
                result.add(br.readLine());
            }

            return result;
        } catch (FileNotFoundException e) {
            return new ArrayList<String>();
        } catch (IOException e) {
            return new ArrayList<String>();
        }
    }

}
