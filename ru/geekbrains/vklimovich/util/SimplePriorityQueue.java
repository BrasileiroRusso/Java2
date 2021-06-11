package ru.geekbrains.vklimovich.util;

import java.util.*;

public class SimplePriorityQueue<E extends Comparable<? super E>> implements VPriorityQueue<E> {
    private final LinkList<E> list;
    private final Comparator<? super E> comp;

    public SimplePriorityQueue(){
        list = new LinkList<>();
        comp = Comparator.naturalOrder();
    }

    public SimplePriorityQueue(Comparator<? super E> comp){
        list = new LinkList<>();
        this.comp = comp;
    }

    @Override
    public boolean offer(E elem) {
        return list.addLast(elem);
    }

    @Override
    public E remove() {
        if(isEmpty())
            throw new IllegalStateException("Queue is empty");
        int index = minIndex();
        E elem = list.get(index);
        list.remove(index);
        return elem;
    }

    @Override
    public E peek() {
        if(isEmpty())
            throw new IllegalStateException("Queue is empty");
        int index = minIndex();
        return list.get(index);
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

    private int minIndex(){
        int index = 0, minIndex = 0;
        Iterator<E> it = list.iterator();
        E minElem = it.next();
        while(it.hasNext()){
            E e = it.next();
            index++;
            if(comp.compare(e, minElem) < 0){
                minIndex = index;
                minElem = e;
            }
        }
        return minIndex;
    }
}
