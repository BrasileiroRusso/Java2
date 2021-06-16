import ru.geekbrains.vklimovich.myarrays.*;
import ru.geekbrains.vklimovich.numbers.IntNumbers;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.*;
import java.util.stream.Stream;

public class JavaAlg5 {
    private static final int TEST_SIZE = 10000;
    private static final int INT_BOUND = 50000;
    private static final int SEARCH_STEP = 100;
    private static final int PRINT_LEN = 40;

    private static <T> T[] testData(int size, Supplier<T> gen, IntFunction<T[]> cons){
        return Stream.generate(gen).limit(size).toArray(cons);
    }

    public static void main(String[] args) {
        Integer[] testArray = testData(TEST_SIZE, () -> ThreadLocalRandom.current().nextInt(INT_BOUND), Integer[]::new);
        Integer[] copyArray = testArray.clone();
        Integer[] searchArr = Stream.iterate(0, val -> val + SEARCH_STEP)
                                    .limit(INT_BOUND/SEARCH_STEP)
                                    .toArray(Integer[]::new);
        long startTime, duration;

        if(testArray.length <= PRINT_LEN)
            System.out.println(Arrays.toString(testArray));


        startTime = System.nanoTime();
        MyArrays.sort(testArray, SortAlg.MERGE);
        duration = System.nanoTime() - startTime;
        if(testArray.length <= PRINT_LEN)
            System.out.println(Arrays.toString(testArray));
        System.out.printf("Merge sort of %d elements: time = %d ns\n", testArray.length, duration);

        startTime = System.nanoTime();
        Arrays.sort(copyArray);
        duration = System.nanoTime() - startTime;
        if(testArray.length <= PRINT_LEN)
            System.out.println(Arrays.toString(copyArray));
        System.out.printf("System sort of %d elements: time = %d ns\n", copyArray.length, duration);

        duration = 0;
        for(Integer i: searchArr){
            startTime = System.nanoTime();
            MyArrays.recBinarySearch(testArray, i);
            duration += System.nanoTime() - startTime;
        }
        duration /= searchArr.length;
        System.out.printf("Average time for binary search in array of size %d = %d ns\n", testArray.length, duration);

        startTime = System.nanoTime();
        IntNumbers.recGetNOD(120, 72);
        IntNumbers.recGetNOD(1320, 77);
        IntNumbers.recGetNOD(120, 180);
        duration = System.nanoTime() - startTime;
        System.out.printf("Time to calculate NOD (recursion) = %d ns\n", duration);

        startTime = System.nanoTime();
        IntNumbers.getNOD(120, 72);
        IntNumbers.getNOD(1320, 77);
        IntNumbers.getNOD(120, 180);
        duration = System.nanoTime() - startTime;
        System.out.printf("Time to calculate NOD (cycle) = %d ns\n", duration);
    }
}
