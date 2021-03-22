package ru.geekbrains.vklimovich.races;

import java.util.concurrent.*;

public class Tunnel extends Stage{
    private int capacity;
    private Semaphore semaphore;

    public Tunnel(double length, int capacity){
        if(capacity <= 0)
            throw new IllegalArgumentException();
        this.length = length;
        this.capacity = capacity;
        semaphore = new Semaphore(capacity, true);
        description = "Tunnel";
    }

    @Override
    public void go(Car car) throws InterruptedException {
        if(!semaphore.tryAcquire()){
            System.out.println(car.getPilot() + " ожидает на въезде в " + this);
            semaphore.acquire();
        }
        try{
            System.out.println(car.getPilot() + " начал этап " + this);
            long time = Math.round((length/car.getSpeed())*1000);
            TimeUnit.MILLISECONDS.sleep(time);
            System.out.println(car.getPilot() + " закончил этап " + this);
        }
        finally{
            semaphore.release();
        }
    }

    @Override
    public String toString() {
        return description + ", " + length + "m (max: " + capacity + ")";
    }
}
