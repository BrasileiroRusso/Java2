package ru.geekbrains.vklimovich.spring1.repository.impl;

import org.springframework.stereotype.Repository;
import ru.geekbrains.vklimovich.spring1.domain.Product;
import ru.geekbrains.vklimovich.spring1.repository.ProductRepository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ListProductRepository implements ProductRepository {
    private Map<Long, Product> prodCatalog;

    @Override
    public List<Product> getProductList() {
        return Collections.unmodifiableList(new ArrayList<>(prodCatalog.values()));
    }

    @Override
    public Product getProduct(long id) {
        return prodCatalog.get(id);
    }

    public ListProductRepository(){
        prodCatalog = new HashMap<>();
        System.out.println("Repository constructor");
    }

    @PostConstruct
    private void init() {
        Product[] products = {
                new Product("Samsung A51", 25000),
                new Product("Samsung A31", 20000),
                new Product("Bork", 100000),
                new Product("DeLonghi", 90000),
                new Product("Dyson", 80000)
        };
        for(Product p: products)
            prodCatalog.put(p.getId(), p);

        System.out.println("Repository init");
    }

}
