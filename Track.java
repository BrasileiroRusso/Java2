package ru.geekbrains.vklimovich.Tournament;

public class Track implements Obstacling {
    private double distance;

    public Track(double distance){
        this.distance = distance;
    }

    @Override
    public ObstacleType getType() {
        return ObstacleType.RUNNING;
    }

    @Override
    public double getSize() {
        return distance;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ", distance = " + distance + "m";
    }
}
