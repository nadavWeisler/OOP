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

            boolean not = command[command.length - 1].equals(Utils.NOT_SUFFIX);
            String filterType = command[0];
            if (not) {
                command = Arrays.copyOfRange(command, 1, command.length - 1);
            } else {
                command = Arrays.copyOfRange(command, 1, command.length);
            }

            switch (filterType) {
                case Utils.GREATER_THEN_FILTER:
                case Utils.SMALLER_THAN_FILTER:
                    if (command.length != 1) {
                        throw new WarningException(lineNumber);
                    } else if (!Utils.isDouble(command[0])) {
                        throw new WarningException(lineNumber);
                    } else if (Double.parseDouble(command[0]) < 0) {
                        throw new WarningException(lineNumber);
                    } else {
                        return new Filter(filterType, not, Double.parseDouble(command[0]));
                    }
                case Utils.BETWEEN_FILTER:
                    if (command.length != 2) {
                        throw new WarningException(lineNumber);
                    } else if (!(Utils.isDouble(command[0]) && Utils.isDouble(command[1]))) {
                        throw new WarningException(lineNumber);
                    } else if (Double.parseDouble(command[0]) < 0 || Double.parseDouble(command[1]) < 0) {
                        throw new WarningException(lineNumber);
                    } else if (Double.parseDouble(command[0]) > Double.parseDouble(command[1])) {
                        throw new WarningException(lineNumber);
                    } else {
                        return new Filter(filterType, not,
                                Double.parseDouble(command[0]), Double.parseDouble(command[1]));
                    }
                case Utils.FILE_FILTER:
                case Utils.CONTAINS_FILTER:
                case Utils.PREFIX_FILTER:
                case Utils.SUFFIX_FILTER:
                    if (command.length != 1) {
                        throw new WarningException(lineNumber);
                    } else {
                        return new Filter(filterType, not, command[0]);
                    }
                case Utils.WRITABLE_FILTER:
                case Utils.EXECUTABLE_FILTER:
                case Utils.HIDDEN_FILTER:
                case Utils.ALL_FILTER:
                    if (command.length != 1) {
                        throw new WarningException(lineNumber);
                    } else if (!(command[0].equals(Utils.Yes) || command[0].equals(Utils.No))) {
                        throw new WarningException(lineNumber);
                    } else {
                        if (command[0].equals(Utils.Yes)) {
                            return new Filter(filterType, not, true);
                        } else {
                            return new Filter(filterType, not, false);
                        }
                    }

                default:
                    throw new WarningException(lineNumber);
            }
        } catch (WarningException exp) {
            System.out.println(exp.getMessage());
            return new Filter(Utils.ALL_FILTER, false);
        }
    }
}
