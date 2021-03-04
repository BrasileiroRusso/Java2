package ru.vklimovich.fruit;

// Виды яблок
public enum AppleKind {
    GRANNY_SMITH(1.0), FUJI(1.1), PINK_LADY(1.2),
    GALA(1.2), ENVY(1.1), JAZZ(1.3), RED_DELICIOUS(1.15),
    GOLDEN(1.0), AMBROSIA(1.25), EMPIRE(1.2), OPAL(1.0),
    WINESAPP(1.35);

    private double weight;

    AppleKind(double weight){
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return getDeclaringClass().getSimpleName();
    }
}
