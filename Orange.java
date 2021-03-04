package ru.vklimovich.fruit;

import ru.vklimovich.util.*;
import java.util.*;

public class Orange extends Fruit {
    private static long count = 0;

    private OrangeKind kind;
    private final long id = ++count;

    public Orange(OrangeKind kind){
        this.kind = kind;
    }

    @Override
    public double getWeight() {
        return kind.getWeight();
    }

    @Override
    public String toString() {
        return "Orange" + id + "(" + kind.name() + ")";
    }

    public static Generator<Orange> randomGenerator(){
        return new Generator<Orange>(){
            private Random rand = new Random();
            @Override
            public Orange next() {
                return new Orange(OrangeKind.values()[rand.nextInt(OrangeKind.values().length)]);
            }
        };
    }
}
