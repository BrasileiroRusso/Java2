import ru.geekbrains.vklimovich.myarrays.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.*;

public class JavaAlg2 {
    private static final int BASE_SIZE = 30;
    private static final int SEARCH_NUM = 10;
    private static final int BASE_BOUND = 100;

    private static final int TEST_SIZE = 400;
    private static final int SIZE_COEFF = 2;
    private static final int ITERATIONS = 7;
    private static final int INT_BOUND = 50000;
    private static final int REPEAT_NUM = 5;

    private static final int SEARCH_ARR_SIZE = 10000;
    private static final int SEARCH_BOUND = 50000;
    private static final int SEARCH_MIN = 1;
    private static final int SEARCH_MAX = 50000;

    public static Integer[] randomArray(int size, int bound){
        return Stream.generate(() -> ThreadLocalRandom.current().nextInt(bound))
                                                      .limit(size)
                                                      .toArray(Integer[]::new);
    }

    public static void main(String[] args) {
        int[] sizes = IntStream.iterate(TEST_SIZE, size -> SIZE_COEFF*size)
                               .limit(ITERATIONS)
                               .toArray();
        Integer[] searchArr = Stream.generate(() -> ThreadLocalRandom.current().nextInt(BASE_BOUND))
                                    .distinct()
                                    .limit(SEARCH_NUM)
                                    .toArray(Integer[]::new);

        Integer[] testArray = randomArray(BASE_SIZE, BASE_BOUND);
        Map<Integer, Boolean> searchResults = new TreeMap<>();
        System.out.println("Array: " + Arrays.toString(testArray));
        Arrays.sort(testArray);
        System.out.println("Sorted array: " + Arrays.toString(testArray));
        for(Integer i: searchArr)
            searchResults.put(i, (Arrays.binarySearch(testArray, i) >= 0));
        System.out.println("Search results: " + searchResults.toString());
        Arrays.sort(testArray, Comparator.reverseOrder());
        System.out.println("Reverse sorted array: " + Arrays.toString(testArray));
        System.out.println();

        for(int size: sizes){
            Map<SortAlg, Long> algMap = new EnumMap<>(SortAlg.class);
            long startTime, duration;

            for(int i = 1; i <= REPEAT_NUM;i++){
                Integer[] intArray = randomArray(TEST_SIZE, INT_BOUND);
                //System.out.println("Array = " + Arrays.toString(intArray));
                for(SortAlg alg: SortAlg.values()){
                    Integer[] copyArray = intArray.clone();
                    startTime = System.nanoTime();
                    MyArrays.sort(copyArray, alg);
                    duration = System.nanoTime() - startTime;
                    duration += algMap.getOrDefault(alg, 0L);
                    algMap.put(alg, duration);
                    //System.out.println(alg + ": " + Arrays.toString(copyArray));
                }
            }
            algMap.replaceAll((alg, value) -> Long.divideUnsigned(value, REPEAT_NUM));

            System.out.println("========= SORT ALGORITHMS TEST =========");
            System.out.printf("========= ARRAY SIZE = %5d   =========\n", size);
            System.out.println("========================================");
            for(Map.Entry<SortAlg, Long> e: algMap.entrySet())
                System.out.printf("%7s: %d ns\n", e.getKey(), e.getValue());
            System.out.println();
        }

        Map<Integer, Long> lSearch = new LinkedHashMap<>();
        Map<Integer, Long> bSearch = new LinkedHashMap<>();

        List<Integer> intLst = Stream.iterate(SEARCH_MIN, val -> Integer.sum(val, 1))
                                 .limit(SEARCH_MAX - SEARCH_MIN + 1)
                                 .collect(Collectors.toList());
        Collections.shuffle(intLst);
        testArray = randomArray(SEARCH_ARR_SIZE, SEARCH_BOUND);

        for(Integer i: intLst){
            long startTime = System.nanoTime();
            MyArrays.search(testArray, i);
            long duration = System.nanoTime() - startTime;
            lSearch.put(i, duration);
        }
        MyArrays.sort(testArray, SortAlg.INSERT);

        for(Integer i: intLst){
            long startTime = System.nanoTime();
            MyArrays.binarySearch(testArray, i);
            long duration = System.nanoTime() - startTime;
            bSearch.put(i, duration);
        }

        long avgLinearTime = lSearch.values().stream().reduce(0L, Long::sum)/intLst.size();
        long avgBinaryTime = bSearch.values().stream().reduce(0L, Long::sum)/intLst.size();
        System.out.println("LINEAR SEARCH: " + avgLinearTime);
        System.out.println("BINARY SEARCH: " + avgBinaryTime);
    }
}
