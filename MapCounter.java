package ru.geekbrains.vklimovich.objcount;

import java.util.*;

public class MapCounter {
    public static <T> Map<T, Integer> getObjList(T[] strArray){
        Map<T, Integer> objsMap = new TreeMap<T, Integer>();

        for(T curStr: strArray)
            objsMap.put(curStr, objsMap.getOrDefault(curStr, 0) + 1);

        return objsMap;
    }
}
