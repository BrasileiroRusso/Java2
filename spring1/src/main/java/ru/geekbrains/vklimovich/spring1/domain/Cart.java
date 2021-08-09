package ru.geekbrains.vklimovich.spring1.domain;

import java.util.*;

public interface Cart {
    void addProduct(Product product, int num) throws CartIllegalCountException;
    void removeProduct(Product product, int num) throws CartIllegalCountException;
    Map<Product, Integer> getProducts();
    int getProductAmount(Product product);
}
