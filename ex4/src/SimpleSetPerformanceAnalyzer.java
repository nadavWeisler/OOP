import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

public class SimpleSetPerformanceAnalyzer {
    private final String DATA1 = "data1.txt";
    private final String DATA2 = "data2.txt";
    private SimpleSet[] allDataStructuresData1;
    private SimpleSet[] allDataStructuresData2;
    private final String[] ALL_STRINGS_DATA_1 = Ex4Utils.file2array(DATA1);
    private final String[] ALL_STRINGS_DATA_2 = Ex4Utils.file2array(DATA2);
    private final String TEST_CONTAIN_WORD_NOT_EXIST_DATA_1 = "hi";
    private final String TEST_CONTAIN_WORD_EXIST_DATA_1 = "-13170890158";
    private final String TEST_CONTAIN_WORD_NOT_EXIST_DATA_2 = "hi";
    private final String TEST_CONTAIN_WORD_EXIST_DATA_2 = "23";
    private final double NANOSECOND_TO_MILLISECOND_CONVERSION = 0.000001;
    private final int WARM_COUNT = 500000;
    private final int WARM_COUNT_LINKED_LIST = 7000;
    private final int TEST_COUNT = 70000;
    private final int TEST_COUNT_LINKED_LIST = 7000;


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
        this.allDataStructuresData1 = new SimpleSet[]{
                new OpenHashSet(),
                new ClosedHashSet(),
                new CollectionFacadeSet(new TreeSet<>()),
                new CollectionFacadeSet(new LinkedList<>()),
                new CollectionFacadeSet(new HashSet<>())
        };
        this.allDataStructuresData2 = new SimpleSet[]{
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
    private void warmUp(int warmUpCount, String word, SimpleSet dataStructure) {
        for (int i = 0; i < warmUpCount; i++) {
            dataStructure.contains(word);
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
        double startTime = System.nanoTime();
        for (String str :
                stringArray) {
            dataStructure.add(str);
        }
        return ((System.nanoTime() - startTime) / stringArray.length) *
                this.NANOSECOND_TO_MILLISECOND_CONVERSION;
    }

    /**
     * Test contain function
     *
     * @param dataStructure Data structure
     * @param word          Word to check contain
     * @param warmCount     Warm up count
     * @return Average result
     */
    private double TestContain(SimpleSet dataStructure, String word, int warmCount, int testCount) {
        this.warmUp(warmCount, this.TEST_CONTAIN_WORD_NOT_EXIST_DATA_2, dataStructure);
        double startTime = System.nanoTime();
        for (int i = 0; i < testCount; i++) {
            dataStructure.contains(word);
        }
        return (System.nanoTime() - startTime) / testCount;
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
     * Get test out count by index
     *
     * @param analyzer analyzer object
     * @param i        Index
     * @return Warm up count
     */
    private static int getTestCount(SimpleSetPerformanceAnalyzer analyzer, int i) {
        int count = analyzer.TEST_COUNT;
        if (i == 3) {
            count = analyzer.TEST_COUNT_LINKED_LIST;
        }
        return count;
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
     *
     * @param args Argument
     */
    public static void main(String[] args) {
        SimpleSetPerformanceAnalyzer analyzer = new SimpleSetPerformanceAnalyzer();
        System.out.println("Data1 - add:");
        for (int i = 0; i < 5; i++) {
            printDataStructure(i);
            assert analyzer.ALL_STRINGS_DATA_1 != null;
            System.out.println(analyzer.TestAdd(analyzer.ALL_STRINGS_DATA_1, analyzer.allDataStructuresData1[i]));
        }

        System.out.println("Data2 - add:");
        for (int i = 0; i < 5; i++) {
            printDataStructure(i);
            assert analyzer.ALL_STRINGS_DATA_2 != null;
            System.out.println(analyzer.TestAdd(analyzer.ALL_STRINGS_DATA_2, analyzer.allDataStructuresData2[i]));
        }

        System.out.println("Data1 - contain word that not exist':");
        for (int i = 0; i < 5; i++) {
            printDataStructure(i);
            int warmCount = getWarmCount(analyzer, i);
            int testCount = getTestCount(analyzer, i);
            System.out.println(analyzer.TestContain(analyzer.allDataStructuresData1[i],
                    analyzer.TEST_CONTAIN_WORD_NOT_EXIST_DATA_1,
                    warmCount, testCount));
        }

        System.out.println("Data1 - contain word that exist':");
        for (int i = 0; i < 5; i++) {
            printDataStructure(i);
            int warmCount = getWarmCount(analyzer, i);
            int testCount = getTestCount(analyzer, i);
            System.out.println(analyzer.TestContain(analyzer.allDataStructuresData1[i],
                    analyzer.TEST_CONTAIN_WORD_EXIST_DATA_1,
                    warmCount, testCount));
        }

        System.out.println("Data2 - contain word that exist:");
        for (int i = 0; i < 5; i++) {
            printDataStructure(i);
            int warmCount = getWarmCount(analyzer, i);
            int testCount = getTestCount(analyzer, i);
            System.out.println(analyzer.TestContain(analyzer.allDataStructuresData2[i],
                    analyzer.TEST_CONTAIN_WORD_EXIST_DATA_2,
                    warmCount, testCount));
        }

        System.out.println("Data2 - contain word that not exist:");
        for (int i = 0; i < 5; i++) {
            printDataStructure(i);
            int warmCount = getWarmCount(analyzer, i);
            int testCount = getTestCount(analyzer, i);
            System.out.println(analyzer.TestContain(analyzer.allDataStructuresData2[i],
                    analyzer.TEST_CONTAIN_WORD_NOT_EXIST_DATA_2,
                    warmCount, testCount));
        }
    }
}
