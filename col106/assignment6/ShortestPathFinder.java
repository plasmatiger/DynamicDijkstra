package col106.assignment6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class ShortestPathFinder implements ShortestPathInterface {
    /**
     * Computes shortest-path from the source vertex s to destination vertex t in
     * graph G. DO NOT MODIFY THE ARGUMENTS TO THIS CONSTRUCTOR
     *
     * @param G       the graph
     * @param s       the source vertex
     * @param t       the destination vertex
     * @param left    the cost of taking a left turn
     * @param right   the cost of taking a right turn
     * @param forward the cost of going forward
     * @throws IllegalArgumentException unless 0 <= s < V
     * @throws IllegalArgumentException unless 0 <= t < V where V is the number of
     *                                  vertices in the graph G.
     */
    private int numDualNodes;
    private int numDualEdges;
    private ArrayList<DualEdge>[] dualAdj;
    private HashMap<Integer, DualNode> dualMap;
    private ArrayList<Integer> endNodes;
    private int distance[];
    private int backtrack[];
    private int shortestEndNode;

    public ShortestPathFinder(final Digraph G, final int[] s, final int[] t, final int left, final int right,
            final int forward) {
        // YOUR CODE GOES HERE
        // we have changed the int[] datatype to int datatype for s and t as per
        // question requirement
        try {
            int height = G.V() / G.W();
            int width = G.W();
            if (s[0] < 0 || s[0] >= height || s[1] < 0 || s[1] >= width) {
                throw new IllegalArgumentException("s is not in the range");
            }
            if (t[0] < 0 || t[0] >= height || t[1] < 0 || t[1] >= width) {
                throw new IllegalArgumentException("t is not in the range");
            }
            int numDualNodes = G.E() + 1;
            int numDualEdges = 0;
            this.numDualNodes = numDualNodes;

            dualAdj = new ArrayList[numDualNodes];
            for (int v = 0; v < numDualNodes; v++) {
                dualAdj[v] = new ArrayList<DualEdge>();
            }

            this.dualMap = new HashMap<Integer, DualNode>();
            dualMap.put(0, new DualNode(s[0], s[1], -1, -1, 0, 0.0));

            int i = 1;
            ArrayList<Integer> startKeys = new ArrayList<Integer>();
            ArrayList<Integer> endKeys = new ArrayList<Integer>();
            for (Edge e : G.edges()) {
                if (!this.dualMap.containsKey(i)) {
                    int to = e.to();
                    int from = e.from();
                    if (G.nodemap(from).i == s[0] && G.nodemap(from).j == s[1])
                        startKeys.add(i);
                    if (G.nodemap(to).i == t[0] && G.nodemap(to).j == t[1])
                        endKeys.add(i);
                    this.dualMap.put(i, new DualNode(G.nodemap(to).i, G.nodemap(to).j, G.nodemap(from).i,
                            G.nodemap(from).j, i, e.weight()));
                    i++;
                }
            }

            for (int start : startKeys) {
                dualAdj[0].add(new DualEdge(0, start, forward + dualMap.get(start).weight));
            }

            for (int from = 1; from < numDualNodes; from++) {
                for (int to = 1; to < numDualNodes; to++) {
                    DualNode f = dualMap.get(from);
                    DualNode g = dualMap.get(to);
                    if (f.tox == g.fromx && f.toy == g.fromy) {
                        if ((f.isWidth() && f.isInc() && g.isWidth() && g.isInc())
                                || (f.isWidth() && !f.isInc() && g.isWidth() && !g.isInc())
                                || (!f.isWidth() && !f.isInc() && !g.isWidth() && !g.isInc())
                                || (!f.isWidth() && f.isInc() && !g.isWidth() && g.isInc())) {
                            dualAdj[from].add(new DualEdge(from, to, forward + dualMap.get(to).weight));
                        }

                        if ((f.isWidth() && f.isInc() && !g.isWidth() && !g.isInc())
                                || (f.isWidth() && !f.isInc() && !g.isWidth() && g.isInc())
                                || (!f.isWidth() && !f.isInc() && g.isWidth() && !g.isInc())
                                || (!f.isWidth() && f.isInc() && g.isWidth() && g.isInc())) {
                            dualAdj[from].add(new DualEdge(from, to, left + dualMap.get(to).weight));
                        }

                        if ((f.isWidth() && f.isInc() && !g.isWidth() && g.isInc())
                                || (f.isWidth() && !f.isInc() && !g.isWidth() && !g.isInc())
                                || (!f.isWidth() && !f.isInc() && g.isWidth() && g.isInc())
                                || (!f.isWidth() && f.isInc() && g.isWidth() && !g.isInc())) {
                            dualAdj[from].add(new DualEdge(from, to, right + dualMap.get(to).weight));
                        }
                        numDualEdges++;
                    }
                }

            }

            PriorityQueue<Integer> queue = new PriorityQueue<>();
            Set<Integer> traversed = new HashSet<>();

            for (int d = 0; d< numDualNodes; d++) {
                distance[d] = Integer.MAX_VALUE;
                backtrack[d] = 0;
            }

            distance[0] = 0;
            queue.add(0);

            while (traversed.size() != numDualNodes) {
                int queueTop = queue.remove();
                traversed.add(queueTop);
                int edgeDistance = Integer.MIN_VALUE;
                int nodeDistance = Integer.MIN_VALUE;
                for (int a = 0; a < dualAdj[queueTop].size(); a++) {
                    DualEdge neigh = dualAdj[queueTop].get(a);
                    if (!traversed.contains(neigh.to())) {
                        edgeDistance = (int)neigh.weight();
                        nodeDistance = distance[queueTop] + edgeDistance;
                        
                        if (nodeDistance < distance[neigh.to()]) {
                            distance[neigh.to()] = nodeDistance;
                            backtrack[neigh.to()] = queueTop;
                        }

                        queue.add(neigh.to());
                    }
                }

            }


            this.numDualNodes = numDualNodes;
            this.numDualEdges = numDualEdges;
            this.dualAdj = dualAdj;
            this.dualMap = dualMap;
            this.endNodes = endKeys;
        } catch (Exception e) {
            System.out.println("Some issue occured in constructing ShortestPathFinder");
        }
    }

    // Return number of nodes in dual graph
    public int numDualNodes() {
        // YOUR CODE GOES HERE
        // System.out.println(G.V()/G.W());
        // System.out.println(G.W());
        // System.out.println((G.W()-1) * (G.V()/G.W()-1) + 1);
        // return 2;
        return this.numDualNodes;
    }

    // Return number of edges in dual graph
    public int numDualEdges() {
        // YOUR CODE GOES HERE

        return this.numDualEdges;
    }

    // Return hooks in dual graph
    // A hook (0,0) - (1,0) - (1,2) with weight 8 should be represented as
    // the integer array {0, 0, 1, 0, 1, 2, 8}
    public ArrayList<int[]> dualGraph() {
        // YOUR CODE GOES HERE
        ArrayList<int[]> dualGraph = new ArrayList<>();
        for (int i = 0; i < numDualNodes; i++) {
            for (DualEdge e : dualAdj[i]) {
                DualNode from = dualMap.get(e.from());
                DualNode to = dualMap.get(e.to());
                double weight = e.weight();
                dualGraph.add(new int[] {from.fromx, from.fromy, from.tox, from.toy, to.tox, to.toy, (int)weight});
            }
        }
        return dualGraph;
    }

    // Return true if there is a path from s to t.
    public boolean hasValidPath() {
        // YOUR CODE GOES HERE
        for (int i : endNodes) {
            if (distance[i] < Integer.MAX_VALUE) return true;
        }
        return false;
    }

    // Return the length of the shortest path from s to t.
    public int ShortestPathValue() {
        // YOUR CODE GOES HERE
        int minVal = Integer.MAX_VALUE;
        if (hasValidPath()) {
            for (int i : endNodes) {
                if (distance[i] < minVal){
                    minVal = distance[i];
                    shortestEndNode = i;
                } 
            }
            return minVal;
        } else {
            return 0;
        }
    }

    // Return the shortest path computed from s to t as an ArrayList of nodes,
    // where each node is represented by its location on the grid.
    public ArrayList<int[]> getShortestPath() {
        // YOUR CODE GOES HERE
        ShortestPathValue();
        ArrayList<int[]> path = new ArrayList<>();
        int curr = shortestEndNode;
        while (curr != 0) {
            DualNode n = dualMap.get(curr);
            int[] pathNode = new int[]{n.tox, n.toy};
            path.add(0, pathNode);
            curr = backtrack[curr];
        }
        return path;
    }
}
