import java.util.ArrayList;

/**
 * Implmentation of a Quadtree. Find more information about this data structure that helps with spacial indexing here:
 * https://medium.com/@waleoyediran/spatial-indexing-with-quadtrees-b998ae49336
 *
 * Data searched for, inserted, or deleted must have location within original quadrant's size
 *
 * @author alexrich
 */

public class Quadtree {
    private final int BUCKET_SIZE;   // amount of data that can be held in a leaf
    private final int WIDTH;         // width of starting quadrant
    private final int HEIGHT;        // height of final quadrant
    private Node root;               // root of tree

    public Quadtree(int bucketSize, int width, int height) {
        BUCKET_SIZE = bucketSize;
        WIDTH = width;
        HEIGHT = height;
        root = null;
    }

    /**
     * Inserts data into tree
     * @param name      String, Name of data
     * @param x         double, x position of data
     * @param y         double, y position of data
     */
    public void insert(String name, double x, double y) {
        insert(new Tuple(name, x, y));
    }

    /**
     * Inserts data to tree
     * @param data  Tuple to insert
     */
    public void insert(Tuple data) {
        if (root == null) {
            root = new Node(BUCKET_SIZE, 0, WIDTH, 0, HEIGHT);
            root.addData(data);
        } else {
            recursiveInsert(root, data);
        }
    }

    /**
     * Recursively calls children of given node until finds one (or creates a new child) that isn't full where it inserts
     * data
     * @param node      Node, node to consider inserting data into or calling a child
     * @param data      Tuple, data to insert
     */
    private void recursiveInsert(Node node, Tuple data) {
        if (!node.isLeaf() || node.isFull()) {
            int depth = 0;
            int breadth = 0;
            if (data.getX() >= (node.getMaxX() + node.getMinX()) / 2)
                breadth = 1;
            if (data.getY() >= (node.getMaxY() + node.getMinY()) / 2)
                depth = 1;

            if (node.isLeaf()) {
                node.addChildren();
            }
            Node child = node.getChild(depth, breadth);
            if (child == null) {
                child = node.createNewChild(depth, breadth);
                node.setChild(child, depth, breadth);
            }
            recursiveInsert(child, data);
        } else {
            node.addData(data);
        }
    }

    /**
     * Finds data with parameters given in quadtree and deletes it
     * @param name      String, name of data
     * @param x         double, x location of data
     * @param y         double, y location of data
     * @return          true if data found and deleted, false otherwise
     */
    public boolean delete(String name, double x, double y) {
        return delete(new Tuple(name, x, y));
    }

    /**
     * Finds given data in quadtree and deletes it
     * @param data      Tuple with data to be matched and deleted
     * @return          true if data found and deleted, false otherwise
     */
    public boolean delete(Tuple data) {
        if (root == null)
            return false;
        else if (root.isLeaf())
            return root.deleteData(data);
        else
            return recursiveDelete(root, data);
    }

    /**
     * Recursive function that keeps calls with children of node that represents quadrant where data would lie unless it
     * finds a leaf. If data is in leaf it deletes data. Also deletes any empty branch that occurs because of this deletion.
     *
     * @param node      Node to check child for data
     * @param data      Tuple with data looking to delete
     * @return          true if data is found and deleted, false otherwise
     */
    private boolean recursiveDelete(Node node, Tuple data) {
        int vertical = 0;   // 0 if data is upper quadrant of children, 1 if in lower
        int horizontal = 0; // 0 if data is left quadrant of children, 1 if in right
        if (data.getX() >= (node.getMaxX() + node.getMinX()) / 2)
            horizontal = 1;
        if (data.getY() >= (node.getMaxY() + node.getMinY()) / 2)
            vertical = 1;
        Node child = node.getChild(vertical, horizontal);
        if (child == null)
            return false;
        else if (!child.isLeaf()) {
            boolean toReturn = recursiveDelete(child, data);
            // deletes node if there is no data beneath it
            if (toReturn) {
                Node[][] children = node.getChildren();
                boolean allNull = true;
                for (int i = 0; i < children.length; i++) {
                    for (int j = 0; j < children[i].length; j++) {
                        if (children[i][j] != null)
                            allNull = false;
                    }
                }
                if (allNull)
                    node = null;
            }
            return toReturn;
        } else {
            boolean toReturn = child.deleteData(data);
            if (toReturn) {
                if (child.isEmpty())
                    node.setChild(null, vertical, horizontal);
                // deletes node if it no longer has any children beneath it
                if (node.getChildren() == null)
                    node = null;
            }
            return toReturn;
        }
    }

    /**
     * Finds data name with location in quadtree.
     * @param x     double of x loc
     * @param y     double of y loc
     * @return      String - name of data at loc or empty string if no data found
     */
    public String search(double x, double y) {
        return recursiveSearch(root, x, y);
    }

    /**
     * Calls child with the correct quadrant unless given node is null or a leaf. If node is leaf, looks for data with given
     * location and returns its string and empty string if not found.
     * @param node      Node to look for data
     * @param x         double x of loc
     * @param y
     * @return
     */
    private String recursiveSearch(Node node, double x, double y) {
        if (node == null)
            return "";
        if (node.isLeaf()) {
            ArrayList<Tuple> data = node.getBucket();
            for (int i = 0; i < data.size(); i++) {
                if (Double.compare(x, data.get(i).getX()) == 0 && Double.compare(y,data.get(i).getY()) == 0)
                    return data.get(i).getName();
            }
            return "";
        }
        int vertical = 0;   // 0 if data is upper quadrant of children, 1 if in lower
        int horizontal = 0; // 0 if data is left quadrant of children, 1 if in right
        if (x >= (node.getMaxX() + node.getMinX()) / 2)
            horizontal = 1;
        if (y >= (node.getMaxY() + node.getMinY()) / 2)
            vertical = 1;
        return recursiveSearch(node.getChild(vertical, horizontal), x, y);
    }
}
