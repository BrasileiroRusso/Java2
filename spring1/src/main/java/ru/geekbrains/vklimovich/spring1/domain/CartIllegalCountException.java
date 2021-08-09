package ru.geekbrains.vklimovich.spring1.domain;

public class CartIllegalCountException extends Exception {
    public CartIllegalCountException(){

    }

    public CartIllegalCountException(String message){
        super(message);
    }

    public CartIllegalCountException(Throwable cause){
        super(cause);
    }
}
