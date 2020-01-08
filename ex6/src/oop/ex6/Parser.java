package oop.ex6;

import oop.ex6.exceptions.BadFormatException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    public static ArrayList<String> fileToArrayList(String fileName) throws IOException {
        ArrayList<String> result = new ArrayList<String>();

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        while (br.ready()) {
            result.add(br.readLine());
        }

        return result;
    }

    public static int ParseFile(String fileName) throws IOException {
        try {
            ArrayList<String> fileList = fileToArrayList(fileName);

            for (String line : fileList) {
                if (line.isEmpty() || line.isBlank()) {
                    continue;
                }

                if (Validations.getValidations().isComment(line)) {
                    continue;
                }

                Validations.getValidations().hasCodeSuffix(line);



            }
        } catch (BadFormatException exp) {
            System.err.println(exp.getMessage());
        }

        return 0;
    }

}
