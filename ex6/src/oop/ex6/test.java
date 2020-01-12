package oop.ex6;

import oop.ex6.code.properties.StringProperty;
import oop.ex6.main.Sjavac;
import oop.ex6.parsers.FileParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class test {
    private static String normalizeNewlines(String data) {
        return data.replaceAll("\\n|\\r\\n", System.getProperty("line.separator"));
    }

    public String getLine(String fileName, int num) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        int i = 0;
        String line = "";
        if (num > br.lines().count()) {
            return line;
        }
        while (br.ready() && i <= num) {
            line = br.readLine();
            i++;
        }

        return line;
    }

    @Test
    public void testEx6() throws IOException {
        String fileName = "C:\\Weisler\\Study\\OOP\\ex6\\sjavac_tests.txt";

        // Wrapped Scanner to get user input.

        String dirPath = "C:\\Weisler\\Study\\OOP\\ex6\\tests\\";
        String newLine = getLine(fileName, 73);
        System.out.println(newLine);
        String[] lineSplit = newLine.split(" ");
        if (lineSplit.length < 2) {
            return;
        }
        File newFile = new File(dirPath + lineSplit[0]);
        if (!newFile.exists()) {
            return;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Sjavac.main(new String[]{newFile.getAbsolutePath()});
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        String resultLine = normalizeNewlines(outputStream.toString("UTF-8"));
        String[] SplitResult = resultLine.split("\n");
        resultLine = SplitResult[SplitResult.length - 1];
        String errorMessage = String.join(" ", Arrays.copyOfRange(lineSplit, 2, lineSplit.length));
        Assert.assertEquals(lineSplit[0] + ": " + errorMessage,
                Utils.RemoveAllSpacesAtEnd(resultLine), lineSplit[1]);
    }

    private ArrayList<String> fileToArrayList(String fileName) throws IOException {
        ArrayList<String> result = new ArrayList<String>();

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        while (br.ready()) {
            result.add(br.readLine());
        }

        return result;
    }

}

