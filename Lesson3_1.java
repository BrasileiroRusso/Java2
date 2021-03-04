package ru.vklimovich.lesson3_1;

import ru.vklimovich.fruit.*;
import ru.vklimovich.myarrays.*;
import ru.vklimovich.util.*;

import java.util.*;

public class Lesson3_1 {

    private static final int ARRAY_SIZE_BOUND = 6;
    private static final Random rand = new Random();
    private static final int CHANGE_ITERATIONS = 3;

    // Тестирование массива: перестановки элементов и преобразование в List
    private static <T> void testArray(T[] array){
        int i, j;

        System.out.println("Array contains:");
        System.out.println(Arrays.toString(array));

        for(int k = 1; k <= CHANGE_ITERATIONS;k++){
            i = rand.nextInt(array.length);
            j = rand.nextInt(array.length);
            while(i==j)
                j = rand.nextInt(array.length);

            MyArrays.change(array, i, j);
            System.out.println("After change of item " + i + " with item " + j + ":");
            System.out.println(Arrays.toString(array));
        }

        System.out.println("Array as ArrayList:");
        System.out.println(MyArrays.createArrayList(array));
    }

    // Полное тестирование массива. К операциям из базового теста добавляется поиск
    // минимального и максимального элементов и сортировка
    private static <T extends Comparable<T>> void fullTestArray(T[] array){
        testArray(array);

        System.out.println("Array min item: " + MyArrays.getMin(array));
        System.out.println("Array max item: " + MyArrays.getMax(array));
        MyArrays.sort(array);
        System.out.println("Sorted array:");
        System.out.println(Arrays.toString(array));
    }

    // Вывод информации о коробках из списка
    public static <T extends Fruit> void getBoxesInfo(List<Box<T>> fruitBoxes, String kind){
        // Информация о коробках из списка
        ListIterator<Box<T>> boxIter = fruitBoxes.listIterator();
        while(boxIter.hasNext()){
            System.out.print(kind + " box " + (boxIter.nextIndex() + 1) + ", size = ");
            Box<T> box = boxIter.next();
            System.out.println(box.getSize() + ", weight = " + box.getWeight() + ":");
            System.out.println(box);
        }
    }

    // Сравнение по весу коробок фруктов из двух списков
    public static <T extends Fruit, F extends Fruit> void
           compareBoxesByWeight(List<Box<T>> boxList1, List<Box<F>> boxList2, String kind1, String kind2){

        // Сравниваем по весу коробки фруктов из двух списков
        ListIterator<Box<T>> boxIter1 = boxList1.listIterator();
        while(boxIter1.hasNext()){
            int boxNum = boxIter1.nextIndex() + 1;
            Box<T> box = boxIter1.next();
            ListIterator<Box<F>> boxIter2 = boxList2.listIterator();
            while (boxIter2.hasNext()){
                int boxNum2 = boxIter2.nextIndex() + 1;
                Box<F> box2 = boxIter2.next();
                System.out.print(kind1 + " box " + boxNum + " compare with ");
                System.out.print(kind2 + " box " + boxNum2 + ":");
                System.out.print(box.compare(box2));
                System.out.println(" (" + box.getWeight() + " vs " + box2.getWeight() + ")");
            }
        }
    }

    // Сравнение по весу коробок фруктов одного вида
    public static <T extends Fruit> void compareBoxesByWeight(List<Box<T>> boxList, String kind){
        compareBoxesByWeight(boxList, boxList, kind, kind);
    }

    public static void main(String[] args) {
        // Размеры массивов выбираем случайным образом в установленных пределах
        String[] strArr = new String[rand.nextInt(ARRAY_SIZE_BOUND-3) + 3];
        Integer[] intArr = new Integer[rand.nextInt(ARRAY_SIZE_BOUND-3) + 3];
        Apple[] apples = new Apple[rand.nextInt(ARRAY_SIZE_BOUND-3) + 3];
        Orange[] oranges = new Orange[rand.nextInt(ARRAY_SIZE_BOUND-3) + 3];

        // Заполняем массивы с помощью случайных генераторов
        RandomList.fillArray(strArr, RandomList.randomStringGenerator());
        RandomList.fillArray(intArr, RandomList.randomIntGenerator());
        RandomList.fillArray(apples, Apple.randomGenerator());
        RandomList.fillArray(oranges, Orange.randomGenerator());

        // Выполняем тест для каждого массива
        testArray(apples);
        System.out.println();

        testArray(oranges);
        System.out.println();

        fullTestArray(strArr);
        System.out.println();

        fullTestArray(intArr);
        System.out.println();

        // Создаем коробки яблок и апельсинов различными способами
        Generator<Apple> appleGen = Apple.randomGenerator();
        List<Apple> appleList = new LinkedList<Apple>();
        Box<Apple> appleBox1 = new Box<Apple>(apples);
        Box<Apple> appleBox2 = new Box<Apple>(appleGen.next(), appleGen.next(), appleGen.next());
        appleList.add(new Apple(AppleKind.GRANNY_SMITH));
        appleList.add(new Apple(AppleKind.GOLDEN));
        appleList.add(new Apple(AppleKind.GALA));
        appleList.add(new Apple(AppleKind.JAZZ));
        Box<Apple> appleBox3 = new Box<Apple>(appleList);
        appleBox3.add(new Apple(AppleKind.PINK_LADY));

        Generator<Orange> orangeGen = Orange.randomGenerator();
        List<Orange> orangeList = new LinkedList<Orange>();
        Box<Orange> orangeBox1 = new Box<Orange>(oranges);
        Box<Orange> orangeBox2 = new Box<Orange>(orangeGen.next(), orangeGen.next(), orangeGen.next());
        orangeList.add(new Orange(OrangeKind.CLEMENTINE));
        orangeList.add(new Orange(OrangeKind.BERGAMOT));
        orangeList.add(new Orange(OrangeKind.BLOOD));
        Box<Orange> orangeBox3 = new Box<Orange>(orangeList);
        orangeBox3.add(new Orange(OrangeKind.TANGERINE));

        // Для удобства перебора сохраняем коробки в списках
        List<Box<Apple>> appleBoxList = new ArrayList<Box<Apple>>();
        List<Box<Orange>> orangeBoxList = new ArrayList<Box<Orange>>();
        appleBoxList.add(appleBox1);
        appleBoxList.add(appleBox2);
        appleBoxList.add(appleBox3);
        orangeBoxList.add(orangeBox1);
        orangeBoxList.add(orangeBox2);
        orangeBoxList.add(orangeBox3);

        // Список коробок яблок
        getBoxesInfo(appleBoxList, "Apple");
        System.out.println();
        // Список коробок апельсинов
        getBoxesInfo(orangeBoxList, "Orange");
        System.out.println();

        // Сравниваем коробки с фруктами по весу
        compareBoxesByWeight(appleBoxList, "Apple");
        System.out.println();
        compareBoxesByWeight(orangeBoxList, "Orange");
        System.out.println();
        compareBoxesByWeight(appleBoxList, orangeBoxList, "Apple", "Orange");
        System.out.println();

        // Перекладываем яблоки из коробки 3 в коробку 1
        Box.move(appleBox3, appleBox1);
        System.out.println("Apples from box 3 moved to box 1");
        getBoxesInfo(appleBoxList, "Apple");
        System.out.println();

        // Перекладываем апельсины из коробки 2 в коробку 3
        Box.move(orangeBox2, orangeBox3);
        Box.move(orangeBox2, orangeBox3);
        System.out.println("Oranges from box 2 moved to box 3");
        getBoxesInfo(orangeBoxList, "Orange");

    }

}
