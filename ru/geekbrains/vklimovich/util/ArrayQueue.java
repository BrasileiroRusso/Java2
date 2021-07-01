package ru.geekbrains.vklimovich.util;

import java.util.Arrays;

public class ArrayQueue<E> implements VQueue<E> {
    private static final int INIT_SIZE = 64;

    private E[] storage;
    private int size;
    private int top, tail;

    public ArrayQueue(){
        this(INIT_SIZE);
    }

    public ArrayQueue(int initSize){
        storage = allocate(initSize);
        size = 0;
        top = 0;
        tail = -1;
    }

    @Override
    public boolean offer(E elem) {
        size++;
        checkCapacity();
        tail++;
        if(tail == storage.length)
            tail = 0;
        storage[tail] = elem;
        return true;
    }

    @Override
    public E remove() {
        if(isEmpty())
            throw new IllegalStateException("Queue is empty");
        E elem = storage[top];
        storage[top++] = null;
        if(top == storage.length)
            top = 0;
        size--;
        return elem;
    }

    @Override
    public E peek() {
        if(isEmpty())
            throw new IllegalStateException("Queue is empty");
        return storage[top];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return Arrays.toString(storage);
    }

    @SuppressWarnings("unchecked")
    private E[] allocate(int size){
        return (E[]) new Object[size];
    }

    private void checkCapacity(){
        if(size < storage.length)
            return;
        E[] newStorage = allocate(2*storage.length);
        if(tail >= top){
            System.arraycopy(storage, top, newStorage, 0,tail - top + 1);
        }
        else{
            System.arraycopy(storage, top, newStorage, 0, storage.length - top);
            System.arraycopy(storage, 0, newStorage, storage.length - top, tail);
        }
        top = 0;
        tail = storage.length - 1;
        storage = newStorage;
    }
}
