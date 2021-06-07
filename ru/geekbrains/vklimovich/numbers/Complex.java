package ru.geekbrains.vklimovich.numbers;

import java.util.concurrent.*;
import java.util.function.*;

public final class Complex {
    private final double re;
    private final double im;
    private final double radius;
    private final double arg;

    public static final Complex I = new Complex(0, 1);
    public static final Complex ONE = new Complex(1, 0);
    public static final Complex ZERO = new Complex(0, 0);

    private Complex(double re, double im){
        this.re = re;
        this.im = im;
        radius = (re == 0?Math.abs(im):(im == 0?Math.abs(re):Math.sqrt(re*re + im*im)));
        arg = Math.asin(im/radius);
    }

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

    public double getArg() {
        return arg;
    }

    public double getRadius() {
        return radius;
    }

    public double abs() {
        return getRadius();
    }

    @Override
    public String toString() {
        return re + " + " + im + "i";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Complex))
            return false;
        Complex z = (Complex) o;
        return Double.compare(z.re, re) == 0 && Double.compare(z.im, im) == 0;
    }

    @Override
    public int hashCode() {
        return 31*Double.hashCode(re) + Double.hashCode(im);
    }

    public static Complex valueOf(double re, double im){
        return new Complex(re, im);
    }

    public static Complex valueOf(double re){
        return new Complex(re, 0);
    }

    public static Complex sum(Complex u, Complex v){
        return valueOf(u.re + v.re, u.im + v.im);
    }

    public static Complex prod(Complex u, Complex v){
        return valueOf(u.re*v.re - u.im*v.im, u.im*v.re + u.re*v.im);
    }

    public static Complex diff(Complex u, Complex v){
        return valueOf(u.re - v.re, u.im - v.im);
    }

    public static Complex div(Complex u, Complex v){
        if(v.im == 0)
            return valueOf(u.re/v.re, u.im/v.re);
        if(v.re == 0)
            return valueOf(u.im/v.im, -u.re/v.im);
        double radSqr = v.radius * v.radius;
        return valueOf((u.re*v.re + u.im*v.im)/radSqr, (u.im*v.re - u.re*v.im)/radSqr);
    }

    public static Supplier<Complex> generator(){
        return () -> valueOf(Math.random(), Math.random());
    }

    public static Supplier<Complex> intGenerator(int bound){
        return () -> valueOf(ThreadLocalRandom.current().nextInt(bound),
                             ThreadLocalRandom.current().nextInt(bound));
    }

}
