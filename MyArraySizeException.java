package ru.geekbrains.vklimovich.matrix;

public class MyArraySizeException extends Exception {
    public MyArraySizeException(){
        super("The array is not a matrix");
    }
}
