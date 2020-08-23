package col106.assignment6;

public class DualEdge {
    private final int u;
    private final int v;
    private final double weight;

    public DualEdge(int u, int v, double weight) {
        if (u < 0) throw new IllegalArgumentException("Vertex names must be nonnegative integers");
        if (v < 0) throw new IllegalArgumentException("Vertex names must be nonnegative integers");
        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    public int from() {
        return u;
    }

    /**
     * Returns the head vertex of the directed edge.
     * @return the head vertex of the directed edge
     */
    public int to() {
        return v;
    }

    /**
     * Returns the weight of the directed edge.
     * @return the weight of the directed edge
     */
    public double weight() {
        return weight;
    }
}