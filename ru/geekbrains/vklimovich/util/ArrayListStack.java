package ru.geekbrains.vklimovich.util;

public class ArrayListStack<E> implements VStack<E>{
    private final SimpleArrayList<E> list;

    public ArrayListStack(){
        list = new SimpleArrayList<E>();
    }

    public ArrayListStack(int initSize){
        list = new SimpleArrayList<E>(initSize);
    }

    @Override
    public void push(E elem){
        list.add(elem);
    }

    @Override
    public E pop(){
        return list.remove(list.size() - 1);
    }

    @Override
    public E peek(){
        return list.get(list.size() - 1);
    }

    @Override
    public boolean isEmpty(){
        return list.isEmpty();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
