package filesprocessing;

import java.io.File;

public class Filter {

    public Filter() {

    }

    /**
     * Constructor
     *
     * @param filterType Filter type
     */
    public Filter(String filterType) {
        switch (filterType) {

        }
    }

    public boolean FilterFile(File file, String type) {
        return true;
    }

    /**
     * Greater than filter
     *
     * @param file File to filter
     * @param num  Number
     * @return True if file is bigger from num, False otherwise
     */
    private boolean GreaterThanFilter(File file, double num) {
        return file.length() > num;
    }

    /**
     * Between filter
     *
     * @param file     File to filter
     * @param smallNum Small number
     * @param bigNum   Big number
     * @return True if file size is between both numbers, False otherwise
     */
    private boolean BetweenFilter(File file, double smallNum, double bigNum) {
        return file.length() >= smallNum && file.length() <= bigNum;
    }

    /**
     * Smaller than filter
     *
     * @param file File to filter
     * @param num  Number
     * @return True if file size is smaller than num, False otherwise
     */
    private boolean SmallerThanFilter(File file, double num) {
        return file.length() < num;
    }

    /**
     * File name filter
     *
     * @param file  File to filter
     * @param value String value
     * @return True if file name equal to value, False otherwise
     */
    private boolean FileFilter(File file, String value) {
        return Utils.getFileNameWithoutExtension(file).equals(value);
    }

    /**
     * Contains in filename filter
     *
     * @param file  File to filter
     * @param value String value
     * @return True if file name contain value, false otherwise
     */
    private boolean ContainFilter(File file, String value) {
        return Utils.getFileNameWithoutExtension(file).contains(value);
    }

    /**
     * Prefix filename filter
     *
     * @param file  File to filter
     * @param value String value
     * @return True if file name start with value, false otherwise
     */
    private boolean PrefixFilter(File file, String value) {
        return Utils.getFileNameWithoutExtension(file).startsWith(value);
    }

    /**
     * Suffix filename
     *
     * @param file  File to filter
     * @param value String value
     * @return True if file name end with value, false otherwise
     */
    private boolean SuffixFilter(File file, String value) {
        return Utils.getFileNameWithoutExtension(file).endsWith(value);
    }

    /**
     * Writeable filter
     *
     * @param file File to filter
     * @return True if file is writable, false otherwise
     */
    private boolean WritableFilter(File file) {
        return file.canWrite();
    }

    /**
     * Executable filter
     *
     * @param file File to filter
     * @return True if file is executable, false otherwise
     */
    private boolean ExecutableFilter(File file) {
        return file.canExecute();
    }

    /**
     * Hidden filter
     *
     * @param file File to filter
     * @return True if file is hidden, false otherwise
     */
    private boolean HiddenFilter(File file) {
        return file.isHidden();
    }

    /**
     * All filter
     *
     * @param file file to filter
     * @return Returns True
     */
    private boolean AllFilter(File file) {
        return true;
    }
}
