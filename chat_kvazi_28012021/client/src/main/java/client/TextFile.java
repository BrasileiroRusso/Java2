package client;

import java.util.*;
import java.io.*;

class TextFile implements Iterable<String> {
    private List<String> list = new ArrayList<String>();

    public TextFile(String fileName) {
        File file = new File(fileName);

        if(file.isFile() && file.exists()){
            try {
                BufferedReader in = new BufferedReader(new FileReader(
                        new File(fileName).getAbsoluteFile()));
                try {
                    String s;
                    while ((s = in.readLine()) != null) {
                        list.add(s);
                    }
                } finally {
                    in.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public Iterator<String> iterator() {
        return iterator(0, list.size()-1);
    }

    public Iterator<String> iterator(int startPos, int lastPos) {
        return new Iterator<String>() {
            private int index = startPos;

            @Override
            public boolean hasNext() {
                return index <= lastPos;
            }

            @Override
            public String next() {
                return list.get(index++);
            }
        };
    }

    public Iterator<String> lastIterator(int elemNum) {
        return iterator((list.size() >= elemNum?list.size()-elemNum:0), list.size() - 1);
    }

    public boolean add(String string){
        return list.add(string);
    }

    public int getSize(){
        return list.size();
    }

    public static void writeToFile(TextFile textFile, String fileName) {
        try {
            PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile());
            try {
                for(String s: textFile)
                    out.println(s);
            } finally {
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return list.toString();
    }




}
