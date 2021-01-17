package ru.geekbrains.vklimovich.Tournament;

public class Robot implements Moveable {
    private double maxDistance, maxHeight;
    private String name;

    public Robot(String name){
        this.name = name;
        maxDistance = 50;
        maxHeight = 0.5;
    }

    public Robot(String name, double maxDistance, double maxHeight){
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
        return this.getClass().getSimpleName() + " " + name;
    }

    private boolean getMove(double distance, double maxDistance, String moveType){
        boolean isPosible = distance <= maxDistance;
        System.out.println(this + " " + (isPosible?moveType + "s":"can't " + moveType) + " " + distance + "m");
        return isPosible;
    }
}
