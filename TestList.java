import ru.geekbrains.vklimovich.vunit.*;

import java.util.*;

public class TestList {
    private static final int SIZE = 20000;

    private List<Integer> list;
    private int testSize;

    public TestList(int testSize){
        this.testSize = testSize;
    }

    @ObjectInit
    public static TestList getTestInstance(){
        return new TestList(SIZE);
    }

    @BeforeSuite
    public void initTest(){
        list = new LinkedList<Integer>();
        for(int i = 0; i < testSize;i++)
            list.add(i);
    }

    @Test(1)
    public boolean testGet(){
        boolean result = true;
        for(int i = 0; i < testSize;i++)
            result = result && (list.get(i) != null);

        return result;
    }

    @Test(1)
    public boolean testSize(){
        return list.size() == testSize;
    }

    @Test(2)
    public boolean testContains(){
        boolean result = true;
        for(int i = 0; i < testSize;i++)
            result = result && list.contains(i);

        List<Integer> addList = new LinkedList<Integer>();
        for(int i = 0; i < testSize;i++)
            if(i%3 == 1)
                addList.add(i);
        result = result && list.containsAll(addList);

        for(int i = testSize; i < 2*testSize;i++)
            if(i%5 == 2)
                addList.add(i);
        list.addAll(addList);
        result = result && list.containsAll(addList);

        return result;
    }

    @Test(3)
    public boolean testSort(){
        boolean result = true;
        Integer prevInt = Integer.MIN_VALUE;
        Integer curInt;

        Collections.sort(list);
        Iterator<Integer> it = list.iterator();
        while(it.hasNext()){
            curInt = it.next();
            if(curInt < prevInt){
                result = false;
                break;
            }
            prevInt = curInt;
        }

        return result;
    }

    @Test(4)
    private boolean removeTest(){
        Iterator<Integer> it = list.iterator();
        while(it.hasNext()){
            it.next();
            it.remove();
        }

        return list.isEmpty();
    }

    @Test(5)
    public boolean testList(int size){
        return true;
    }

    @AfterSuite
    public void clear(){
        list.clear();
    }

    public static void main(String[] args) {
        try {
            VUnit.start(TestList.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
