import ru.geekbrains.vklimovich.util.SimpleGraph;

public class JavaAlg7 {
    /*
    Задание 7.1
    Приведите пример графа.
    Задание 7.2
    Реализуйте базовые методы графа.
    Задание 7.3
    В программный код из задания 7.2 добавьте реализацию метода обхода в глубину.
    Выполните оценку времени с помощью System.nanoTime().
    Задание 7.4
    В базовом графе из задания 7.2 реализуйте метод обхода в ширину.
    Выполните оценку времени с помощью System.nanoTime().
     */
    public static void main(String[] args) {
        SimpleGraph<Character> graph = new SimpleGraph<>();
        long startTime, duration;
        graph.addVertex('A');
        graph.addVertex('B');
        graph.addVertex('C');
        graph.addVertex('D');
        graph.addVertex('E');
        graph.addVertex('F');
        graph.addVertex('G');
        graph.addVertex('H');
        graph.addVertex('I');
        graph.addVertex('J');
        graph.addVertex('K');
        graph.addVertex('L');
        graph.addEdge('A', 'B');
        graph.addEdge('B', 'C');
        graph.addEdge('C', 'D');
        graph.addEdge('A', 'E');
        graph.addEdge('A', 'F');
        graph.addEdge('F', 'G');
        graph.addEdge('G', 'H');
        graph.addEdge('H', 'I');
        graph.addEdge('A', 'J');
        graph.addEdge('J', 'K');
        graph.addEdge('K', 'L');

        startTime = System.nanoTime();
        graph.dfs();
        duration = System.nanoTime() - startTime;
        System.out.printf("DFS Time = %d\n", duration);
        startTime = System.nanoTime();
        graph.bfs();
        duration = System.nanoTime() - startTime;
        System.out.printf("BFS Time = %d\n", duration);
    }
}
