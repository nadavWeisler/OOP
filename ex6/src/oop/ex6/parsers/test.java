package oop.ex6.parsers;

import oop.ex6.Utils;
import org.junit.Assert;
import org.junit.Test;

public class test {
    @Test
    public void testIsComment1() {
        Assert.assertFalse("Fail isComment1", FileParser.getInstance().isComment(""));
        Assert.assertFalse("Fail isComment2", FileParser.getInstance().isComment("12"));
        Assert.assertFalse("Fail isComment3", FileParser.getInstance().isComment("/"));
        Assert.assertFalse("Fail isComment4", FileParser.getInstance().isComment(" //"));
        Assert.assertFalse("Fail isComment5", FileParser.getInstance().isComment("/ /"));
        Assert.assertTrue("Fail isComment6", FileParser.getInstance().isComment("//"));
        Assert.assertTrue("Fail isComment7", FileParser.getInstance().isComment("//2232"));
        Assert.assertTrue("Fail isComment8", FileParser.getInstance().isComment("//dfdf"));
        Assert.assertTrue("Fail isComment9", FileParser.getInstance().isComment("// fdff"));
        Assert.assertTrue("Fail isComment10", FileParser.getInstance().isComment("//   "));


        Assert.assertFalse("Fail isEmpty1", FileParser.getInstance().isEmpty("s"));
        Assert.assertFalse("Fail isEmpty2", FileParser.getInstance().isEmpty(" s"));
        Assert.assertFalse("Fail isEmpty3", FileParser.getInstance().isEmpty("s "));
        Assert.assertFalse("Fail isEmpty4", FileParser.getInstance().isEmpty(" s "));
        Assert.assertTrue("Fail isEmpty5", FileParser.getInstance().isEmpty(""));
        Assert.assertTrue("Fail isEmpty6", FileParser.getInstance().isEmpty(" "));
        Assert.assertTrue("Fail isEmpty7", FileParser.getInstance().isEmpty("    "));


    }
}
