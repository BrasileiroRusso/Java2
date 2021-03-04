package ru.vklimovich.myarrays;

import java.util.*;

public class MyArrays {
    // Перестановка двух элементов массива
    public static <T> void change(T[] array, int i, int j){
        T t = array[i];
        array[i] = array[j];
        array[j] = t;
    }

    // Создание списка по массиву.
    public static <T> ArrayList<T> createArrayList(T[] array){
        ArrayList<T> list = new ArrayList<T>();
        for(T t: array)
            list.add(t);

        return list;
    }

    // Сортировка массива методом Шелла (используется стандартная последовательность Кнута)
    public static <T extends Comparable<T>> void sort(T[] array){
        int step = 1;
        int i, j;

        while(step <= array.length/3)
            step = 3*step + 1;

        while(step > 0){
            for(i = step;i < array.length;i++){
                T t = array[i];
                j = i;
                while(j >= step && array[j-step].compareTo(t) >= 0){
                    array[j] = array[j-step];
                    j -= step;
                }
                array[j] = t;
            }
            step = (step - 1)/3;
        }
    }

    // Поиск минимального элемента массива
    public static <T extends Comparable<T>> T getMin(T[] array){
        if(array.length == 0)
            return null;

        int index = 0;
        for(int i = 1;i < array.length;i++)
            if(array[index].compareTo(array[i]) > 0)
                index = i;

        return array[index];
    }

    // Поиск максимального элемента массива
    public static <T extends Comparable<T>> T getMax(T[] array){
        if(array.length == 0)
            return null;

        int index = 0;
        for(int i = 1;i < array.length;i++)
            if(array[index].compareTo(array[i]) < 0)
                index = i;

        return array[index];
    }

}
