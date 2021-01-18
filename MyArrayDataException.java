package ru.geekbrains.vklimovich.matrix;

public class MyArrayDataException extends Exception {
    private int[] indexes;

    public MyArrayDataException(int i, int j){
        super("Non-numeric data in matrix");
        indexes = new int[] {i, j};
    }

    public int[] getIndexes(){
        return indexes;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + ", position [" + indexes[0] + ", " + indexes[1] + "]";
    }
}
