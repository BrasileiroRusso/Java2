package ru.geekbrains.vklimovich.printmanager;

public interface PrintManager {
    public void print(String seq, int iterations);
    public void close();
}
