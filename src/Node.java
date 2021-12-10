import java.util.ArrayList;

/**
 * Object representing a node used in Quadtree.
 *
 * @author alexrich
 */

public class Node {
    private ArrayList<Tuple> bucket;    // contains all data held in node if node is leaf
    private int bucketSize;             // how much data the node can hold
    /** this can be null to represent that node is leaf */
    private Node[][] children;          // holds four children that have four sub-quadrants of node
    private double minX;                // minimum x value of node's quadrant
    private double maxX;                // maximum x value of node's quadrant
    private double minY;                // minimum y value of node's quadrant
    private double maxY;                // maximum y value of node's quadrant

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
     * Deletes data from leaf.
     * @param tuple     Tuple, data to delete
     * @return          boolean, true if data was found and deleted, false if data wasn't found
     */
    public boolean deleteData(Tuple tuple) {
        return bucket.remove(tuple);
    }

    /**
     * @return  true if bucket is at max capacity, false otherwise
     */
    public boolean isFull() { return bucketSize == bucket.size(); }

    public boolean isEmpty() { return bucket.size() == 0; }

    public ArrayList<Tuple> getBucket() {
        return bucket;
    }

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
        children = new Node[2][2];      // 2d array of size of total size 4 to represent the four sub-quadrants
        for (int i = 0; i < bucket.size(); i++) {
            Tuple data = bucket.get(i);
            int vertical = 0;
            int horizontal = 0;
            if (data.getX() >= (maxX + minX) / 2)
                horizontal = 1;
            if (data.getY() >= (maxY + minY) / 2)
                vertical = 1;

            if (children[vertical][horizontal] == null) {
                children[vertical][horizontal] = createNewChild(vertical, horizontal);
            }
            children[vertical][horizontal].addData(data);
        }
        bucket.clear();
    }

    /**
     * Makes node a leaf again by deleting all children
     */
    public void deleteChildren() {
        children = null;
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
     * @param vertical     int, 1 if lower quadrant, 0 if upper
     * @param horizontal   int, 1 if right quadrant, 0 if left
     * @return      Node
     */
    public Node getChild(int vertical, int horizontal) {
        if (isLeaf()) {
            return null;
        }
        return children[vertical][horizontal];
    }

    /**
     * Sets child of respective quadrant to given child, makes children null if all nodes inside are now null
     * @param child     Node new child
     * @param vertical     int
     * @param horizontal   int
     */
    public void setChild(Node child, int vertical, int horizontal) {
        if (children != null) {
            children[vertical][horizontal] = child;
            boolean allNull = true;
            for (int i = 0; i < children.length; i++) {
                for (int j = 0; j < children[i].length; j++) {
                    if (children[i][j] != null)
                        allNull = false;
                }
            }
            if (allNull)
                deleteChildren();
        }
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
