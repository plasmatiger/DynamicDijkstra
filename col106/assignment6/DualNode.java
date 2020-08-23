package col106.assignment6;

public class DualNode {

    int tox;
    int toy;
    int fromx;
    int fromy;
    int key;
    double weight;


    public DualNode(int tox, int toy, int fromx, int fromy, int key, double weight) {
        this.tox = tox;
        this.toy = toy;
        this.fromx = fromx;
        this.fromy = fromy;
        this.key = key;
        this.weight = weight;
    }

    public boolean isWidth() {
        if(this.tox == this.fromx) return false;
        return true;
    }

    public boolean isInc() {
        if (isWidth()) {
            if (fromx > tox) return true;
        } else {
            if (fromy > toy) return true;
        }

        return false;
    }

}