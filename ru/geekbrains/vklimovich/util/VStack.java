package ru.geekbrains.vklimovich.util;

public interface VStack<E> {
    void push(E elem);
    E pop();
    E peek();
    boolean isEmpty();
    int size();
}
