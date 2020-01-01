package filesprocessing.filterPackage;

import filesprocessing.Exceptions.WarningException;
import filesprocessing.Utils;

import java.util.Arrays;

public class FilterFactory {
    /**
     * Create filter by commend
     *
     * @param filterCommend Filter commend
     * @param lineNumber    Line number in file
     * @return Filter
     */
    public static Filter CreateFilter(String filterCommend, int lineNumber) {
        try {
            String[] command = filterCommend.split(Utils.SEPARATOR);
            if (command.length == 0) {
                throw new WarningException(lineNumber);
            }

            boolean not_suffix = command[command.length - 1].equals(Utils.NOT_SUFFIX);
            String filterType = command[0];
            if (not_suffix) {
                command = Arrays.copyOfRange(command, 1, command.length - 1);
            } else {
                command = Arrays.copyOfRange(command, 1, command.length);
            }
            if (Utils.GREATER_THEN_FILTER.equals(filterType) ||
                    Utils.SMALLER_THAN_FILTER.equals(filterType)) {
                if (command.length != 1) {
                    throw new WarningException(lineNumber);
                } else if (!Utils.isDouble(command[0])) {
                    throw new WarningException(lineNumber);
                } else if (Double.parseDouble(command[0]) < 0) {
                    throw new WarningException(lineNumber);
                } else {
                    return new Filter(filterType, not_suffix, Double.parseDouble(command[0]));
                }
            } else if (Utils.BETWEEN_FILTER.equals(filterType)) {
                if (command.length != 2) {
                    throw new WarningException(lineNumber);
                } else if (!(Utils.isDouble(command[0]) && Utils.isDouble(command[1]))) {
                    throw new WarningException(lineNumber);
                } else if (Double.parseDouble(command[0]) < 0 || Double.parseDouble(command[1]) < 0) {
                    throw new WarningException(lineNumber);
                } else if (Double.parseDouble(command[0]) > Double.parseDouble(command[1])) {
                    throw new WarningException(lineNumber);
                } else {
                    return new Filter(filterType, not_suffix,
                            Double.parseDouble(command[0]), Double.parseDouble(command[1]));
                }
            } else if (Utils.FILE_FILTER.equals(filterType) ||
                    Utils.CONTAINS_FILTER.equals(filterType) ||
                    Utils.PREFIX_FILTER.equals(filterType) ||
                    Utils.SUFFIX_FILTER.equals(filterType)) {
                if (command.length != 1) {
                    throw new WarningException(lineNumber);
                } else {
                    return new Filter(filterType, not_suffix, command[0]);
                }
            } else if (Utils.WRITABLE_FILTER.equals(filterType) ||
                    Utils.EXECUTABLE_FILTER.equals(filterType) ||
                    Utils.HIDDEN_FILTER.equals(filterType)) {
                if (command.length != 1) {
                    throw new WarningException(lineNumber);
                } else {
                    if (command[0].equals(Utils.Yes)) {
                        return new Filter(filterType, not_suffix, true);
                    } else if (command[0].equals(Utils.No)) {
                        return new Filter(filterType, not_suffix, false);
                    } else {
                        throw new WarningException(lineNumber);
                    }
                }
            } else if (Utils.ALL_FILTER.equals(filterType)) {
                if (command.length != 0) {
                    throw new WarningException(lineNumber);
                } else {
                    return new Filter(filterType, not_suffix);
                }
            }
            throw new WarningException(lineNumber);
        } catch (WarningException exp) {
            System.err.println(exp.getMessage());
            return new Filter(Utils.ALL_FILTER, false);
        }
    }
}
