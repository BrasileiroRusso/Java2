package ru.geekbrains.vklimovich.Lesson2_2;

import ru.geekbrains.vklimovich.matrix.*;
import java.io.*;
import java.util.Arrays;

public class Lesson2_2 {
    public static void main(String[] args) throws IOException {
        String[][] matrixA;

        while(true){
            try{
                matrixA = getStrArray();
                System.out.println();
                System.out.println("A = " + Arrays.deepToString(matrixA));
                System.out.println("det A = " + Matrix.getDeterminant(matrixA));
                System.out.println("Total elements summ = " + Matrix.getTotalSum(matrixA));
                System.out.println("Norm of A: ||A|| = " + Matrix.getMatrixNorm(matrixA));
                System.out.println();
            }
            catch(MyArraySizeException e){
                System.out.println(e.getMessage());
            }
            catch(MyArrayDataException e){
                System.out.println(e.getMessage());
            }
            finally{
                System.out.println();
            }
        }

    }

    private static String[][] getStrArray() throws IOException {
        InputStreamReader streamReader = new InputStreamReader(System.in);
        BufferedReader buffReader = new BufferedReader(streamReader);
        int rowNum;
        String[][] result;
        String s;

        while (true){
            System.out.print("Enter the rows number: ");
            s = buffReader.readLine();
            try{
                rowNum = Integer.parseInt(s);
                result = new String[rowNum][];
                break;
            }
            catch(NumberFormatException | NegativeArraySizeException e){
                System.out.println("Incorrect rows number!");
            }
        }

        rowNum = 0;

        while (rowNum < result.length){
            System.out.print("Enter values for the row N " + rowNum + " (separated by space): ");
            s = buffReader.readLine().trim();
            if (!s.isEmpty()){
                result[rowNum++] = s.split(" ");
            }
        }

        return result;
    }
}
