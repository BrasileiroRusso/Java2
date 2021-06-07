import ru.geekbrains.vklimovich.numbers.*;
import ru.geekbrains.vklimovich.util.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class JavaAlg3 {
    /*
    Задание 3.1
На основе массива из домашнего задания 2.1 реализуйте простой список и коллекцию.
Оцените время выполнения преобразования.
Задание 3.2
На основе списка из задания 3.1 реализуйте основные методы добавления, удаления и получения объекта или элемента из списка.
Оценить выполненные методы с помощью базового класса System.nanoTime().
Задание 3.3
Реализуйте простой односвязный список и его базовые методы.
Задание 3.4
На основе списка из задания 3.1 реализуйте простой двусторонний список и его базовые методы.
Реализуйте список заполненный объектами из вашего класса из задания 1.3
Задание 3.5
Реализуйте итератор на основе связанных списков из задания 3.4 и выполните базовые операции итератора.
Оцените время выполнения операций с помощью базового метода System.nanoTime()
     */
    private static final int SIZE = 400;
    private static final int BOUND = 1000;
    private static final int OPER_NUM = 10;

    public static void main(String[] args) {
        long startTime, duration;
        Integer[] testArray = Stream.generate(() -> ThreadLocalRandom.current().nextInt(BOUND))
                                    .limit(SIZE)
                                    .toArray(Integer[]::new);
        SimpleArrayList<Integer> l1 = new SimpleArrayList<>();
        startTime = System.nanoTime();
        for(Integer i: testArray)
            l1.add(i);
        duration = System.nanoTime() - startTime;
        System.out.printf("Conversion from array to simple array: %d ns\n", duration);
        //System.out.println("List: " + l1);

        startTime = System.nanoTime();
        for(int i = 0; i < OPER_NUM;i++)
            l1.add(ThreadLocalRandom.current().nextInt(BOUND));
        duration = System.nanoTime() - startTime;
        System.out.printf("Adding of %d elements: %d ns\n", OPER_NUM, duration);

        startTime = System.nanoTime();
        for(int i = 0; i < OPER_NUM;i++)
            l1.remove(ThreadLocalRandom.current().nextInt(BOUND));
        duration = System.nanoTime() - startTime;
        System.out.printf("Attempt to remove %d elements: %d ns\n", OPER_NUM, duration);
        //System.out.println("List: " + l1);

        startTime = System.nanoTime();
        for(int i = 0; i < OPER_NUM;i++)
            l1.get(ThreadLocalRandom.current().nextInt(l1.size()));
        duration = System.nanoTime() - startTime;
        System.out.printf("Get %d elements: %d ns\n", OPER_NUM, duration);

        LinkList<Integer> l2 = new LinkList<Integer>();
        l2.add(3);
        l2.addLast(8);
        l2.addFirst(5);
        l2.add(9, 2);
        l2.removeFirst();
        l2.removeLast();
        l2.addFirst(7);
        l2.addFirst(4);
        System.out.println("List of integer numbers: " + l2);
        System.out.println();

        LinkList<Complex> complList = new LinkList<Complex>();
        complList.add(Complex.ZERO);
        complList.addFirst(Complex.I);
        complList.addLast(Complex.ONE);
        complList.add(Complex.sum(Complex.ONE, Complex.I), 1);
        complList.addLast(Complex.valueOf(1, -2));
        complList.addFirst(Complex.valueOf(4, 3));
        complList.add(Complex.valueOf(Math.E, Math.PI), 2);
        System.out.println("List of complex numbers (after adding 7 numbers):");
        System.out.println(complList);
        complList.removeLast();
        complList.remove(3);
        complList.removeFirst();
        System.out.println("List of complex numbers (after removing 3 numbers):");
        System.out.println(complList);
        System.out.println("Element 1: " + complList.get(1));
        System.out.println("Element 3: " + complList.get(3));
        System.out.println("Element 0: " + complList.get(0));
        System.out.println();

        Iterator<Complex> it = complList.iterator();
        System.out.println("List contains:");
        while(it.hasNext())
            System.out.print(it.next() + "  ");
        System.out.println();
        it = complList.iterator();
        it.next();
        it.next();
        it.next();
        it.remove();
        System.out.println("List contains (after removing element 2):");
        System.out.println(complList);
        System.out.println();

        DoubleLinkList<Complex> cList = new DoubleLinkList<Complex>();
        cList.add(Complex.ZERO);
        System.out.println(cList);
        cList.addFirst(Complex.I);
        System.out.println(cList);
        cList.addLast(Complex.ONE);
        System.out.println(cList);
        cList.add(Complex.sum(Complex.ONE, Complex.I), 1);
        cList.addLast(Complex.valueOf(1, -2));
        cList.addFirst(Complex.valueOf(4, 3));
        cList.add(Complex.valueOf(Math.E, Math.PI), 2);
        System.out.println("Double-linked list of complex numbers (after adding 7 numbers):");
        System.out.println(cList);
        cList.removeLast();
        cList.remove(3);
        cList.removeFirst();
        System.out.println("Double-linked list of complex numbers (after removing 3 numbers):");
        System.out.println(cList);
    }
}
