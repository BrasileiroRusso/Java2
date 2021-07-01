package ru.geekbrains.vklimovich.util;

public class LinkListQueue<E> implements VQueue<E> {
    private final LinkList<E> list;

    public LinkListQueue(){
        list = new LinkList<>();
    }

    @Override
    public boolean offer(E elem) {
        return list.addLast(elem);
    }

    @Override
    public E remove() {
        E elem = list.getFirst();
        list.removeFirst();
        return elem;
    }

    @Override
    public E peek() {
        return list.getFirst();
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
