package ru.geekbrains.vklimovich.spring1.service.impl;

import org.springframework.stereotype.Service;
import ru.geekbrains.vklimovich.spring1.domain.Product;
import ru.geekbrains.vklimovich.spring1.repository.ProductRepository;
import ru.geekbrains.vklimovich.spring1.service.ProductService;
import java.util.List;

@Service("productService")
public class MyProductService implements ProductService {

    private final ProductRepository productRepository;

    public MyProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        System.out.println("MyProductService constructor");
    }

    @Override
    public List<Product> getProductCatalog() {
        return productRepository.getProductList();
    }

    @Override
    public Product getProduct(long id) {
        return productRepository.getProduct(id);
    }
}
