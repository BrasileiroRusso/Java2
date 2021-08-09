package ru.geekbrains.vklimovich.spring1;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrains.vklimovich.spring1.configuration.ApplicationConfiguration;
import ru.geekbrains.vklimovich.spring1.controller.ProductController;
import ru.geekbrains.vklimovich.spring1.domain.Cart;
import ru.geekbrains.vklimovich.spring1.domain.CartIllegalCountException;
import ru.geekbrains.vklimovich.spring1.domain.Product;
import ru.geekbrains.vklimovich.spring1.service.ProductService;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
            Scanner input = new Scanner(System.in)
        )
        {
            char cmd = ' ';
            String[] cmdArgs;
            int count = 0;
            long productId;
            Product product = null;
            ProductController prodController = context.getBean("productController", ProductController.class);
            ProductService prodService = context.getBean("productService", ProductService.class);
            Cart cart = context.getBean("cart", Cart.class);

            System.out.println();
            System.out.println("Hello!");
            System.out.println();

            do{
                System.out.println("Enter command (catalog, add, remove, pay, display, exit) or first letter of command): ");
                cmdArgs = input.nextLine().trim().split("\\s+");
                if(cmdArgs.length == 0 || cmdArgs[0].isEmpty())
                    continue;
                cmd = cmdArgs[0].charAt(0);

                if(cmd == 'a' || cmd == 'r'){
                    try{
                        System.out.print("Enter product ID: ");
                        productId = Long.parseLong(input.nextLine());
                        System.out.print("Enter product units count: ");
                        count = Integer.parseInt(input.nextLine());
                    }
                    catch(NumberFormatException e){
                        System.out.println("Incorrect number value!");
                        continue;
                    }

                    product = prodService.getProduct(productId);
                    if(product == null){
                        System.out.printf("Product %2d doesn't exist, incorrect operation!%n", productId);
                        continue;
                    }
                    if(count <= 0){
                        System.out.println("The unit number " + count + " is incorrect!");
                        continue;
                    }
                }

                try{
                    switch(cmd){
                        case 'a':
                            cart.addProduct(product, count);
                            break;
                        case 'c':
                            displayCatalog(prodService.getProductCatalog());
                            break;
                        case 'd':
                            displayCart(cart);
                            break;
                        case 'p':
                            System.out.println("The order is payed");
                            cart = context.getBean("cart", Cart.class);
                            break;
                        case 'r':
                            cart.removeProduct(product, count);
                            break;
                        default:
                    }
                }
                catch(CartIllegalCountException e){
                    // Исключение возникнуть не может в силу проверки предусловий
                    System.out.print("It's impossible to remove " + count + " products ID = " + product.getId() + ". ");
                    System.out.println("Your cart contains " + cart.getProductAmount(product) + " units of this product.");
                    continue;
                }

            }
            while(cmd != 'e');
        }

    }

    public static void displayCatalog(List<Product> products){
        System.out.printf("--------------------------------------------------------%n");
        System.out.printf("| ID |         Product           |         Cost        |%n");
        System.out.printf("--------------------------------------------------------%n");
        for(Product p: products)
            System.out.printf("| %2d | %25s | %18.2f  |%n", p.getId(), p.getTitle(), p.getCost());
        System.out.printf("--------------------------------------------------------%n");
    }

    public static void displayCart(Cart cart){
        Map<Product, Integer> products = cart.getProducts();
        if(products.isEmpty()){
            System.out.println("Your cart is empty");
            return;
        }
        double totalCost = 0;
        System.out.println("Your cart includes:");
        System.out.printf("------------------------------------------------------------%n");
        System.out.printf("| ID |         Product           |  Count  |      Cost     |%n");
        System.out.printf("------------------------------------------------------------%n");
        for(Map.Entry<Product, Integer> p: products.entrySet()){
            Product product = p.getKey();
            int num = p.getValue();
            double cost = product.getCost() * num;
            totalCost += cost;
            System.out.printf("| %2d | %25s | %7d | %12.2f  |%n", product.getId(), product.getTitle(), num, cost);
        }
        System.out.printf("------------------------------------------------------------%n");
        System.out.printf("| %2s | %25s | %7s | %12.2f  |%n", "", "Total", "", totalCost);
        System.out.printf("------------------------------------------------------------%n");
    }
}
