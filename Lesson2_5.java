package ru.geekbrains.vklimovich.lesson2_5;

import ru.geekbrains.vklimovich.ThreadArray.*;
import java.util.*;
import java.util.function.*;

public class Lesson2_5 {
    private static final int ARRAY_SIZE = 10000000;
    private static final int THREAD_NUM = 3;

    public static void main(String[] args) {
        Float[] floatArray = new Float[ARRAY_SIZE];
        ThreadArray<Float> array;
        BiFunction<Float, Integer, Float> func;

        Arrays.fill(floatArray, 1f);
        array = new ThreadArray<Float>(floatArray, THREAD_NUM);
        func = (e, i)->{return (float)(e * Math.sin(0.2f + i/5) * Math.cos(0.2f + i/5) * Math.cos(0.4f + i/2));};

        array.apply(func);
        array.printOperationTiming();

        System.out.println();

        array.sort();
        array.printOperationTiming();

        System.out.println();

        array.parallelApply(func);
        array.printOperationTiming();

        System.out.println();

        array.parallelSort();
        array.printOperationTiming();

    }

}
