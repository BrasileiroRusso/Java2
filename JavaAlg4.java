import ru.geekbrains.vklimovich.numbers.Complex;
import ru.geekbrains.vklimovich.util.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

public class JavaAlg4 {
    /*
    Задание 4.1
    На основе данных объектного списка из задания 3.4 реализуйте простой стек и его базовые методы.
    Оцените время выполнения операций с помощью базового метода System.nanoTime().
    Задание 4.2
    На основе данных объектного списка из задания 3.4 реализуйте простую очередь и его базовые методы.
    Реализуйте вспомогательные методы.
    Оцените время выполнения операций с помощью базового метода System.nanoTime().
    Задание 4.3
    На основе данных объектного списка из задания 3.4 реализуйте простой дек и его базовые методы.
    Оцените время выполнения операций с помощью базового метода System.nanoTime().
    Задание 4.4
    Реализуйте приоритетную очередь на основе ссылочных типов данных, например, integer.
    Оцените время выполнения операций с помощью базового метода System.nanoTime().
    Задание 4.5
    На основе данных из задания 4.1 и 4.2, реализуйте стек и очередь на базе связанного списка.
    Оцените время выполнения операций с помощью базового метода System.nanoTime().
    */
    private static final int TEST_SIZE = 10000;
    private static final int INT_BOUND = 50000;
    private static final int LIMIT_PRINT_SIZE = 50;

    public static <T> T[] testData(T seed, UnaryOperator<T> iterFunc, int size, IntFunction<T[]> arrCons){
        return Stream.iterate(seed, iterFunc).limit(size).toArray(arrCons);
    }

    public static <T> void test(VStack<? super T> stack, T[] testData){
        long startTime, duration;
        String clName = stack.getClass().getSimpleName();
        System.out.println("============ Class " + clName + " =============");
        if(testData.length <= LIMIT_PRINT_SIZE)
            System.out.println(Arrays.toString(testData));

        startTime = System.nanoTime();
        for(T t: testData)
            stack.push(t);
        duration = System.nanoTime() - startTime;
        System.out.printf("Inserted %d elements: %d ns\n", testData.length, duration);

        if(testData.length <= LIMIT_PRINT_SIZE)
            System.out.println(stack);

        startTime = System.nanoTime();
        while(!stack.isEmpty())
            stack.pop();
        duration = System.nanoTime() - startTime;
        System.out.printf("Removed %d elements: %d ns\n", testData.length, duration);
        System.out.println();
    }

    public static <T> void test(VQueue<? super T> queue, T[] testData){
        long startTime, duration;
        String clName = queue.getClass().getSimpleName();
        System.out.println("============ Class " + clName + " =============");
        if(testData.length <= LIMIT_PRINT_SIZE)
            System.out.println(Arrays.toString(testData));

        startTime = System.nanoTime();
        for(T t: testData)
            queue.offer(t);
        duration = System.nanoTime() - startTime;
        System.out.printf("Inserted %d elements: %d ns\n", testData.length, duration);

        if(testData.length <= LIMIT_PRINT_SIZE)
            System.out.println(queue);

        startTime = System.nanoTime();
        while(!queue.isEmpty())
            queue.remove();
        duration = System.nanoTime() - startTime;
        System.out.printf("Removed %d elements: %d ns\n", testData.length, duration);
        System.out.println();
    }

    public static <T> void test(VDeque<? super T> deque, T[] testData){
        long startTime, duration;
        String clName = deque.getClass().getSimpleName();
        System.out.println("============ Class " + clName + " =============");
        if(testData.length <= LIMIT_PRINT_SIZE)
            System.out.println(Arrays.toString(testData));

        startTime = System.nanoTime();
        for(int i = 1; i < testData.length;i+=2){
            deque.addFirst(testData[i-1]);
            deque.addLast(testData[i]);
        }
        duration = System.nanoTime() - startTime;
        System.out.printf("Inserted %d elements: %d ns\n", testData.length, duration);

        if(testData.length <= LIMIT_PRINT_SIZE)
            System.out.println(deque);

        startTime = System.nanoTime();
        for(int i = 1; i < testData.length;i+=2){
            deque.removeFirst();
            deque.removeLast();
        }
        duration = System.nanoTime() - startTime;
        System.out.printf("Removed %d elements: %d ns\n", testData.length, duration);
        System.out.println();
    }

    public static <T> void testStacks(List<VStack<T>> stacks, T[] testData){
        for(VStack<T> stack: stacks){
            test(stack, testData);
        }
    }

    public static <T> void testQueues(List<VQueue<T>> queues, T[] testData){
        for(VQueue<T> queue: queues){
            test(queue, testData);
        }
    }

    public static <T> void testDeques(List<VDeque<T>> deques, T[] testData){
        for(VDeque<T> deque: deques){
            test(deque, testData);
        }
    }

    public static void main(String[] args) {
        Integer[] intArray = testData(1, val -> ThreadLocalRandom.current().nextInt(INT_BOUND),
                                        TEST_SIZE, Integer[]::new);
        Complex[] complexArray = testData(Complex.ZERO, val -> Complex.valueOf(ThreadLocalRandom.current().nextInt(INT_BOUND),
                                                                               ThreadLocalRandom.current().nextInt(INT_BOUND)),
                                          TEST_SIZE, Complex[]::new);

        List<VStack<Complex>> stacks = new ArrayList<>();
        stacks.add(new ArrayListStack<>());
        stacks.add(new LinkListStack<>());
        List<VQueue<Complex>> queues = new ArrayList<>();
        queues.add(new LinkListQueue<>());
        queues.add(new ArrayQueue<>());
        queues.add(new LinkedDeque<>());
        List<VQueue<Integer>> priorityQueues = new ArrayList<>();
        priorityQueues.add(new SimplePriorityQueue<>());
        priorityQueues.add(new SortedPriorityQueue<>(Comparator.reverseOrder()));
        List<VDeque<Complex>> deques = new ArrayList<>();
        deques.add(new LinkedDeque<>());

        testStacks(stacks, complexArray);
        testQueues(queues, complexArray);
        testQueues(priorityQueues, intArray);
        testDeques(deques, complexArray);
    }
}
