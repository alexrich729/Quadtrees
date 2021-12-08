import java.util.ArrayList;

/**
 * Object representing the nodes used in Quadtree.
 *
 * @author alexrich
 */

public class Node {
    private ArrayList<Tuple> bucket;
    private int bucketSize;
    private Node[] children;

    public Node (int bucketSize) {
        bucket = new ArrayList<>();
        this.bucketSize = bucketSize;
        children = null;
    }

    /**
     * Stores data in the node.
     * @param tuple     Object that holds relevant data
     */
    public void addData(Tuple tuple) {
        if (bucketSize < bucket.size()) {
            bucket.add(tuple);
        }
    }

    /**
     * Returns true if node is leaf, false otherwise. Node is a leaf if it has no children
     * @return      true if children array not yet initialized, false otherwise
     */
    public boolean isLeaf() {
        return children == null;
    }
}
