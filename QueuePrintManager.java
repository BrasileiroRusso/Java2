package ru.geekbrains.vklimovich.printmanager;

import java.util.concurrent.*;
import java.util.*;


public class QueuePrintManager implements PrintManager {
    private int iterations;
    private char[] printSymbols;
    private final ExecutorService execService;
    private int counter = 0;

    public QueuePrintManager(){
        execService = Executors.newCachedThreadPool();
    }

    @Override
    public synchronized void print(String seq, int iterations){
        this.iterations = iterations;
        printSymbols = seq.toCharArray();
        counter = 0;

        CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(execService);
        List<SynchronousQueue<Boolean>> queues = new ArrayList<SynchronousQueue<Boolean>>();
        for(int i = 0;i < printSymbols.length;i++)
            queues.add(new SynchronousQueue<Boolean>());
        for(int i = 0;i < printSymbols.length - 1;i++)
            cs.submit(new PrintTask(i, queues.get(i), queues.get(i+1), false));
        cs.submit(new PrintTask(printSymbols.length-1, queues.get(printSymbols.length-1), queues.get(0), true));

        try {
            queues.get(0).put(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            for(int i = 0;i < printSymbols.length;i++){
                Future<Integer> f = cs.take();
                f.get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause());
        }

    }

    @Override
    public synchronized void close(){
        execService.shutdownNow();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    class PrintTask implements Callable<Integer>{
        private final int order;
        private final boolean isLast;
        private final BlockingQueue<Boolean> inQueue;
        private final BlockingQueue<Boolean> outQueue;

        PrintTask(int order, BlockingQueue<Boolean> inQueue, BlockingQueue<Boolean> outQueue, boolean isLast){
            this.order = order;
            this.inQueue = inQueue;
            this.outQueue = outQueue;
            this.isLast = isLast;
        }

        @Override
        public Integer call() {
            int curIter = 0;

            try {
                while(!Thread.interrupted() && ++curIter <= iterations) {
                    inQueue.take();
                    System.out.print(printSymbols[order]);
                    if(!isLast || curIter < iterations)
                        outQueue.put(true);
                }
            } catch(InterruptedException e) {
                System.out.println("Interrupted");
            }

            return curIter-1;
        }

    }
}


