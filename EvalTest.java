import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;
import java.lang.Math;
import col106.assignment6.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class EvalTest {

    private boolean testDualGraph(String infile, String outfile, int[]s, int[]t, 
    int left, int right, int forward) throws FileNotFoundException {
    
        Scanner sc = new Scanner(new File(outfile));
        int nodes0 = sc.nextInt();
        int edges0 = sc.nextInt();
        ArrayList<int[]> hooks0 = new ArrayList<int[]>();

        while(sc.hasNextInt()){
            int a1 = sc.nextInt();
            int a2 = sc.nextInt();
            int b1 = sc.nextInt();
            int b2 = sc.nextInt();
            int c1 = sc.nextInt();
            int c2 = sc.nextInt();
            int w = sc.nextInt();
            hooks0.add(new int[]{a1, a2, b1, b2, c1, c2, w});
        }
        // Compute dual graph using candidate's solution
        col106.assignment6.Digraph G = new col106.assignment6.Digraph(infile);
        ShortestPathFinder sp = new ShortestPathFinder(G, s, t, left, right, forward);
        int nodes = sp.numDualNodes();
        int edges = sp.numDualEdges();
        ArrayList<int[]> hooks = sp.dualGraph();

        // Check1: compare number of nodes in dual graph
        if (nodes != nodes0){
            System.out.println("Unexpected number of nodes in dual graph");
            return false;
        }

        // Check2: compare number of edges in dual graph
        if (edges != edges0){
            System.out.println("Unexpected number of edges in dual graph");
            return false;
        }

        // Check3: compare hooks
        ArrayList<String> hooks0_str = new ArrayList<String>();
        ArrayList<String> hooks_str = new ArrayList<String>();
        for (int i=0; i<hooks0.size(); i++){
            int[] h0 = hooks0.get(i);
            int[] h1 = hooks.get(i);
            hooks0_str.add(Arrays.toString(h0));
            hooks_str.add(Arrays.toString(h1));
        }

        Collections.sort(hooks0_str);
        Collections.sort(hooks_str);

        if (!hooks_str.equals(hooks0_str)){
            System.out.println("Dual graphs do not match");
            return false;
        }

        return true;
    }

    private boolean testShortestPath(String infile, String outfile, int[]s, int[]t, 
    int left, int right, int forward) throws FileNotFoundException {
    
        Scanner sc = new Scanner(new File(outfile));
        int hPath0 = sc.nextInt();
        boolean hasPath0 = (hPath0 == 1); 
        int pathLength0 = sc.nextInt();
        ArrayList<int[]> path0 = new ArrayList<int[]>();
        while(sc.hasNextInt()){
            int n1 = sc.nextInt();
            int n2 = sc.nextInt();
            path0.add(new int[]{n1, n2});
            // System.out.println("(" + n1 + "," + n2 + ")");
        }

        // Compute shortest path using candidate's solution
        col106.assignment6.Digraph G = new col106.assignment6.Digraph(infile);
        ShortestPathFinder sp = new ShortestPathFinder(G, s, t, left, right, forward);
        boolean hasPath = sp.hasValidPath();
        int pathLength = sp.ShortestPathValue();
        ArrayList<int[]> path = sp.getShortestPath();

        // Check 1 : Does a valid path exist
        if (hasPath != hasPath0){
            System.out.println("No path found");
            return false;
        }

        // Check 2 : Do the path lengths match
        if (pathLength != pathLength0){
            System.out.println("Path lengths do not match");
            return false;
        }

        // Check 3 : Does the path match
        for (int i=0;i<path0.size();i++){
            int[] node0 = path0.get(i);
            int[] node1 = path.get(i);
            if ((node0[0] != node1[0]) || (node0[1] != node1[1])) {
                System.out.println(node0[0] + " " + node0[1]);
                System.out.println(node1[0] + " " + node1[1]);

                System.out.println("Path nodes do not match");
                return false;
            }
        }

        return true;

    }

    // Test 1A (dual graph)
    @Test(timeout=10000)
    public void test1A_10P(){
        System.out.println("Test 1A");
        String infile = "test_cases/input_files/in1.csv";
        String outfile = "test_cases/dual_graph/1.csv";
        int[] s = new int[]{0, 0};
        int[] t = new int[]{1, 1};

        int left = 8;
        int right = 1;
        int forward = 0;

        try{
            assertTrue(testDualGraph(infile, outfile, s, t, left, right, forward));
        }	
        catch(Exception e){
            System.out.println(e);
            fail();
        ;
        }      
    }

    // Test 1B (shortest path)
    @Test(timeout=10000)
    public void test1B_10P(){
        System.out.println("Test 1B");
        String infile = "test_cases/input_files/in1.csv";
        String outfile = "test_cases/output_path/1.csv";
        int[] s = new int[]{0, 0};
        int[] t = new int[]{1, 1};

        int left = 8;
        int right = 1;
        int forward = 0;

        try{
            assertTrue(testShortestPath(infile, outfile, s, t, left, right, forward));
        }	
        catch(Exception e){
            System.out.println(e);
            fail();
        ;
        }      
    }

    // Test 2A (dual graph)
    @Test(timeout=10000)
    public void test2A_10P(){
        System.out.println("Test 2A");
        String infile = "test_cases/input_files/in1.csv";
        String outfile = "test_cases/dual_graph/2.csv";
        int[] s = new int[]{0, 0};
        int[] t = new int[]{1, 2};

        int left = 8;
        int right = 1;
        int forward = 0;

        try{
            assertTrue(testDualGraph(infile, outfile, s, t, left, right, forward));
        }	
        catch(Exception e){
            System.out.println(e);
            fail();
        ;
        }      
    }

    // Test 2B (shortest path)
    @Test(timeout=10000)
    public void test2B_10P(){
        System.out.println("Test 2B");
        String infile = "test_cases/input_files/in1.csv";
        String outfile = "test_cases/output_path/2.csv";
        int[] s = new int[]{0, 0};
        int[] t = new int[]{1, 2};

        int left = 8;
        int right = 1;
        int forward = 0;

        try{
            assertTrue(testShortestPath(infile, outfile, s, t, left, right, forward));
        }	
        catch(Exception e){
            System.out.println(e);
            fail();
        ;
        }      
    }

    // Test 3A (dual graph)
    @Test(timeout=10000)
    public void test3A_10P(){
        System.out.println("Test 3A");
        String infile = "test_cases/input_files/in2.csv";
        String outfile = "test_cases/dual_graph/3.csv";
        int[] s = new int[]{0, 0};
        int[] t = new int[]{2, 2};

        int left = 2;
        int right = 3;
        int forward = 0;

        try{
            assertTrue(testDualGraph(infile, outfile, s, t, left, right, forward));
        }	
        catch(Exception e){
            System.out.println(e);
            fail();
        ;
        }      
    }

    // Test 2B (shortest path)
    @Test(timeout=10000)
    public void test3B_10P(){
        System.out.println("Test 3B");
        String infile = "test_cases/input_files/in2.csv";
        String outfile = "test_cases/output_path/3.csv";
        int[] s = new int[]{0, 0};
        int[] t = new int[]{2, 2};

        int left = 2;
        int right = 3;
        int forward = 0;

        try{
            assertTrue(testShortestPath(infile, outfile, s, t, left, right, forward));
        }	
        catch(Exception e){
            System.out.println(e);
            fail();
        ;
        }      
    }
}


   