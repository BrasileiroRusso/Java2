package ru.geekbrains.vklimovich.telephonebook;

import java.util.*;

public class TelephoneBook {
    private Map<String, Set<String>> telBook;

    public TelephoneBook(){
        telBook = new HashMap<String, Set<String>>();
    }

    public boolean add(String name, String telephone){
        name = name.trim();
        telephone = telephone.trim();

        if (name.isEmpty() || telephone.isEmpty())
            return false;

        name = convertName(name);
        Set<String> set = telBook.get(name);

        if (set == null){
            set = new TreeSet<String>();
            telBook.put(name, set);
        }
        set.add(telephone);

        return true;
    }

    public Set<String> getTelephoneList(String name){
        return telBook.get(convertName(name));
    }

    private String convertName(String name){
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public void printTelephoneList(String name){
        Set<String> telList = getTelephoneList(name);

        if (telList == null){
            System.out.println("There is no contact information for " + name + "!");
        }
        else{
            System.out.println("The phone number list for " + name + ":");
            for(String tel: telList)
                System.out.print(tel + " ");
            System.out.println();
        }
    }

    public void printTelephoneList(){
        for(String name: telBook.keySet())
            printTelephoneList(name);

    }

}
