package ru.geekbrains.vklimovich.myarrays;

import java.util.Arrays;
import java.util.Comparator;

public class MyArrays {

    public static <T> int search(T[] array, T searchElem){
        for(int i = 0;i < array.length;i++)
            if(array[i].equals(searchElem))
                return i;
        return -1;
    }

    public static <T> int binarySearch(T[] array, T searchElem, Comparator<? super T> cmp){
        int startIndex = 0, lastIndex = array.length - 1, curIndex, compareRes;

        while(startIndex <= lastIndex){
            curIndex = (startIndex + lastIndex)/2;
            compareRes = cmp.compare(array[curIndex], searchElem);
            if(compareRes == 0){
                return curIndex;
            }
            if(compareRes > 0)
                lastIndex = curIndex - 1;
            else
                startIndex = curIndex + 1;
        }

        return -1;
    }

    private static <T> int recBinarySearch(T[] array, int initPos, int finPos, T searchElem, Comparator<? super T> cmp){
        if(finPos < initPos)
            return -1;
        int curIndex = (initPos + finPos)/2;
        int compareRes = cmp.compare(array[curIndex], searchElem);
        if(compareRes == 0)
            return curIndex;
        if(compareRes < 0)
            return recBinarySearch(array, curIndex + 1, finPos, searchElem, cmp);
        else
            return recBinarySearch(array, initPos, curIndex - 1, searchElem, cmp);
    }

    public static <T> int recBinarySearch(T[] array, T searchElem, Comparator<? super T> cmp){
        return recBinarySearch(array, 0, array.length - 1, searchElem, cmp);
    }

    public static <T> int max(T[] array, Comparator<? super T> cmp){
        if(array.length <= 0)
            return -1;

        T t;
        int curIndex = 0;
        for(int i = 1; i < array.length; i++)
            if(cmp.compare(array[i], array[curIndex]) > 0)
                curIndex = i;

        return curIndex;
    }

    public static <T extends Comparable<? super T>> int max(T[] array){
        return max(array, Comparator.naturalOrder());
    }

    public static <T> int min(T[] array, Comparator<? super T> cmp){
        return max(array, cmp.reversed());
    }

    public static <T extends Comparable<? super T>> int min(T[] array){
        return min(array, Comparator.naturalOrder());
    }

    public static <T extends Comparable<? super T>> int binarySearch(T[] array, T searchElem){
        return binarySearch(array, searchElem, Comparator.naturalOrder());
    }

    public static <T extends Comparable<? super T>> int recBinarySearch(T[] array, T searchElem){
        return recBinarySearch(array, searchElem, Comparator.naturalOrder());
    }

    private static <T> void bubbleSort(T[] array, Comparator<? super T> cmp){
        T t;
        for(int len = array.length - 1; len > 0; len--)
            for(int i = 0; i < len; i++)
                if(cmp.compare(array[i], array[i+1]) > 0){
                    t = array[i];
                    array[i] = array[i+1];
                    array[i+1] = t;
                }
    }

    private static <T> void selectSort(T[] array, Comparator<? super T> cmp){
        T t;
        int curIndex;

        for(int step = 0; step < array.length - 1; step++){
            curIndex = step;
            for(int i = step + 1; i < array.length; i++){
                if(cmp.compare(array[i], array[curIndex]) < 0)
                    curIndex = i;
            }

            if(curIndex > step){
                t = array[step];
                array[step] = array[curIndex];
                array[curIndex] = t;
            }
        }
    }

    private static <T> void insertSort(T[] array, Comparator<? super T> cmp){
        T t;
        int curIndex;

        for(int i = 1; i < array.length; i++){
            t = array[i];
            curIndex = i - 1;
            while(curIndex >= 0 && cmp.compare(array[curIndex], t) > 0){
                array[curIndex+1] = array[curIndex];
                curIndex--;
            }
            array[curIndex+1] = t;
        }
    }

    private static <T> void oddEvenSort(T[] array, Comparator<? super T> cmp){
        T t;
        boolean isContinue = true;

        while(isContinue){
            isContinue = false;
            for(int startPos = 1; startPos >= 0;startPos--){
                for(int i = startPos; i < array.length - 1; i += 2){
                    if(cmp.compare(array[i], array[i+1]) > 0){
                        t = array[i];
                        array[i] = array[i+1];
                        array[i+1] = t;
                        isContinue = true;
                    }
                }
            }
        }

    }

    private static <T> void systemSort(T[] array, Comparator<? super T> cmp){
        Arrays.sort(array, cmp);
    }

    private static <T> void merge(T[] destArray, T[] sourceArray, int initPos, int middlePos, int finPos, Comparator<? super T> cmp){
        int i = initPos, j = middlePos + 1, k = initPos;
        while(i <= middlePos && j <= finPos){
            if(cmp.compare(sourceArray[i], sourceArray[j]) <= 0)
                destArray[k++] = sourceArray[i++];
            else
                destArray[k++] = sourceArray[j++];
        }
        while(i <= middlePos)
            destArray[k++] = sourceArray[i++];
        while(j <= finPos)
            destArray[k++] = sourceArray[j++];
    }

    private static <T> void mergeSort(T[] array, T[] tempArray, int initPos, int finPos, Comparator<? super T> cmp){
        if(finPos <= initPos)
            return;
        int middlePos = (initPos + finPos)/2;
        mergeSort(array, tempArray, initPos, middlePos, cmp);
        mergeSort(array, tempArray, middlePos + 1, finPos, cmp);
        merge(array, tempArray, initPos, middlePos, finPos, cmp);
        System.arraycopy(array, initPos, tempArray, initPos, finPos - initPos + 1);
    }

    private static <T> void mergeSort(T[] array, Comparator<? super T> cmp){
        T[] tempArray = array.clone();
        mergeSort(array, tempArray, 0, array.length - 1, cmp);
    }

    public static <T> void sort(T[] array, SortAlg alg, Comparator<? super T> cmp){
        switch (alg){
            case BUBBLE:
                bubbleSort(array, cmp);
                break;
            case SELECT:
                selectSort(array, cmp);
                break;
            case INSERT:
                insertSort(array, cmp);
                break;
            case ODDEVEN:
                oddEvenSort(array, cmp);
                break;
            case MERGE:
                mergeSort(array, cmp);
                break;
            case SYSTEM:
                systemSort(array, cmp);
                break;
            default:
        }
    }

    public static <T extends Comparable<? super T>> void sort(T[] array, SortAlg alg){
        sort(array, alg, Comparator.naturalOrder());
    }

    public static <T> void sort(T[] array, Comparator<? super T> cmp){
        sort(array, SortAlg.INSERT, cmp);
    }

    public static <T extends Comparable<? super T>> void sort(T[] array){
        sort(array, SortAlg.INSERT, Comparator.naturalOrder());
    }

    private MyArrays(){}

}
