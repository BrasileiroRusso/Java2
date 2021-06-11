package ru.geekbrains.vklimovich.util;

public interface VQueue<E> {
    boolean offer(E elem);
    E remove();
    E peek();
    boolean isEmpty();
    int size();
}
