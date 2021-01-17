package ru.geekbrains.vklimovich.Tournament;

public class Wall implements Obstacling {
    private double height;

    public Wall(double height){
        this.height = height;
    }

    @Override
    public ObstacleType getType() {
        return ObstacleType.JUMPING;
    }

    @Override
    public double getSize() {
        return height;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ", height = " + height + "m";
    }
}
