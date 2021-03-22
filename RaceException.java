package ru.geekbrains.vklimovich.races;

public class RaceException extends Exception{
    RaceException(String name){
        super(name);
    }

    RaceException(){
        super();
    }
}
