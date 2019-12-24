package filesprocessing;

import java.io.File;
import java.lang.reflect.Array;
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
    public final static ArrayList<String> FILTER_TYPES = new ArrayList<String>() {{
        add(GREATER_THEN_FILTER);
        add(BETWEEN_FILTER);
        add(SMALLER_THAN_FILTER);
        add(FILE_FILTER);
        add(CONTAINS_FILTER);
        add(PREFIX_FILTER);
        add(SUFFIX_FILTER);
        add(WRITABLE_FILTER);
        add(EXECUTABLE_FILTER);
        add(HIDDEN_FILTER);
        add(ALL_FILTER);
    }};

    public static final String ABS_ORDER = "abs";
    public static final String SIZE_ORDER = "size";
    public static final String TYPE_ORDER = "type";
    public final static ArrayList<String> ORDER_TYPES = new ArrayList<String>() {{
        add(ABS_ORDER);
        add(SIZE_ORDER);
        add(TYPE_ORDER);
    }};

    /**
     * Check if string is integer
     * @param str String
     * @return True if s is integer, false otherwise_
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /**
     * Get file name without extension
     *
     * @param file File
     * @return File name without extension
     */
    public static String getFileNameWithoutExtension(File file) {
        String fileName = "";

        try {
            if (file != null && file.exists()) {
                String name = file.getName();
                fileName = name.replaceFirst("[.][^.]+$", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fileName = "";
        }

        return fileName;

    }

    /**
     * Get file extension
     *
     * @param fileName File name
     * @return Get file extension
     */
    public static String GetFileExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }

        return extension;
    }
}
