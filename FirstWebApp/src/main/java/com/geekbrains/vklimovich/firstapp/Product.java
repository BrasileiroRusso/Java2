package com.geekbrains.vklimovich.firstapp;

import java.util.concurrent.ThreadLocalRandom;

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

    @Override
    public String toString() {
        return title + " (cost " + cost + ")";
    }

    public static Product[] randomProductList(int num){
        String[] prodTypes = {"Smartphone", "Notebook", "TV", "eBook Reader"};
        String[] firmList = {"Samsung", "Apple", "Sony", "LG", "Panasonic"};
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        Product[] list = new Product[num];

        for(int i = 0;i < num;i++){
            String product = prodTypes[rand.nextInt(prodTypes.length)] + " ";
            product += firmList[rand.nextInt(firmList.length)] + " ";
            product += (char)('A' + rand.nextInt(26));
            product += rand.nextInt(1000);
            double cost = Math.round(rand.nextDouble(1000) * 10000)/100.0;
            list[i] = new Product(product, cost);
        }
        return list;
    }
}
