package filesprocessing.filterPackage;

import filesprocessing.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Filter {
    private String filterType;

    private boolean not_suffix;

    private double num1;

    private double num2;

    private String str;

    private boolean yesNo;

    /**
     * @param file
     * @return
     */
    private static double getFileSizeKiloBytes(File file) {
        return (double) file.length() / 1024;
    }

    /**
     * Basic Constructor
     *
     * @param filterType Filter type
     * @param not_suffix Not
     */
    public Filter(String filterType, boolean not_suffix) {
        this.filterType = filterType;
        this.not_suffix = not_suffix;
    }

    /**
     * Constructor for double
     *
     * @param filterType Filter type
     * @param not_suffix Not
     * @param num        Double
     */
    public Filter(String filterType, boolean not_suffix, double num) {
        this(filterType, not_suffix);
        this.num1 = num;
    }

    /**
     * Constructor for two double values
     *
     * @param filterType Filter type
     * @param not_suffix Not
     * @param n1         double 1
     * @param n2         double 2
     */
    public Filter(String filterType, boolean not_suffix, double n1, double n2) {
        this(filterType, not_suffix);
        this.num1 = n1;
        this.num2 = n2;
    }

    /**
     * Constructor for string value
     *
     * @param filterType Filter type
     * @param not_suffix Not
     * @param str        Str value
     */
    public Filter(String filterType, boolean not_suffix, String str) {
        this(filterType, not_suffix);
        this.str = str;
    }

    /**
     * Constructor for boolean value
     *
     * @param filterType Filter type
     * @param not_suffix Not
     * @param _yesNo      Yes or no
     */
    public Filter(String filterType, boolean not_suffix, boolean _yesNo) {
        this(filterType, not_suffix);
        this.yesNo = _yesNo;
    }

    /**
     * Filter file with num
     *
     * @param files Files to filter
     * @return Filter result
     */
    public ArrayList<File> FilterFile(File[] files) {
        ArrayList<File> result = new ArrayList<File>();
        if (Utils.GREATER_THEN_FILTER.equals(this.filterType)) {
            result = this.GreaterThanFilter(files);
        } else if (Utils.SMALLER_THAN_FILTER.equals(this.filterType)) {
            result = this.SmallerThanFilter(files);
        } else if (Utils.BETWEEN_FILTER.equals(this.filterType)) {
            result = this.BetweenFilter(files);
        } else if (Utils.FILE_FILTER.equals(this.filterType)) {
            result = this.FileFilter(files);
        } else if (Utils.CONTAINS_FILTER.equals(this.filterType)) {
            result = this.ContainFilter(files);
        } else if (Utils.PREFIX_FILTER.equals(this.filterType)) {
            result = this.PrefixFilter(files);
        } else if (Utils.SUFFIX_FILTER.equals(this.filterType)) {
            result = this.SuffixFilter(files);
        } else if (Utils.WRITABLE_FILTER.equals(this.filterType)) {
            result = this.WritableFilter(files);
        } else if (Utils.EXECUTABLE_FILTER.equals(this.filterType)) {
            result = this.ExecutableFilter(files);
        } else if (Utils.HIDDEN_FILTER.equals(this.filterType)) {
            result = this.HiddenFilter(files);
        } else if (Utils.ALL_FILTER.equals(this.filterType)) {
            result = this.AllFilter(files);
        }

        return result;
    }

    /**
     * Greater than filter
     *
     * @return True if file is bigger from num, False otherwise
     */
    private ArrayList<File> GreaterThanFilter(File[] files) {
        ArrayList<File> result = new ArrayList<File>();
        for (File file : files) {
            if (getFileSizeKiloBytes(file) > this.num1) {
                if (!this.not_suffix) {
                    result.add(file);
                }
            } else {
                if (this.not_suffix) {
                    result.add(file);
                }
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
        ArrayList<File> result = new ArrayList<File>();
        for (File file : files) {
            if (getFileSizeKiloBytes(file) >= this.num1 &&
                    getFileSizeKiloBytes(file) <= this.num2) {
                if (!this.not_suffix) {
                    result.add(file);
                }
            } else {
                if (this.not_suffix) {
                    result.add(file);
                }
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
        ArrayList<File> result = new ArrayList<File>();
        for (File file : files) {
            if (getFileSizeKiloBytes(file) < this.num1) {
                if (!this.not_suffix) {
                    result.add(file);
                }
            } else {
                if (this.not_suffix) {
                    result.add(file);
                }
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
        ArrayList<File> result = new ArrayList<File>();
        for (File file : files) {
            if ((file.getName()).equals(this.str)) {
                if (!this.not_suffix) {
                    result.add(file);
                }
            } else {
                if (this.not_suffix) {
                    result.add(file);
                }
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
        ArrayList<File> result = new ArrayList<File>();
        for (File file : files) {
            if ((file.getName()).contains(this.str)) {
                if (!this.not_suffix) {
                    result.add(file);
                }
            } else {
                if (this.not_suffix) {
                    result.add(file);
                }
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
        ArrayList<File> result = new ArrayList<File>();
        for (File file : files) {
            if ((file.getName()).startsWith(this.str)) {
                if (!this.not_suffix) {
                    result.add(file);
                }
            } else {
                if (this.not_suffix) {
                    result.add(file);
                }
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
        ArrayList<File> result = new ArrayList<File>();
        for (File file : files) {
            if ((file.getName()).endsWith(this.str)) {
                if (!this.not_suffix) {
                    result.add(file);
                }
            } else {
                if (this.not_suffix) {
                    result.add(file);
                }
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
        ArrayList<File> result = new ArrayList<File>();
        for (File file : files) {
            if (file.canWrite() == this.yesNo) {
                if (!this.not_suffix) {
                    result.add(file);
                }
            } else {
                if (this.not_suffix) {
                    result.add(file);
                }
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
        ArrayList<File> result = new ArrayList<File>();
        for (File file : files) {
            if (file.canExecute() == this.yesNo) {
                if (!this.not_suffix) {
                    result.add(file);
                }
            } else {
                if (this.not_suffix) {
                    result.add(file);
                }
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
        ArrayList<File> result = new ArrayList<File>();
        for (File file : files) {
            if (file.isHidden() == this.yesNo) {
                if (!this.not_suffix) {
                    result.add(file);
                }
            } else {
                if (this.not_suffix) {
                    result.add(file);
                }
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
        if (this.not_suffix) {
            return new ArrayList<File>();
        } else {
            return new ArrayList<File>(Arrays.asList(files));
        }
    }
}
