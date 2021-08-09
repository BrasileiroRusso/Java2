package ru.geekbrains.vklimovich.spring1.domain;

import java.util.*;

public class ProdCart implements Cart{
    private Map<Product, Integer> products;

    public ProdCart(){
        products = new HashMap<>();
        System.out.println("New cart");
    }

    @Override
    public void addProduct(Product product, int num) throws CartIllegalCountException {
        Objects.requireNonNull(product);
        if(num < 0)
            throw new CartIllegalCountException("Negative product units count!");
        products.put(product, products.getOrDefault(product, 0) + num);
    }

    @Override
    public void removeProduct(Product product, int num) throws CartIllegalCountException {
        int curNum = getProductAmount(product);
        curNum -= num;
        if(curNum < 0)
            throw new CartIllegalCountException("Too many product units to remove!");
        if(curNum == 0)
            products.remove(product);
        else
            products.put(product, curNum);
    }

    @Override
    public Map<Product, Integer> getProducts(){
        return Collections.unmodifiableMap(new HashMap<>(products));
    }

    @Override
    public int getProductAmount(Product product){
        return products.getOrDefault(product, 0);
    };
}
