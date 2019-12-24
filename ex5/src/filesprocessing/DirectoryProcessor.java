package filesprocessing;

import java.io.*;

public class DirectoryProcessor {
    private static boolean TestArgs(String[] args) {
        if (args.length < 2) {
            System.out.println("Less than two args");
            return false;
        }

        if (args.length > 2) {
            System.out.println("More than two args");
            return false;
        }

        File dir = new File(args[0]);
        File file = new File(args[1]);

        if (!dir.isDirectory() || dir.exists()) {
            System.out.println("Not valid directory");
            return false;
        }

        if (!file.isFile() || file.exists()) {
            System.out.println("Not valid file");
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        if (!TestArgs(args)) {
            return;
        }

        File dir = new File(args[0]);
        File file = new File(args[1]);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                try {
                    switch (lineCount % 4) {
                        case 0:
                            HandleHeading(line, lineCount, "FILTER");
                            break;
                        case 1:
                            HandleFilterArgs(line, lineCount);
                            break;
                    }
                    lineCount++;
                } catch (WarningException exp) {
                    System.out.println(exp.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void HandleHeading(String line, int lineCount, String str) throws WarningException {
        if (!line.equals(str)) {
            throw new WarningException(lineCount);
        }
    }

    private static void HandleFilterArgs(String line, int lineCount) throws WarningException {
        String[] splitLine = line.split("#");
        if(splitLine.length == 0) {
            throw new WarningException(lineCount);
        }

        if(!Utils.FILTER_TYPES.contains(splitLine[0])){
            throw new WarningException(lineCount);
        }

        //Check count
        CheckCountOfArgsFilter(lineCount, splitLine);

        //Check type
        CheckTypeOrArgsFilter(lineCount, splitLine);
    }

    private static void CheckTypeOrArgsFilter(int lineCount, String[] splitLine) throws WarningException {
        switch (splitLine[0]) {
            case Utils.BETWEEN_FILTER:
            case Utils.GREATER_THEN_FILTER:
            case Utils.SMALLER_THAN_FILTER:
                for(int i = 1; i < splitLine.length; i++){
                    if(!Utils.isInteger(splitLine[i])) {
                        throw new WarningException(lineCount);
                    }
                }
                break;
            case Utils.WRITABLE_FILTER:
            case Utils.EXECUTABLE_FILTER:
            case Utils.HIDDEN_FILTER:
                if(!splitLine[1].equals("Yes") && !splitLine[1].equals("No")) {
                    throw new WarningException(lineCount);
                }
            default:
                if(!splitLine[0].equals(Utils.ALL_FILTER) && splitLine[1].isEmpty()) {
                    throw new WarningException(lineCount);
                }
        }
    }

    private static void CheckCountOfArgsFilter(int lineCount, String[] splitLine) throws WarningException {
        switch (splitLine[0]) {
            case Utils.BETWEEN_FILTER:
                if(splitLine.length != 3) {
                    throw new WarningException(lineCount);
                }
                break;
            case Utils.GREATER_THEN_FILTER:
            case Utils.SMALLER_THAN_FILTER:
                break;
        }
    }
}
