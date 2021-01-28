package ru.geekbrains.vklimovich.ThreadArray;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

public class ThreadArray<T extends Comparable<T>> {
    private static final int THREAD_NUM = 2;
    private static final int QUEUE_SIZE_COEFF = 4;

    private T[] array;
    private int size;
    private int threadNum;
    private BlockingQueue<TimeStat> threadTimeStat;
    private BlockingQueue<List<T>> buffer;
    private String lastOperation = "";

    private class ArrThread implements Runnable {
        int initIndex;
        int lastIndex;
        BiFunction<T, Integer, T> function;

        ArrThread(BiFunction<T, Integer, T> function, int initIndex, int lastIndex){
            this.function = function;
            this.initIndex = initIndex;
            this.lastIndex = lastIndex;
        }

        @Override
        public void run() {
            TimeStat ts;

            ts = new TimeStat("Parallel calc");
            apply(function, initIndex, lastIndex);
            ts.close();
            threadTimeStat.add(ts);
        }
    }

    private class SortThread implements Runnable {
        int initIndex;
        int lastIndex;
        int threadNum;

        SortThread(int initIndex, int lastIndex, int threadNum){
            this.initIndex = initIndex;
            this.lastIndex = lastIndex;
            this.threadNum = threadNum;
        }

        @Override
        public void run() {
            T[] copyArray;
            TimeStat ts;

            ts = new TimeStat("Copy array");
            copyArray = Arrays.copyOfRange(array, initIndex, lastIndex + 1);
            ts.close();
            threadTimeStat.add(ts);

            ts = new TimeStat("Parallel sort");
            Arrays.sort(copyArray);
            buffer.add(Arrays.asList(copyArray));
            ts.close();
            threadTimeStat.add(ts);
        }
    }

    private static class TimeStat{
        Thread thread;
        long startTime, endTime;
        String operation;

        TimeStat(String operation){
            this.thread = Thread.currentThread();
            this.operation = operation;
            this.startTime = System.currentTimeMillis();
        }

        void close(){
            this.endTime = System.currentTimeMillis();
        }

        @Override
        public String toString() {
            return thread.getName() + ": operation = " + operation
                                    + ", execution time = " + (endTime - startTime) + "ms";
        }
    }

    public ThreadArray(T[] array){
        this(array, THREAD_NUM);
    }

    public ThreadArray(T[] array, int threadNum){
        this.size = array.length;
        this.array = array;
        this.threadNum = threadNum;
        threadTimeStat = new ArrayBlockingQueue<TimeStat>(threadNum * QUEUE_SIZE_COEFF);
        buffer = new ArrayBlockingQueue<List<T>>(threadNum * QUEUE_SIZE_COEFF);
    }

    public void apply(BiFunction<T, Integer, T> func){
        TimeStat ts;

        lastOperation = "Calculation";
        threadTimeStat.clear();
        ts = new TimeStat("Calc");
        apply(func, 0, array.length - 1);
        ts.close();
        threadTimeStat.add(ts);
    }

    public void parallelApply(BiFunction<T, Integer, T> func) {
        Thread[] threads = new Thread[threadNum];
        int batchSize, rest, initIndex, lastIndex;
        TimeStat ts;

        batchSize = size/threadNum;
        rest = size % threadNum;
        lastOperation = "Parallel calculation";
        ts = new TimeStat("Main parallel calculation");

        for(int i = 1;i <= threadNum;i++){
            initIndex = (i - 1)*batchSize + Math.min(i-1, rest);
            lastIndex = i*batchSize - 1 + Math.min(i, rest);
            threads[i-1] = new Thread(new ArrThread(func, initIndex, lastIndex));
            threads[i-1].setName("Thread-" + i + "(" + initIndex + ", " + lastIndex + ")");
        }
        threadTimeStat.clear();

        for(Thread t: threads)
            t.start();

        try{
            for(Thread t: threads)
                t.join();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        ts.close();
        threadTimeStat.add(ts);

    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    private void apply(BiFunction<T, Integer, T> func, int initIndex, int lastIndex){
        for(int i = initIndex;i <= lastIndex;i++)
            array[i] = func.apply(array[i], i);
    }

    public void printOperationTiming() {
        if (lastOperation.isEmpty())
            return;

        Iterator<TimeStat> it = threadTimeStat.iterator();
        System.out.println("OPERATION: " + lastOperation.toUpperCase());

        while (it.hasNext())
            System.out.println(it.next());

    }

    public void sort(){
        TimeStat ts;

        lastOperation = "Sort array";
        threadTimeStat.clear();
        ts = new TimeStat("Sort");
        Arrays.sort(array);
        ts.close();
        threadTimeStat.add(ts);
    }

    public void parallelSort() {
        Thread[] threads = new Thread[threadNum];
        int batchSize, rest, initIndex, lastIndex;
        TimeStat ts, mergeTs;

        batchSize = size/threadNum;
        rest = size % threadNum;
        lastOperation = "Parallel sort array";
        ts = new TimeStat("Main parallel sort");

        for(int i = 1;i <= threadNum;i++){
            initIndex = (i - 1)*batchSize + Math.min(i-1, rest);
            lastIndex = i*batchSize - 1 + Math.min(i, rest);
            threads[i-1] = new Thread(new SortThread(initIndex, lastIndex, i));
            threads[i-1].setName("Thread-" + i + "(" + initIndex + ", " + lastIndex + ")");
        }
        buffer.clear();
        threadTimeStat.clear();

        for(Thread t: threads)
            t.start();

        try{
            for(Thread t: threads)
                t.join();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        mergeTs = new TimeStat("Merge arrays");
        mergeFromBuffer();
        mergeTs.close();
        ts.close();
        threadTimeStat.add(mergeTs);
        threadTimeStat.add(ts);

    }

    private void mergeFromBuffer(){
        while(buffer.size() >= 2){
            buffer.add(mergeLists(buffer.remove(), buffer.remove()));
        }

        if(!buffer.isEmpty())
            buffer.remove().toArray(array);

    }

    private List<T> mergeLists(List<T> lst1, List<T> lst2){
        List<T> tempList = new LinkedList<T>();
        Iterator<T> it1 = lst1.iterator();
        Iterator<T> it2 = lst2.iterator();
        T t1, t2;

        if(it1.hasNext() && it2.hasNext()){
            t1 = it1.next();
            t2 = it2.next();

            while (true){
                if(t1.compareTo(t2) <= 0){
                    tempList.add(t1);
                    if(it1.hasNext())
                        t1 = it1.next();
                    else{
                        tempList.add(t2);
                        break;
                    }

                }
                else{
                    tempList.add(t2);
                    if(it2.hasNext())
                        t2 = it2.next();
                    else{
                        tempList.add(t1);
                        break;
                    }
                }
            }

        }

        while(it1.hasNext())
            tempList.add(it1.next());

        while(it2.hasNext())
            tempList.add(it2.next());

        return tempList;

    }

}
