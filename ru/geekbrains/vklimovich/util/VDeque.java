package ru.geekbrains.vklimovich.util;

public interface VDeque<E> extends VQueue<E> {
    boolean addFirst(E elem);
    boolean addLast(E elem);
    E removeFirst();
    E removeLast();
    E peekFirst();
    E peekLast();
}
