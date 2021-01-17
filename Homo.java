package ru.geekbrains.vklimovich.Tournament;

public class Homo implements Moveable {
    private double maxDistance, maxHeight;
    private String name;

    public Homo(String name){
        this.name = name;
        maxDistance = 1000;
        maxHeight = 1.5;
    }

    public Homo(String name, double maxDistance, double maxHeight){
        this.name = name;
        this.maxDistance = maxDistance;
        this.maxHeight = maxHeight;
    }

    @Override
    public boolean jump(double height) {
        return getMove(height, maxHeight, "jump");
    }

    @Override
    public boolean run(double distance) {
        return getMove(distance, maxDistance, "run");
    }

    @Override
    public String toString() {
        return "Human " + name;
    }

    private boolean getMove(double distance, double maxDistance, String moveType){
        boolean isPosible = distance <= maxDistance;
        System.out.println(this + " " + (isPosible?moveType + "s":"can't " + moveType) + " " + distance + "m");
        return isPosible;
    }
}
