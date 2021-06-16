package ru.geekbrains.vklimovich.numbers;

public class IntNumbers {
    private IntNumbers(){}

    public static int getNOD(int a, int b){
        a = Math.abs(a);
        b = Math.abs(b);

        int rest = a % b;
        while(rest != 0){
            a = b;
            b = rest;
            rest = a % b;
        }
        return b;
    }

    private static int recursiveGetNOD(int a, int b){
        int rest = a % b;
        if(rest == 0)
            return b;
        else
            return recursiveGetNOD(b, rest);
    }

    public static int recGetNOD(int a, int b){
        a = Math.abs(a);
        b = Math.abs(b);
        return recursiveGetNOD(a, b);
    }
}
