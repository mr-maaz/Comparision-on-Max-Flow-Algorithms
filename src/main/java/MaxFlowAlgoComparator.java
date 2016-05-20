import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class MaxFlowAlgoComparator {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
        MaxFlowFordFulkerson fordFulkerson = new MaxFlowFordFulkerson();
        MaxFlowDinic dinic = new MaxFlowDinic();
        MaxFlowPushRelabel pushRelabel = new MaxFlowPushRelabel();

//        Scanner commandLineArgs = new Scanner(System.in);
//        System.out.println("Enter the number of vertices");
//        int vertices = commandLineArgs.nextInt();
//        int vertices = 200;
        Random random = new Random();
        for (int vertices = 100; vertices < 200; vertices+=100) {
        List<MaxFlowDinic.Edge>[] graph = dinic.createGraph(vertices);
        pushRelabel.init(vertices);
        int[][] randomWeightedAdjMatrix = new int[vertices][vertices];
        for (int i = 0; i < vertices; i++){
            for (int j = i + 1; j < vertices; j++){
                randomWeightedAdjMatrix[i][j] = random.nextInt(50 + 1);
                if (randomWeightedAdjMatrix[i][j] != 0) {
                    pushRelabel.addEdge(i, j, randomWeightedAdjMatrix[i][j]);
                    dinic.addEdge(graph, i, j, randomWeightedAdjMatrix[i][j]);
                }
            }
        }
            long start = System.nanoTime();
            System.out.println(fordFulkerson.maxFlow(randomWeightedAdjMatrix, 0, vertices - 1));
            long stop = System.nanoTime();
            long fordTime = (stop - start) / 1000000;

            start = System.nanoTime();
            System.out.println(dinic.maxFlow(graph, 0, vertices - 1));
            stop = System.nanoTime();
            long dinicTime = (stop - start) / 1000000;

            start = System.nanoTime();
            System.out.println(pushRelabel.maxFlow(0, vertices - 1));
            stop = System.nanoTime();
            long pushTime = (stop - start) / 1000000;
            writer.println(vertices + "," + fordTime + "," + dinicTime + "," + pushTime);
            System.out.println(vertices + "," + fordTime + "," + dinicTime + "," + pushTime);
        }
        writer.close();
    }
}
