package ru.geekbrains.vklimovich.util;

import java.util.StringJoiner;
import java.util.function.*;

public class VHashtable<K, V> {
    private static final int DEFAULT_SIZE = 128;
    private static final int INCREASE_COEFF = 2;
    private static final double LOAD_COEFF = 0.67;

    private Object[] array;
    private int size;
    private int maxSize;
    private final Entry delEntry = new Entry(null, null);
    private final ToIntFunction<K> secondHash;

    private class Entry{
        K key;
        V value;
        Entry(K key, V value){
            this.key = key;
            this.value = value;
        }
    }

    public VHashtable(int initSize, ToIntFunction<K> secondHash){
        allocateArray(initSize);
        this.secondHash = secondHash;
    }

    public VHashtable(int initSize){
        this(initSize, key -> 1);
    }

    public VHashtable(ToIntFunction<K> secondHash){
        this(DEFAULT_SIZE, secondHash);
    }

    public VHashtable(){
        this(DEFAULT_SIZE);
    }

    private void allocateArray(int size){
        array = new Object[size];
        this.size = 0;
        maxSize = (int) (array.length * LOAD_COEFF);
    }

    @SuppressWarnings("unchecked")
    private Entry getEntry(int index){
        return ((Entry) array[index]);
    }

    private V getValue(int index){
        return getEntry(index).value;
    }

    private K getKey(int index){
        return getEntry(index).key;
    }

    public boolean put(K key, V value){
        int pos = key.hashCode() % array.length;
        int step = secondHash.applyAsInt(key);

        while(array[pos] != null && getEntry(pos) != delEntry){
            pos += step;
            pos %= array.length;
        }
        array[pos] = new Entry(key, value);
        size++;
        reHash();
        return true;
    }

    public boolean remove(K key){
        int pos = key.hashCode() % array.length;
        int step = secondHash.applyAsInt(key);

        while(array[pos] != null){
            if(getKey(pos).equals(key)){
                array[pos] = delEntry;
                size--;
                return true;
            }
            pos += step;
            pos %= array.length;
        }
        return false;
    }

    public V get(K key){
        int pos = key.hashCode() % array.length;
        int step = secondHash.applyAsInt(key);

        while(array[pos] != null){
            if(getKey(pos).equals(key))
                return getValue(pos);
            pos += step;
            pos %= array.length;
        }
        return null;
    }

    public int getSize(){
        return size;
    }

    private void reHash(){
        if(size < maxSize)
            return;
        int newSize = array.length * INCREASE_COEFF;
        Object[] copyArray = array.clone();
        allocateArray(newSize);
        for(int i = 0; i < copyArray.length;i++){
            if(copyArray[i] != null){
                @SuppressWarnings("unchecked")
                Entry e = (Entry) copyArray[i];
                if(e != delEntry)
                    put(e.key, e.value);
            }
        }
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ");
        for(int i = 0; i < array.length;i++){
            if(array[i] != null && getEntry(i) != delEntry){
                sj.add(getKey(i) + "=" + getValue(i));
            }
        }
        return "[" + sj.toString() + "]";
    }
}
