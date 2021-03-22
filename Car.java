package ru.geekbrains.vklimovich.races;

import java.util.concurrent.*;
import java.util.*;

public class Car {
    private static final Random rand = new Random();
    private static final int PREPARE_TIME = 3000;
    private static final int PREPARE_TIME_DIF = 2000;

    private final String team;
    private final String pilot;
    private final double speed;

    public Car(String team, String pilot, double speed){
        this.team = team;
        this.pilot = pilot;
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public String getTeam() {
        return team;
    }

    public String getPilot() {
        return pilot;
    }

    public void prepare() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(PREPARE_TIME + rand.nextInt(PREPARE_TIME_DIF));
    }

    @Override
    public String toString() {
        return team + ", " + pilot;
    }
}
