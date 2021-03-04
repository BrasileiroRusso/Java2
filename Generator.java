package ru.vklimovich.util;

// Генератор значений заданного типа
public interface Generator<T> {
    T next();
}
