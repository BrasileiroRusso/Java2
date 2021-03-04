package ru.vklimovich.util;

import java.util.*;

public class RandomList {
    private static final int DEFAULT_INT_BOUND = 20;
    private static final int DEFAULT_STR_LEN = 5;
    private static final char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    private static final char[] consonants = "bcdfghjklmnpqrstvxz".toCharArray();

    // Создает список заданного размера с элементами нужного типа
    public static <T> List<T> getList(Generator<T> gen, int listSize){
        List<T> list = new ArrayList<T>();
        for(int i = 0; i < listSize;i++)
            list.add(gen.next());
        return list;
    }

    // Заполняет массив элементами, создаваемыми переданным генератором
    public static <T> void fillArray(T[] array, Generator<T> gen){
        for(int i = 0; i < array.length;i++)
            array[i] = gen.next();
    }

    // Случайный генератор значений типа Integer
    public static Generator<Integer> randomIntGenerator(int bound){
        return new Generator<Integer>(){
            private final Random rand = new Random();

            @Override
            public Integer next() {
                return rand.nextInt(bound);
            }
        };
    }

    // Случайный генератор строк елых числе в диапазоне по умолчанию
    public static Generator<Integer> randomIntGenerator(){
        return randomIntGenerator(DEFAULT_INT_BOUND);
    }

    // Случайный генератор строк String заданной длины
    public static Generator<String> randomStringGenerator(int strLen){
        return new Generator<String>(){
            private final Random rand = new Random();

            @Override
            public String next() {
                char[] chars = new char[strLen];
                for(int i = 0; i < strLen/2; i++){
                    chars[2*i] = consonants[rand.nextInt(consonants.length)];
                    chars[2*i+1] = vowels[rand.nextInt(vowels.length)];
                }
                if(strLen%2 == 1)
                    chars[strLen-1] = consonants[rand.nextInt(consonants.length)];

                return new String(chars);
            }
        };
    }

    // Случайный генератор строк String стандартной длины по умолчанию
    public static Generator<String> randomStringGenerator(){
        return randomStringGenerator(DEFAULT_STR_LEN);
    }
}
