package ru.geekbrains.vklimovich.randomstring;

import java.util.*;

public class RandomString {
    private static char[] consonants = "bcdflmnrst".toCharArray();
    private static char[] vowels = "aeiou".toCharArray();
    private static Random rand = new Random();

    public static String getString(int strLen){
        StringBuilder strBuilder = new StringBuilder();

        for(int i = 1;i <= strLen/2;i++){
            strBuilder.append(consonants[rand.nextInt(consonants.length)]);
            strBuilder.append(vowels[rand.nextInt(vowels.length)]);
        }
        if (strLen % 2 == 1)
            strBuilder.append(consonants[rand.nextInt(consonants.length)]);

        return strBuilder.toString();
    }

    public static String[] getStrArray(int arraySize, int strLen){
        String[] result = new String[arraySize];

        for(int i = 0;i < arraySize;i++)
            result[i] = getString(strLen);

        return result;
    }
}
