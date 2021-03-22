import ru.geekbrains.vklimovich.races.*;

import java.util.*;

public class Lesson3_5 {

    public static void main(String[] args) {
        RaceCircuit circuit = new RaceCircuit(new Road(400.0),
                                              new Tunnel(600.0, 6),
                                              new Road(500.0),
                                              new Tunnel(300, 4),
                                              new Road(300.0)
                                             );

        List<Car> cars = new ArrayList<Car>();
        cars.add(new Car("Ferrari", "Carlos Sainz", 88.0));
        cars.add(new Car("Mercedes", "Lewis Hamilton", 90.0));
        cars.add(new Car("Red Bull", "Max Verstappen", 88.5));
        cars.add(new Car("McLaren", "Daniel Ricciardo", 89.0));
        cars.add(new Car("McLaren", "Ayrton Senna", 92.0));
        cars.add(new Car("McLaren", "Alain Prost", 91.8));
        cars.add(new Car("Ferrari", "Niki Lauda", 90.0));
        cars.add(new Car("Ferrari", "Michael Schumacher", 90.6));
        cars.add(new Car("Renault", "Fernando Alonso", 91.0));
        cars.add(new Car("McLaren", "Mika Hakkinen", 90.5));
        GrandPrix grandPrix = new GrandPrix(circuit, cars,"Гран-При Великобритании Silverstone");
        try {
            grandPrix.goRace();
        } catch (RaceException e) {
            System.out.println(e.getMessage());
        }
    }
}
