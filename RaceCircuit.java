package ru.geekbrains.vklimovich.races;

import java.util.*;

public class RaceCircuit {
    private List<Stage> stages;

    public List<Stage> getStages() {
        return stages;
    }

    public RaceCircuit(Stage... stages){
        this.stages = new ArrayList<>(Arrays.asList(stages));
    }

    public RaceCircuit(Collection<Stage> stages){
        this.stages = new ArrayList<Stage>(stages);
    }

}

