package ru.geekbrains.vklimovich.races;

public abstract class Stage {
    protected double length;
    protected String description;

    public String getDescription() {
        return description;
    }

    public double getLength() {
        return length;
    }

    public abstract void go(Car car) throws InterruptedException;

}