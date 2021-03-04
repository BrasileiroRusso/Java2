package ru.vklimovich.fruit;

import java.math.BigDecimal;
import java.util.*;

public class Box<T extends Fruit> {
    private List<T> fruits;

    // Создание пустой коорбки
    public Box(){
        fruits = new LinkedList<T>();
    }

    // Создание коробки на основе коллекции
    public Box(Collection<T> coll){
        fruits = new LinkedList<T>(coll);
    }

    // Создание коробки по списку элементов переменной длины (или массиву)
    public Box(T... elems){
        this();
        for(T t: elems)
            fruits.add(t);
    }

    // Добавляет фрукт в коробку
    public void add(T fruit){
        fruits.add(fruit);
    }

    // Добавляе в коробку все фрукты из коллекции
    public void addAll(List<T> fruitList){
        fruits.addAll(fruitList);
    }

    // Добавляе в коорбку все фрукты из переданного списка
    public void addAll(T... fruitList){
        for(T t: fruitList)
            fruits.add(t);
    }

    // Удаляет все фрукты из коробки
    public List<T> removeAll(){
        List<T> fruitList = new LinkedList<T>(fruits);
        fruits.clear();

        return fruitList;
    }

    // Возвращает число фруктов в коробке
    public int getSize(){
        return fruits.size();
    }

    // Возвращает вес коробки
    public float getWeight(){
        double weight = 0f;
        for(T t: fruits)
            weight += t.getWeight();
        return (float)weight;
    }

    // Метод сравнения коробок фруктов по весу
    public boolean compare(Box<? extends Fruit> otherBox){
        return getWeight() == otherBox.getWeight();
    }

    // Информация на печать
    @Override
    public String toString() {
        return "Box" + fruits;
    }

    // Перекладывает все фрукты из одной коробки в другую коробку того же типа
    public static <T extends Fruit> void move(Box<T> boxFrom, Box<T> boxTo){
        for(T t: boxFrom.removeAll())
            boxTo.add(t);
    }

}
