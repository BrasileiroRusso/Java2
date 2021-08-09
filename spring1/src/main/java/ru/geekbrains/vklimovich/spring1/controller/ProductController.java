package ru.geekbrains.vklimovich.spring1.controller;

import org.springframework.stereotype.Component;
import ru.geekbrains.vklimovich.spring1.service.ProductService;

@Component
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;

        System.out.println("Controller constructor");
    }
}
