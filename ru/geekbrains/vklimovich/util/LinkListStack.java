package ru.geekbrains.vklimovich.util;

public class LinkListStack<E> implements VStack<E> {
    private final LinkList<E> list;

    public LinkListStack(){
        list = new LinkList<>();
    }

    @Override
    public void push(E elem) {
        list.addFirst(elem);
    }

    @Override
    public E pop() {
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
