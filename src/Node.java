import java.util.ArrayList;
import java.util.Arrays;

/**
 * Object representing the nodes used in Quadtree.
 *
 * @author alexrich
 */

public class Node {
    private ArrayList<Tuple> bucket;
    private int bucketSize;
    private Node[][] children;
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    public Node (int bucketSize, double minX, double maxX, double minY, double maxY) {
        bucket = new ArrayList<>();
        this.bucketSize = bucketSize;
        children = null;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    /**
     * Stores data in the node.
     * @param tuple     Object that holds relevant data
     */
    public void addData(Tuple tuple) {
        if (bucket.size() < bucketSize) {
            bucket.add(tuple);
        }
    }

    /**
     * @return  true if bucket is at max capacity, false otherwise
     */
    public boolean isFull() { return bucketSize == bucket.size(); }

    /**
     * Returns true if node is leaf, false otherwise. Node is a leaf if it has no children
     * @return      true if children array not yet initialized, false otherwise
     */
    public boolean isLeaf() {
        return children == null;
    }

    /**
     * Creates four new children and moves all data in node to children
     */
    public void addChildren() {
        children = new Node[2][2];      // 2d array of size of 4 to represent the four sub-quadrants
        for (int i = 0; i < bucket.size(); i++) {
            Tuple data = bucket.get(i);
            int depth = 0;
            int breadth = 0;
            if (data.getX() >= (maxX + minX) / 2)
                breadth = 1;
            if (data.getY() >= (maxY + minY) / 2)
                depth = 1;

            if (children[depth][breadth] == null) {
                children[depth][breadth] = createNewChild(depth, breadth);
            }
            children[depth][breadth].addData(data);
        }
        bucket.clear();
    }

    /**
     * Initializes a child that is currently null.
     * @param depth     If 0 child is upper quadrant, lower otherwise
     * @param breadth   If 0 child is left quadrant, right otherwise
     * @return      Node with correct min and max values based off passed quadrants
     */
    public Node createNewChild(int depth, int breadth) {
        double minX, maxX, minY, maxY;
        if (breadth == 0) {
            minX = this.minX;
            maxX = (this.maxX + this.minX) / 2;
        } else {
            minX = (this.maxX + this.minX) / 2;
            maxX = this.maxX;
        }
        if (depth == 0) {
            minY = this.minY;
            maxY = (this.maxY + this.minY) / 2;
        } else {
            minY = (this.maxY + this.minY) / 2;
            maxY = this.maxY;
        }
        return new Node(bucketSize, minX, maxX, minY, maxY);
    }

    public Node[][] getChildren() {
        return children;
    }

    /**
     * Returns child of respective quadrant
     * @param depth     int, 1 if lower quadrant, 0 if upper
     * @param breadth   int, 1 if right quadrant, 0 if left
     * @return      Node
     */
    public Node getChild(int depth, int breadth) {
        if (isLeaf()) {
            return null;
        }
        return children[depth][breadth];
    }

    /**
     * Sets child of respective quadrant to given child
     * @param child     Node new child
     * @param depth     int
     * @param breadth   int
     */
    public void setChild(Node child, int depth, int breadth) {
        if (children != null)
            children[depth][breadth] = child;
    }

    public double getMinX() {
        return minX;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxY() {
        return maxY;
    }

    @Override
    public String toString() {
        return "Node{" +
                "bucket=" + bucket +
                ", minX=" + minX +
                ", maxX=" + maxX +
                ", minY=" + minY +
                ", maxY=" + maxY +
                '}';
    }
}
