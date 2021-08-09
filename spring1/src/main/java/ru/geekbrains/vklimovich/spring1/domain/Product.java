package ru.geekbrains.vklimovich.spring1.domain;

import java.util.Objects;

public class Product {
    private static long counter = 0;

    private final long id = ++counter;
    private final String title;
    private final double cost;

    public Product(String title, double cost){
        this.title = title;
        this.cost = cost;
    }

    public String getTitle() {
        return title;
    }

    public double getCost() {
        return cost;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return title + " (cost " + cost + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
