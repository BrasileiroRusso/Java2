package ru.geekbrains.vklimovich.Tournament;

import java.util.*;

public class Tournament {
    private String name;
    private ArrayList<Moveable> rivals;
    private ArrayList<Obstacling> obstacles;
    private TryToPass tryToPass;

    private class TryToPass{
        boolean tryToPassObstacle(Moveable rival, Obstacling obstacle){
            boolean result = true;
            ObstacleType obstacleType = obstacle.getType();

            if (rival == null)
                return true;

            switch(obstacleType){
                case JUMPING:
                    result = rival.jump(obstacle.getSize());
                    break;
                case RUNNING:
                    result = rival.run(obstacle.getSize());
                    break;
                default:
                    System.out.println("Unknown obstacle type!");
            }

            return result;
        }
    }

    public Tournament(String name){
        this.name = name;
        rivals = new ArrayList<Moveable>();
        obstacles = new ArrayList<Obstacling>();
        tryToPass = new TryToPass();
    }

    public void addRival(Moveable rival){
        rivals.add(rival);
    }

    public void addObstacle(Obstacling obstacle){
        obstacles.add(obstacle);
    }

    public boolean removeRival(Moveable rival){
        return rivals.remove(rival);
    }

    public boolean removeObstacle(Obstacling obstacle){
        return obstacles.remove(obstacle);
    }

    public void clear(){
        rivals.clear();
        obstacles.clear();
    }

    public void startCompetition(){
       ArrayList<Moveable> copyRivals = new ArrayList<Moveable>(rivals);
       Iterator<Moveable> rivalIterator;
       Moveable curRival;

       System.out.println(this + " starts!");

       for(Obstacling obstacle: obstacles){
           System.out.println("Current obstacle is " + obstacle + "!");
           rivalIterator = copyRivals.iterator();
           while(rivalIterator.hasNext()){
               curRival = rivalIterator.next();
               if (!tryToPass.tryToPassObstacle(curRival, obstacle)){
                   rivalIterator.remove();
                   System.out.println(curRival + " is eliminated from the tournament!");
               }
           }
           System.out.println();
       }

       System.out.println(this + " is finished!");
       if (copyRivals.isEmpty()){
           System.out.println("There are no winners!");
       }
       else{
           System.out.println("Our winners are:");
           for(Moveable rival: copyRivals){
               System.out.println(rival);
           }
           System.out.println("Congratulations!");
       }

    }

    @Override
    public String toString() {
        return "Tournament " + name;
    }

    public static void main(String[] args) {
        Tournament torneo = new Tournament("Ironman 2021");

        torneo.addRival(new Cat("Napoleon"));
        torneo.addRival(new Cat("Caesar", 1000, 2.5));
        torneo.addRival(new Homo("Jose"));
        torneo.addRival(new Robot("Terminator"));
        torneo.addRival(new Robot("T1000", 100000, 4));
        torneo.addRival(new Homo("Arnold Schwarzenegger", 40000, 3));

        torneo.addObstacle(new Track(100));
        torneo.addObstacle(new Wall(2));
        torneo.addObstacle(new Track(200));
        torneo.addObstacle(new Wall(2.3));

        torneo.startCompetition();

        System.out.println();
        for(DayOfWeek day: DayOfWeek.values()){
            if (!DayOfWeek.isWorkDay(day))
                System.out.println("Day " + day + " is holiday");
            else
                System.out.println("Day " + day + ": working hours = " + DayOfWeek.getWorkingHours(day));
        }

    }
}
