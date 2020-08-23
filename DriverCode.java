import col106.assignment6.*;

import java.util.ArrayList;

public class DriverCode {
    public static void main(String[] args) {
        String file = "test_cases/input_files/in1.csv";
        Digraph G = new Digraph(file);
        int[] s = new int[]{0, 0};
        int[] t = new int[]{1, 2};
        int forward = 0;
        int left = 8;
        int right = 1;

        // Construct dual graph
        ShortestPathFinder sp = new ShortestPathFinder(G, s, t, left, right, forward);
        System.out.println(sp);
        int nodes = sp.numDualNodes();
        int edges = sp.numDualEdges();
        System.out.println("Number of nodes in dual graph = " + nodes);
        System.out.println("Number of edges in dual graph = " + edges);
        ArrayList<int[]> hooks = sp.dualGraph();
        System.out.println("Hooks in orignal graph corresponding to edges in dual graph:");
        for (int[] hook: hooks){
            System.out.println("(" + hook[0] + "," + hook[1] + ") " 
                            + "(" + hook[2] + "," + hook[3] + ") " 
                            + "(" + hook[4] + "," + hook[5] + ") " 
                            + hook[6]);
        }
       
        // Compute shortest path
        boolean hasPath = sp.hasValidPath();
        int pathLength = sp.ShortestPathValue();
        ArrayList<int[]> path = sp.getShortestPath();
        System.out.println("Shortest path length = " + pathLength);

        // Print the path obtained
        if (!hasPath) {
            System.out.println("No valid path found.");
        }
        else {
            for (int[] node : path) {
                System.out.println("(" + node[0] + "," + node[1] + ")");
            }
            System.out.println();
        }
    }
}
