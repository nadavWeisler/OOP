package filesprocessing.orderPackage;

import filesprocessing.Exceptions.WarningException;
import filesprocessing.Utils;

import java.util.Arrays;

public class OrderFactory {
    /**
     * Create order from commend
     *
     * @param orderCommend Order commend
     * @param lineNumber   Line number
     * @return Requested Order
     */
    public static Order CreateOrder(String orderCommend, int lineNumber) {
        try {
            String[] command = orderCommend.split(Utils.SEPARATOR);
            if (command.length == 0) {
                throw new WarningException(lineNumber);
            }

            boolean reverse = command[command.length - 1].equals(Utils.REVERSE_SUFFIX);
            String orderType = command[0];

            if (reverse) {
                command = Arrays.copyOfRange(command, 1, command.length - 1);
            } else {
                command = Arrays.copyOfRange(command, 1, command.length);
            }

            if (command.length != 0) {
                throw new WarningException(lineNumber);
            } else if (!orderType.equals(Utils.ABS_ORDER)
                    && !orderType.equals(Utils.TYPE_ORDER)
                    && !orderType.equals(Utils.SIZE_ORDER)) {
                throw new WarningException(lineNumber);
            } else {
                return new Order(orderType, reverse);
            }
        } catch (WarningException exp) {
            System.err.println(exp.getMessage());
            return new Order(Utils.ABS_ORDER, false);
        }
    }
}
