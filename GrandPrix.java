package ru.geekbrains.vklimovich.races;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class GrandPrix {
    private final RaceCircuit circuit;
    private final List<Car> carList;
    private final String name;
    private final ExecutorService execService;
    private final ReentrantLock lock;

    public GrandPrix(RaceCircuit circuit, List<Car> carList, String name){
        this.circuit = circuit;
        this.carList = Collections.unmodifiableList(new ArrayList<Car>(carList));
        this.name = name;
        execService = Executors.newFixedThreadPool(carList.size());
        lock = new ReentrantLock(true);
    }

    private static class RaceResult{
        final Car car;
        final long finishTime;
        RaceResult(Car car, long finishTime){
            this.car = car;
            this.finishTime = finishTime;
        }
    }

    private static class PrepareTask implements Runnable {
        Car car;
        CountDownLatch latch;

        PrepareTask(Car car, CountDownLatch latch){
            this.car = car;
            this.latch = latch;
        }

        @Override
        public void run() {
            System.out.println(car.getPilot() + " готовится");
            try {
                car.prepare();
                System.out.println(car.getPilot() + " готов к началу гонки");
                latch.countDown();
            } catch (InterruptedException e) {
                System.out.println("Interrupted" + this);;
            }
        }
    }

    private class RaceTask implements Callable<RaceResult>{
        Car car;
        String pilot;
        Iterator<Stage> iterStages;
        CountDownLatch startLatch;

        RaceTask(Car car, Iterator<Stage> iterStages, CountDownLatch startLatch){
            this.car = car;
            this.iterStages = iterStages;
            this.startLatch = startLatch;
            this.pilot = car.getPilot();
        }

        @Override
        public RaceResult call() throws InterruptedException {
            Long finishTime = null;

            startLatch.await();
            while(iterStages.hasNext()){
                Stage curStage = iterStages.next();
                curStage.go(car);
            }

            lock.lock();
            try{
                finishTime = System.nanoTime();
                System.out.println(pilot + " финишировал!");
                return new RaceResult(car, finishTime);
            }
            finally{
                lock.unlock();
            }

        }
    }

    public void goRace() throws RaceException{
        long startTime;
        CountDownLatch prepareLatch = new CountDownLatch(carList.size());
        CountDownLatch startLatch = new CountDownLatch(1);
        CompletionService<RaceResult> completeService = new ExecutorCompletionService<RaceResult>(execService);
        Queue<String> raceResults = new LinkedList<String>();

        System.out.println("Добро пожаловать на гонку " + name + "!");
        for(Car car: carList)
            execService.execute(new PrepareTask(car, prepareLatch));
        try {
            prepareLatch.await();
        } catch (InterruptedException e) {
            execService.shutdownNow();
            throw new RaceException("Гонка прервана");
        }

        System.out.println("Все пилоты готовы к старту, прогревочный круг завершен.");
        System.out.println("Болиды выстроились на стартовой решетке, считанные секунды до старта!");
        for(Car car: carList)
            completeService.submit(new RaceTask(car, circuit.getStages().iterator(), startLatch));

        try{
            for(int i = 0; i < 5; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Загорается светофор " + (i+1));
            }
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Гаснут стартовые огни, гонка началась!");
            startTime = System.nanoTime();
            startLatch.countDown();
        } catch (InterruptedException e) {
            execService.shutdownNow();
            throw new RaceException("Гонка прервана");
        }

        int pos = 1;
        for(int i = 0; i < carList.size();i++){
            try {
                Future<RaceResult> fut = completeService.take();
                RaceResult result = fut.get();
                raceResults.add(String.format("%d) %s (%s) - %dms", pos, result.car.getPilot(), result.car.getTeam(), (result.finishTime - startTime)/1000000));
                pos++;
            } catch (InterruptedException e) {
                execService.shutdownNow();
                throw new RaceException("Гонка прервана");
            } catch (ExecutionException e) {
                execService.shutdownNow();
                throw new RuntimeException();
            }
        }

        execService.shutdown();

        System.out.println();
        System.out.println("Результаты Гран-При:");
        while(!raceResults.isEmpty())
            System.out.println(raceResults.remove());

    }

}
