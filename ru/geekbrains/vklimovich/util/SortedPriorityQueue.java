package ru.geekbrains.vklimovich.util;

import java.util.Comparator;
import java.util.ListIterator;

public class SortedPriorityQueue<E extends Comparable<? super E>> implements VPriorityQueue<E> {
    private final DoubleLinkList<E> list;
    private final Comparator<? super E> comp;

    public SortedPriorityQueue(){
        list = new DoubleLinkList<>();
        comp = Comparator.naturalOrder();
    }

    public SortedPriorityQueue(Comparator<? super E> comp){
        list = new DoubleLinkList<>();
        this.comp = comp;
    }

    @Override
    public boolean offer(E elem) {
        ListIterator<E> it = list.listIterator();
        while(it.hasNext()){
            E e = it.next();
            if(comp.compare(e, elem) >= 0){
                it.previous();
                break;
            }
        }
        it.add(elem);
        return true;
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
