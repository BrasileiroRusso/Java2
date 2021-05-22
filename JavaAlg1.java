import ru.geekbrains.vklimovich.numbers.*;
import ru.geekbrains.vklimovich.util.*;

import java.util.LinkedList;
import java.util.List;
import static java.util.stream.Collectors.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class JavaAlg1 {
    private static final int TEST_MIN = 10;
    private static final int TEST_COEFF = 10;
    private static final int TEST_NUM = 5;
    private static final int BOUND_INT = 100;

    public static void main(String[] args) {
        int[] testNums = IntStream.iterate(TEST_MIN, size -> size*TEST_COEFF)
                                  .limit(TEST_NUM)
                                  .toArray();

        for(int size: testNums) {
            SimpleArrayList<Complex> cmplList = new SimpleArrayList<>(size);
            List<Complex> lst = Stream.generate(Complex.intGenerator(BOUND_INT))
                                      .limit(size)
                                      .collect(toCollection(LinkedList::new));

            System.out.printf("============== Test size = %d ==============", size);
            System.out.println();

            long startTime = System.nanoTime();
            for (Complex z : lst)
                cmplList.add(z);
            long duration = System.nanoTime() - startTime;
            System.out.printf("Time to add elements: %dns", duration);
            System.out.println();
            System.out.println("List size = " + cmplList.size());

            Complex searchElem = cmplList.get(ThreadLocalRandom.current().nextInt(cmplList.size()));
            startTime = System.nanoTime();
            boolean contains = cmplList.contains(searchElem);
            duration = System.nanoTime() - startTime;
            System.out.printf("Search time: %dns (search result = %b)", duration, contains);
            System.out.println();
        }
    }

}
