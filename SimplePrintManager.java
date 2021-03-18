package ru.geekbrains.vklimovich.printmanager;

import java.util.concurrent.*;


public class SimplePrintManager implements PrintManager {
    private int iterations;
    private char[] printSymbols;
    private final ExecutorService execService;
    private final Object monitor = new Object();
    private int counter = 0;

    public SimplePrintManager(){
        execService = Executors.newCachedThreadPool();
    }

    private void nextTurn(){
        counter = (counter + 1)%printSymbols.length;
    }

    @Override
    public synchronized void print(String seq, int iterations){
        this.iterations = iterations;
        printSymbols = seq.toCharArray();
        counter = 0;

        CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(execService);
        for(int i = 0;i < printSymbols.length;i++)
            cs.submit(new PrintTask(i));

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

        PrintTask(int order){
            this.order = order;
        }

        @Override
        public Integer call() {
            int curIter = 0;

            try {
                while(!Thread.interrupted() && ++curIter <= iterations) {
                    synchronized(monitor) {
                        while(counter != order)
                            monitor.wait();
                        System.out.print(printSymbols[order]);
                        nextTurn();
                        monitor.notifyAll();
                    }
                }
            } catch(InterruptedException e) {
                System.out.println("Interrupted");
            }

            return curIter-1;
        }

    }
}

