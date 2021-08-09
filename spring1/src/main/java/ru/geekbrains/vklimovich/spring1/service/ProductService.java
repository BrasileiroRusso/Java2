package ru.geekbrains.vklimovich.spring1.service;

import ru.geekbrains.vklimovich.spring1.domain.*;
import java.util.*;

public interface ProductService {
    List<Product> getProductCatalog();
    Product getProduct(long id);
}
