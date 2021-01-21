package ru.geekbrains.vklimovich.lesson2_3;

import java.util.*;
import ru.geekbrains.vklimovich.objcount.*;
import ru.geekbrains.vklimovich.telephonebook.*;
import ru.geekbrains.vklimovich.randomstring.*;

public class Lesson2_3 {
    private static final int STR_ARRAY_SIZE = 100000;
    private static final int INT_ARRAY_SIZE = 50000;
    private static final int WORD_LEN = 3;
    private static final int INT_BOUND = 50;

    public static void main(String[] args) {
        String[] strArray;
        Integer[] intArray = new Integer[INT_ARRAY_SIZE];
        TelephoneBook telBook = new TelephoneBook();
        Random rand = new Random();

        System.out.println("Statistics for the integer numbers:");
        for(int i = 0;i < intArray.length;i++)
            intArray[i] = rand.nextInt(INT_BOUND);
        printObjCount(intArray);
        System.out.println();

        System.out.println("Statistics for the strings:");
        strArray = RandomString.getStrArray(STR_ARRAY_SIZE, WORD_LEN);
        printObjCount(strArray);
        System.out.println();

        telBook.add("Ivanov", "+74951333333");
        telBook.add("IVANOV", "+79163335566");
        telBook.add("Petrov", "+79161235478");
        telBook.add("PETROV", "+79161237278");
        telBook.add("Ivanov", "+79857774456");
        telBook.add("Ivanov", "+79857774456");
        telBook.add("Sidorov", "+74959999999");
        telBook.add("Sidorov", "  ");
        telBook.add("Borisov", "+74959657832");
        telBook.add("Borisov", "+79104531357");

        System.out.println();
        telBook.printTelephoneList("Ivanov");
        telBook.printTelephoneList("Petrov");
        telBook.printTelephoneList("Kozlov");

        System.out.println();
        telBook.printTelephoneList();
    }

    private static <T> void printObjCount(T[] array){
        int wordNum = 0;

        for(Map.Entry<T, Integer> entry: MapCounter.getObjList(array).entrySet()){
            System.out.print("(" + entry.getKey() + ", " + entry.getValue() + ") ");
            wordNum++;
            if (wordNum % 12 == 0)
                System.out.println();
        }
    }

}