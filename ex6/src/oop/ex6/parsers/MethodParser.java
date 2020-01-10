package oop.ex6.parsers;

import oop.ex6.code.Block;
import oop.ex6.code.Method;
import oop.ex6.exceptions.BadFormatException;

import java.util.ArrayList;

public class MethodParser extends Parser {
    private MethodParser() {

    }

    private static MethodParser methodParser = new MethodParser();

    public static MethodParser getInstance() {
        return methodParser;
    }

    public Method parseMethod(ArrayList<String> lines) throws BadFormatException {
        Method newMethod = new Method(lines.get(0));
        ArrayList<Block> blocks = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            if (this.isIfLine(lines.get(i))) {
                blocks.add(new Block(false, lines.get(i)));
                ArrayList<String> newBlock = new ArrayList<>();
                newBlock.add(lines.get(i));
            } else if (this.isWhileLine(lines.get(i))) {
                blocks.add(new Block(true, lines.get(i)));
                ArrayList<String> newBlock = new ArrayList<>();
                newBlock.add(lines.get(i));
            }

            if (blocks.size() > 0) {
                blocks.get(blocks.size() - 1).addLine(lines.get(i));
            }

            if (isEnd(lines.get(i))) {
                blocks.remove(blocks.size() - 1);
            }
        }

        return newMethod;
    }
}
