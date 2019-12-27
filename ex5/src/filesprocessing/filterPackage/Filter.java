package filesprocessing.filterPackage;

import filesprocessing.Utils;

import java.io.File;

public class Filter {
    private String filterType;

    private boolean not;

    private double num1;

    private double num2;

    private String str;

    private boolean yesNo;

    /**
     * Basic Constructor
     *
     * @param filterType Filter type
     * @param not        Not
     */
    public Filter(String filterType, boolean not) {
        this.filterType = filterType;
        this.not = not;
    }

    /**
     * Constructor for double
     *
     * @param filterType Filter type
     * @param not        Not
     * @param num        Double
     */
    public Filter(String filterType, boolean not, double num) {
        this(filterType, not);
        this.num1 = num;
    }

    /**
     * Constructor for two double values
     *
     * @param filterType Filter type
     * @param not        Not
     * @param n1         double 1
     * @param n2         double 2
     */
    public Filter(String filterType, boolean not, double n1, double n2) {
        this(filterType, not);
        this.num1 = n1;
        this.num2 = n2;
    }

    /**
     * Constructor for string value
     *
     * @param filterType Filter type
     * @param not        Not
     * @param str        Str value
     */
    public Filter(String filterType, boolean not, String str) {
        this(filterType, not);
        this.str = str;
    }

    /**
     * Constructor for boolean value
     *
     * @param filterType Filter type
     * @param not        Not
     * @param yesNo      Yes or no
     */
    public Filter(String filterType, boolean not, boolean yesNo) {
        this(filterType, not);
        this.yesNo = yesNo;
    }

    /**
     * Filter file with num
     *
     * @param file File to filter
     * @return Filter result
     */
    public boolean FilterFile(File file) {
        boolean result = false;
        switch (filterType) {
            case Utils.GREATER_THEN_FILTER:
                result = this.GreaterThanFilter(file);
                break;
            case Utils.SMALLER_THAN_FILTER:
                result = this.SmallerThanFilter(file);
                break;
            case Utils.BETWEEN_FILTER:
                result = this.BetweenFilter(file);
                break;
            case Utils.FILE_FILTER:
                result = this.FileFilter(file);
                break;
            case Utils.CONTAINS_FILTER:
                result = this.ContainFilter(file);
                break;
            case Utils.PREFIX_FILTER:
                result = this.PrefixFilter(file);
                break;
            case Utils.SUFFIX_FILTER:
                result = this.SuffixFilter(file);
                break;
            case Utils.WRITABLE_FILTER:
                result = this.WritableFilter(file);
                break;
            case Utils.EXECUTABLE_FILTER:
                result = this.ExecutableFilter(file);
                break;
            case Utils.HIDDEN_FILTER:
                result = this.HiddenFilter(file);
                break;
            case Utils.ALL_FILTER:
                result = this.AllFilter();
                break;
        }

        if (not) {
            result = !result;
        }
        return result;
    }

    /**
     * Greater than filter
     *
     * @param file File to filter
     * @return True if file is bigger from num, False otherwise
     */
    private boolean GreaterThanFilter(File file) {
        return file.length() > this.num1;
    }

    /**
     * Between filter
     *
     * @param file     File to filter
     * @return True if file size is between both numbers, False otherwise
     */
    private boolean BetweenFilter(File file) {
        return file.length() >= this.num1 && file.length() <= this.num2;
    }

    /**
     * Smaller than filter
     *
     * @param file File to filter
     * @return True if file size is smaller than num, False otherwise
     */
    private boolean SmallerThanFilter(File file) {
        return file.length() < this.num1;
    }

    /**
     * File name filter
     *
     * @param file  File to filter
     * @return True if file name equal to value, False otherwise
     */
    private boolean FileFilter(File file) {
        return Utils.getFileNameWithoutExtension(file).equals(this.str);
    }

    /**
     * Contains in filename filter
     *
     * @param file  File to filter
     * @return True if file name contain value, false otherwise
     */
    private boolean ContainFilter(File file) {
        return Utils.getFileNameWithoutExtension(file).contains(this.str);
    }

    /**
     * Prefix filename filter
     *
     * @param file  File to filter
     * @return True if file name start with value, false otherwise
     */
    private boolean PrefixFilter(File file) {
        return Utils.getFileNameWithoutExtension(file).startsWith(this.str);
    }

    /**
     * Suffix filename
     *
     * @param file  File to filter
     * @return True if file name end with value, false otherwise
     */
    private boolean SuffixFilter(File file) {
        return Utils.getFileNameWithoutExtension(file).endsWith(this.str);
    }

    /**
     * Writeable filter
     *
     * @param file File to filter
     * @return True if file is writable, false otherwise
     */
    private boolean WritableFilter(File file) {
        return file.canWrite() == this.yesNo;
    }

    /**
     * Executable filter
     *
     * @param file File to filter
     * @return True if file is executable, false otherwise
     */
    private boolean ExecutableFilter(File file) {
        return file.canExecute() == this.yesNo;
    }

    /**
     * Hidden filter
     *
     * @param file File to filter
     * @return True if file is hidden, false otherwise
     */
    private boolean HiddenFilter(File file) {
        return file.isHidden() == this.yesNo;
    }

    /**
     * All filter
     *
     * @return Returns True
     */
    private boolean AllFilter() {
        return true;
    }
}
