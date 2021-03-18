import ru.geekbrains.vklimovich.printmanager.*;
import java.util.*;
import java.util.concurrent.*;


public class Lesson3_4 {
    private static final int TIMEOUT = 2;

    public static void main(String[] args) {
        PrintManager[] managers = {new SimplePrintManager(), new QueuePrintManager(), new LockPrintManager()};
        Map<String, Integer> strings = new HashMap<String, Integer>();
        strings.put("ABC", 5);
        strings.put("PRST", 7);

        for(int i = 0; i < managers.length;i++){
            System.out.println("Print manager: " + managers[i]);
            for(Map.Entry<String, Integer> entry: strings.entrySet()){
                String str = entry.getKey();
                int iterations = entry.getValue();
                System.out.println("Prints " + str + " " + iterations + " times:");
                managers[i].print(str, iterations);
                System.out.println();

                System.out.println(Thread.currentThread().getName() + " prints:");
                for(int k = 0;k < 10;k++)
                    System.out.print((char)('F' + k));
                System.out.println();
            }
            try {
                TimeUnit.SECONDS.sleep(TIMEOUT);
                managers[i].close();
                System.out.println();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
