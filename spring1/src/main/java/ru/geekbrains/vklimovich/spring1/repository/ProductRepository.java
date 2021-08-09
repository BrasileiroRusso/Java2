package ru.geekbrains.vklimovich.spring1.repository;

import ru.geekbrains.vklimovich.spring1.domain.Product;
import java.util.List;

public interface ProductRepository {
    List<Product> getProductList();
    Product getProduct(long id);
}
