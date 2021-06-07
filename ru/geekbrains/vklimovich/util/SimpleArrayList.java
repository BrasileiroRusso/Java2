package ru.geekbrains.vklimovich.util;

import java.util.*;

public class SimpleArrayList<E> implements Iterable<E> {
    private static final int INIT_SIZE = 64;

    private E[] storage;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public SimpleArrayList(int initSize){
        storage = (E[]) new Object[initSize];
    }

    public SimpleArrayList(){
        this(INIT_SIZE);
    }

    public SimpleArrayList(Collection<E> col){
        this(col.size());
        int i = 0;
        for(E e: col)
            storage[i++] = e;
        size = storage.length;
    }

    public SimpleArrayList(E[] array){
        storage = array.clone();
        size = storage.length;
    }

    public boolean add(E elem){
        increaseCapacity(1);
        storage[size++] = elem;
        return true;
    }

    public void clear(){
        @SuppressWarnings("unchecked")
        E[] newStorage = (E[]) new Object[INIT_SIZE];
        storage = newStorage;
        size = 0;
    }

    public E get(int index){
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        return storage[index];
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public boolean contains(E elem){
        return getIndex(elem) >= 0;
    }

    public int getIndex(E elem){
        for(int i = 0;i < size;i++)
            if(storage[i].equals(elem))
                return i;
        return -1;
    }

    public boolean remove(E elem){
        int index = getIndex(elem);
        if(index < 0)
            return false;
        shiftArray(index, -1);
        return true;
    }

    private void increaseCapacity(int numElems){
        if(size + numElems < storage.length)
            return;
        int newSize = Math.max(2*storage.length, size + numElems);

        @SuppressWarnings("unchecked")
        E[] newStorage = (E[]) new Object[newSize];

        System.arraycopy(storage, 0, newStorage, 0, storage.length);
        storage = newStorage;
    }

    private void shiftArray(int index, int shift){
        if(shift == 0)
            return;
        int newSize = size + shift;
        if(shift < 0){
            System.arraycopy(storage, index - shift, storage, index, size - index - 1);
            for(int i = newSize + 1;i < size;i++)
                storage[i] = null;
        }
        else{
            increaseCapacity(shift);
            System.arraycopy(storage, index, storage, index + shift, size - index - 1);
        }
        size = newSize;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>(){
            private int index = -1;

            @Override
            public boolean hasNext() {
                return index < size - 1;
            }

            @Override
            public E next() {
                if(index >= size - 1)
                    throw new NoSuchElementException();
                return storage[++index];
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for(int i = 0; i < size;i++)
            sb.append(storage[i]).append(", ");
        if(size > 0)
            sb.delete(sb.length() - 2, sb.length());
        sb.append("}");
        return sb.toString();
    }
}
