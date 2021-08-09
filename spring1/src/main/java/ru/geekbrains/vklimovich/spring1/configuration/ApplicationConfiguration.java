package ru.geekbrains.vklimovich.spring1.configuration;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.geekbrains.vklimovich.spring1.domain.Cart;
import ru.geekbrains.vklimovich.spring1.domain.ProdCart;

@Configuration
@ComponentScan({"ru.geekbrains.vklimovich.spring1.repository",
                "ru.geekbrains.vklimovich.spring1.controller",
                "ru.geekbrains.vklimovich.spring1.service"})
public class ApplicationConfiguration {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Cart cart() {
        return new ProdCart();
    }
}
