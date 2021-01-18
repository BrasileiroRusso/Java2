package ru.geekbrains.vklimovich.matrix;

import java.util.*;

public class Matrix {
    public static int getDeterminant(int[][] matrix) throws MyArraySizeException {
        if (!isMatrix(matrix))
            throw new MyArraySizeException();
        else
            return calcDet(matrix, new HashSet<Integer>());
    }

    public static int getDeterminant(String[][] matrix) throws MyArraySizeException, MyArrayDataException {
        return getDeterminant(convertToMatrix(matrix));
    }

    public static int getTotalSum(int[][] matrix) throws MyArraySizeException {
        int result = 0;

        if (!isMatrix(matrix))
            throw new MyArraySizeException();

        for(int i = 0;i < matrix.length;i++)
            for(int j = 0;j < matrix[i].length;j++)
                result += matrix[i][j];

        return result;
    }

    public static int getTotalSum(String[][] matrix) throws MyArraySizeException, MyArrayDataException{
        return getTotalSum(convertToMatrix(matrix));
    }

    public static double getMatrixNorm(int[][] matrix) throws MyArraySizeException {
        int result = 0;

        if (!isMatrix(matrix))
            throw new MyArraySizeException();

        for(int i = 0;i < matrix.length;i++)
            for(int j = 0;j < matrix[i].length;j++)
                result += matrix[i][j] * matrix[i][j];

        return Math.sqrt(result);
    }

    public static double getMatrixNorm(String[][] matrix) throws MyArraySizeException, MyArrayDataException {
        return getMatrixNorm(convertToMatrix(matrix));
    }

    private static boolean isMatrix(int[][] matrix){
        for(int i = 0;i < matrix.length;i++)
            if (!(matrix[i].length == matrix.length))
                return false;

        return true;
    }

    private static boolean isMatrix(Object[][] matrix){
        for(int i = 0;i < matrix.length;i++)
            if (!(matrix[i].length == matrix.length))
                return false;

        return true;
    }

    private static int calcDet(int[][] matrix, HashSet<Integer> exceptCols){
        int startRow = exceptCols.size();
        int determinant = 0;
        int coeff, colNum = 0;
        boolean isOneElem = (startRow + 1 == matrix.length);

        for(int j = 0;j < matrix.length;j++)
            if (!exceptCols.contains(j)){
                 if (isOneElem){
                     return matrix[startRow][j];
                 }
                 else{
                     coeff = ((j - colNum)%2 == 0?1:-1);
                     exceptCols.add(j);
                     determinant += coeff * matrix[startRow][j] * calcDet(matrix, exceptCols);
                     exceptCols.remove(j);
                 }
            }
            else{
                 colNum++;
            }

        return determinant;

    }

    private static int[][] convertToMatrix(String[][] strMatrix) throws MyArraySizeException, MyArrayDataException{
        int[][] result = new int[strMatrix.length][];

        if (!isMatrix(strMatrix))
            throw new MyArraySizeException();

        for(int i = 0;i < strMatrix.length;i++){
            result[i] = new int[strMatrix[i].length];
            for(int j = 0;j < strMatrix[i].length;j++)
                try{
                    result[i][j] = Integer.parseInt(strMatrix[i][j]);
                }
                catch(NumberFormatException e){
                    throw new MyArrayDataException(i, j);
                }
        }
        return result;

    }

}
