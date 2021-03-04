package ru.vklimovich.fruit;

// Виды апельсинов
public enum OrangeKind {
    NAVEL(1.5), BLOOD(1.65), TANGERINE(1.0), ACID_LESS(1.2),
    MANDARIN(0.9), SEVILLE(1.5), BERGAMOT(1.45), CLEMENTINE(1.6),
    TRIFOLIATA(1.4), CARA_CARA(1.6);

    private double weight;

    OrangeKind(double weight){
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }
}
