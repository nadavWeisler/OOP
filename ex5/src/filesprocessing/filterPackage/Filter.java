package filesprocessing.filterPackage;

import filesprocessing.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

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
     * @param files Files to filter
     * @return Filter result
     */
    public ArrayList<File> FilterFile(File[] files) {
        ArrayList<File> result = new ArrayList<>();
        switch (filterType) {
            case Utils.GREATER_THEN_FILTER:
                result = this.GreaterThanFilter(files);
                break;
            case Utils.SMALLER_THAN_FILTER:
                result = this.SmallerThanFilter(files);
                break;
            case Utils.BETWEEN_FILTER:
                result = this.BetweenFilter(files);
                break;
            case Utils.FILE_FILTER:
                result = this.FileFilter(files);
                break;
            case Utils.CONTAINS_FILTER:
                result = this.ContainFilter(files);
                break;
            case Utils.PREFIX_FILTER:
                result = this.PrefixFilter(files);
                break;
            case Utils.SUFFIX_FILTER:
                result = this.SuffixFilter(files);
                break;
            case Utils.WRITABLE_FILTER:
                result = this.WritableFilter(files);
                break;
            case Utils.EXECUTABLE_FILTER:
                result = this.ExecutableFilter(files);
                break;
            case Utils.HIDDEN_FILTER:
                result = this.HiddenFilter(files);
                break;
            case Utils.ALL_FILTER:
                result = this.AllFilter(files);
                break;
        }

        return result;
    }

    /**
     * Greater than filter
     *
     * @return True if file is bigger from num, False otherwise
     */
    private ArrayList<File> GreaterThanFilter(File[] files) {
        ArrayList<File> result = new ArrayList<>();
        for (File file : files) {
            if (!this.not && file.length() > this.num1) {
                result.add(file);
            } else if (this.not && file.length() <= this.num1) {
                result.add(file);
            }
        }
        return result;
    }

    /**
     * Between filter
     *
     * @param files File array to filter
     * @return True if file size is between both numbers, False otherwise
     */
    private ArrayList<File> BetweenFilter(File[] files) {
        ArrayList<File> result = new ArrayList<>();
        for (File file : files) {
            if (!this.not && (file.length() >= this.num1 && file.length() <= this.num2)) {
                result.add(file);
            } else if (this.not && (file.length() <= this.num1 || file.length() >= this.num2)) {
                result.add(file);
            }
        }
        return result;
    }

    /**
     * Smaller than filter
     *
     * @param files File array to filter
     * @return True if file size is smaller than num, False otherwise
     */
    private ArrayList<File> SmallerThanFilter(File[] files) {
        ArrayList<File> result = new ArrayList<>();
        for (File file : files) {
            if (!this.not && file.length() < this.num1) {
                result.add(file);
            } else if (this.not && file.length() >= this.num1) {
                result.add(file);
            }
        }
        return result;
    }

    /**
     * File name filter
     *
     * @param files File array to filter
     * @return True if file name equal to value, False otherwise
     */
    private ArrayList<File> FileFilter(File[] files) {
        ArrayList<File> result = new ArrayList<>();
        for (File file : files) {
            if (!this.not && Utils.getFileNameWithoutExtension(file).equals(this.str)) {
                result.add(file);
            } else if (this.not && !Utils.getFileNameWithoutExtension(file).equals(this.str)) {
                result.add(file);
            }
        }
        return result;
    }

    /**
     * Contains in filename filter
     *
     * @param files Files to filter
     * @return True if file name contain value, false otherwise
     */
    private ArrayList<File> ContainFilter(File[] files) {
        ArrayList<File> result = new ArrayList<>();
        for (File file : files) {
            if (!this.not && Utils.getFileNameWithoutExtension(file).contains(this.str)) {
                result.add(file);
            } else if (this.not && !Utils.getFileNameWithoutExtension(file).contains(this.str)) {
                result.add(file);
            }
        }
        return result;
    }

    /**
     * Prefix filename filter
     *
     * @param files Files to filter
     * @return True if file name start with value, false otherwise
     */
    private ArrayList<File> PrefixFilter(File[] files) {
        ArrayList<File> result = new ArrayList<>();
        for (File file : files) {
            if (!this.not && Utils.getFileNameWithoutExtension(file).startsWith(this.str)) {
                result.add(file);
            } else if (this.not && !Utils.getFileNameWithoutExtension(file).startsWith(this.str)) {
                result.add(file);
            }
        }
        return result;
    }

    /**
     * Suffix filename
     *
     * @param files Files to filter
     * @return True if file name end with value, false otherwise
     */
    private ArrayList<File> SuffixFilter(File[] files) {
        ArrayList<File> result = new ArrayList<>();
        for (File file : files) {
            if (!this.not && Utils.getFileNameWithoutExtension(file).endsWith(this.str)) {
                result.add(file);
            } else if (this.not && !Utils.getFileNameWithoutExtension(file).endsWith(this.str)) {
                result.add(file);
            }
        }
        return result;
    }

    /**
     * Writeable filter
     *
     * @param files Files to filter
     * @return True if file is writable, false otherwise
     */
    private ArrayList<File> WritableFilter(File[] files) {
        ArrayList<File> result = new ArrayList<>();
        for (File file : files) {
            if (!this.not && file.canWrite() == this.yesNo) {
                result.add(file);
            } else if (this.not && file.canWrite() != this.yesNo) {

            }
        }
        return result;
    }

    /**
     * Executable filter
     *
     * @param files Files to filter
     * @return True if file is executable, false otherwise
     */
    private ArrayList<File> ExecutableFilter(File[] files) {
        ArrayList<File> result = new ArrayList<>();
        for (File file : files) {
            if (!this.not && file.canExecute() == this.yesNo) {
                result.add(file);
            } else if (this.not && file.canExecute() != this.yesNo) {
                result.add(file);
            }
        }
        return result;
    }

    /**
     * Hidden filter
     *
     * @param files Files to filter
     * @return True if file is hidden, false otherwise
     */
    private ArrayList<File> HiddenFilter(File[] files) {
        ArrayList<File> result = new ArrayList<>();
        for (File file : files) {
            if (!this.not && file.isHidden() == this.yesNo) {
                result.add(file);
            } else if (this.not && file.isHidden() != this.yesNo) {
                result.add(file);
            }
        }
        return result;
    }

    /**
     * All filter
     *
     * @return Returns True
     */
    private ArrayList<File> AllFilter(File[] files) {
        if (this.not) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(Arrays.asList(files));
        }
    }
}
