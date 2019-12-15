import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

public class SimpleSetPerformanceAnalyzer {
    private final String DATA1 = "data1.txt";
    private final String DATA2 = "data2.txt";
    private SimpleSet[] allDataStructures;
    private final String[] ALL_STRINGS_DATA_1 = Ex4Utils.file2array(DATA1);
    private final String[] ALL_STRINGS_DATA_2 = Ex4Utils.file2array(DATA2);
    private final String TEST_CONTAIN_WORD_NOT_EXIST_DATA_1 = "hi";
    private final String TEST_CONTAIN_WORD_EXIST_DATA_1 = "-13170890158";
    private final String TEST_CONTAIN_WORD_NOT_EXIST_DATA_2 = "hi";
    private final String TEST_CONTAIN_WORD_EXIST_DATA_2 = "23";
    private final double NANOSECOND_TO_MILLISECOND_CONVERSION = 0.000001;
    private final int WARM_COUNT = 70000;
    private final int WARM_COUNT_LINKED_LIST = 7000;

    /**
     * Constructor
     */
    SimpleSetPerformanceAnalyzer() {
        resetStructure();
    }

    /**
     * Reset structures array
     */
    private void resetStructure() {
        this.allDataStructures = new SimpleSet[]{
                new OpenHashSet(),
                new ClosedHashSet(),
                new CollectionFacadeSet(new TreeSet<>()),
                new CollectionFacadeSet(new LinkedList<>()),
                new CollectionFacadeSet(new HashSet<>())
        };
    }

    /**
     * Warm up loop
     *
     * @param warmUpCount Warm up number
     * @param word        Word to check contain
     */
    private void warmUp(int warmUpCount, String word) {
        for (int i = 0; i < warmUpCount; i++) {
            for (SimpleSet dataStructure :
                    allDataStructures) {
                dataStructure.contains(word);
            }
        }
    }

    /**
     * Test add function
     *
     * @param stringArray   Strings array
     * @param dataStructure Data structure
     * @return Average result
     */
    private double TestAdd(String[] stringArray, SimpleSet dataStructure) {
        double count = 0;
        for (String str :
                stringArray) {
            double startTime = System.nanoTime();
            dataStructure.add(str);
            count += System.nanoTime() - startTime;
        }
        return count / stringArray.length * this.NANOSECOND_TO_MILLISECOND_CONVERSION;
    }

    /**
     * Test contain function
     *
     * @param stringArray   Strings array
     * @param dataStructure Data structure
     * @param word          Word to check contain
     * @param warmCount     Warm up count
     * @return Average result
     */
    private double TestContain(String[] stringArray, SimpleSet dataStructure, String word, int warmCount) {
        TestAdd(stringArray, dataStructure);
        this.warmUp(warmCount, this.TEST_CONTAIN_WORD_NOT_EXIST_DATA_2);
        double count = 0;
        dataStructure.contains(word);
        return count / stringArray.length;
    }

    /**
     * Get warm out count by index
     *
     * @param analyzer analyzer object
     * @param i        Index
     * @return Warm up count
     */
    private static int getWarmCount(SimpleSetPerformanceAnalyzer analyzer, int i) {
        int warmCount = analyzer.WARM_COUNT;
        if (i == 3) {
            warmCount = analyzer.WARM_COUNT_LINKED_LIST;
        }
        return warmCount;
    }

    /**
     * Print data structure by index
     *
     * @param i Index
     */
    private static void printDataStructure(int i) {
        switch (i) {
            case 0:
                System.out.println("OpenHashSet:");
                break;
            case 1:
                System.out.println("CloseHashSet:");
                break;
            case 2:
                System.out.println("TreeSet:");
                break;
            case 3:
                System.out.println("LinkedHashSet:");
                break;
            case 4:
                System.out.println("HashMap:");
        }
    }

    /**
     * Main function
     * @param args Argument
     */
    public static void main(String[] args) {
        SimpleSetPerformanceAnalyzer analyzer = new SimpleSetPerformanceAnalyzer();
        System.out.println("Data1 - add:");
        for (int i = 0; i < 5; i++) {
            printDataStructure(i);
            assert analyzer.ALL_STRINGS_DATA_1 != null;
            System.out.println(analyzer.TestAdd(analyzer.ALL_STRINGS_DATA_1, analyzer.allDataStructures[i]));
        }
        analyzer.resetStructure();
        System.out.println("Data2 - add:");
        for (int i = 0; i < 5; i++) {
            printDataStructure(i);
            assert analyzer.ALL_STRINGS_DATA_2 != null;
            System.out.println(analyzer.TestAdd(analyzer.ALL_STRINGS_DATA_2, analyzer.allDataStructures[i]));
        }
        analyzer.resetStructure();
        System.out.println("Data1 - contain word that not exist':");
        for (int i = 0; i < 5; i++) {
            printDataStructure(i);
            int warmCount = getWarmCount(analyzer, i);
            System.out.println(analyzer.TestContain(analyzer.ALL_STRINGS_DATA_1,
                    analyzer.allDataStructures[i],
                    analyzer.TEST_CONTAIN_WORD_NOT_EXIST_DATA_1,
                    warmCount));
        }
        analyzer.resetStructure();
        System.out.println("Data1 - contain word that exist':");
        for (int i = 0; i < 5; i++) {
            printDataStructure(i);
            int warmCount = getWarmCount(analyzer, i);
            System.out.println(analyzer.TestContain(analyzer.ALL_STRINGS_DATA_1,
                    analyzer.allDataStructures[i],
                    analyzer.TEST_CONTAIN_WORD_EXIST_DATA_1,
                    warmCount));
        }
        analyzer.resetStructure();
        System.out.println("Data2 - contain word that exist:");
        for (int i = 0; i < 5; i++) {
            printDataStructure(i);
            int warmCount = getWarmCount(analyzer, i);
            System.out.println(analyzer.TestContain(analyzer.ALL_STRINGS_DATA_2,
                    analyzer.allDataStructures[i],
                    analyzer.TEST_CONTAIN_WORD_EXIST_DATA_2,
                    warmCount));
        }
        analyzer.resetStructure();
        System.out.println("Data2 - contain word that not exist:");
        for (int i = 0; i < 5; i++) {
            printDataStructure(i);
            int warmCount = getWarmCount(analyzer, i);
            System.out.println(analyzer.TestContain(analyzer.ALL_STRINGS_DATA_2,
                    analyzer.allDataStructures[i],
                    analyzer.TEST_CONTAIN_WORD_NOT_EXIST_DATA_2,
                    warmCount));
        }
    }
}
