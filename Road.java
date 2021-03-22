package ru.geekbrains.vklimovich.races;

import java.util.concurrent.*;

public class Road extends Stage{
    public Road(double length){
        this.length = length;
        this.description = "Road";
    }

    @Override
    public void go(Car car) throws InterruptedException {
        System.out.println(car.getPilot() + " начал этап " + this);
        long time = Math.round((length/car.getSpeed())*1000);
        TimeUnit.MILLISECONDS.sleep(time);
        System.out.println(car.getPilot() + " закончил этап " + this);
    }

    @Override
    public String toString() {
        return description + ", " + length + "m";
    }
}
