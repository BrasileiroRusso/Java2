package ru.vklimovich.fruit;

import ru.vklimovich.util.*;
import java.util.*;

public class Apple extends Fruit {
    private static long count = 0;

    private AppleKind kind;
    private final long id = ++count;

    public Apple(AppleKind kind){
        this.kind = kind;
    }

    @Override
    public double getWeight() {
        return kind.getWeight();
    }

    @Override
    public String toString() {
        return "Apple" + id + "(" + kind.name() + ")";
    }

    public static Generator<Apple> randomGenerator(){
        return new Generator<Apple>(){
            private Random rand = new Random();
            @Override
            public Apple next() {
                return new Apple(AppleKind.values()[rand.nextInt(AppleKind.values().length)]);
            }
        };
    }

}
