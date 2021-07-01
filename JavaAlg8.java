import ru.geekbrains.vklimovich.util.VHashtable;

import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

public class JavaAlg8 {
    private final static int TEST_SIZE = 32;
    private final static int INT_BOUND = 1000;
    private final static int STR_LEN = 4;

    private static String randomString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < STR_LEN;i++)
            sb.append((char)('A' + ThreadLocalRandom.current().nextInt(22)));
        return sb.toString();
    }

    private static <T> T[] testData(int size, Supplier<T> gen, IntFunction<T[]> cons){
        return Stream.generate(gen).distinct().limit(size).toArray(cons);
    }

    public static void main(String[] args) {
        Integer[] keyArray = testData(TEST_SIZE, () -> ThreadLocalRandom.current().nextInt(INT_BOUND), Integer[]::new);
        String[] valueArray = testData(TEST_SIZE, JavaAlg8::randomString, String[]::new);

        VHashtable<Integer, String> hashTable = new VHashtable<>(TEST_SIZE*2);
        for(int i = 0; i < keyArray.length;i++)
            hashTable.put(keyArray[i], valueArray[i]);
        System.out.println("Hash Table (linear):");
        System.out.println(hashTable);
        System.out.println();

        hashTable = new VHashtable<>(TEST_SIZE*2, key -> 7 - key%7);
        for(int i = 0; i < keyArray.length;i++)
            hashTable.put(keyArray[i], valueArray[i]);
        System.out.println("Hash Table (double hash):");
        System.out.println(hashTable);
    }
}
