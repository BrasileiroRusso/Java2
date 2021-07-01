package ru.geekbrains.vklimovich.util;

public class LinkedDeque<E> implements VDeque<E> {
    private final DoubleLinkList<E> list;

    public LinkedDeque(){
        list = new DoubleLinkList<>();
    }

    @Override
    public boolean addFirst(E elem) {
        return list.addFirst(elem);
    }

    @Override
    public boolean addLast(E elem) {
        return list.addLast(elem);
    }

    @Override
    public E removeFirst() {
        E elem = list.getFirst();
        list.removeFirst();
        return elem;
    }

    @Override
    public E removeLast() {
        E elem = list.getLast();
        list.removeLast();
        return elem;
    }

    @Override
    public E peekFirst() {
        return list.getFirst();
    }

    @Override
    public E peekLast() {
        return list.getLast();
    }

    @Override
    public boolean offer(E elem) {
        return addLast(elem);
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public boolean isEmpty() {
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
