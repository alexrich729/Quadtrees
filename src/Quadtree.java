/**
 * Implmentation of a Quadtree. Find more information about this data structure that helps with spacial indexing here:
 * https://medium.com/@waleoyediran/spatial-indexing-with-quadtrees-b998ae49336
 *
 * @author alexrich
 */

public class Quadtree {
    private final int BUCKET_SIZE;   // amount of data that can be held in a leaf
    private final int WIDTH;         // width of starting quadrant
    private final int HEIGHT;        // height of final quadrant
    private Node root;               // root of tree
    private int count;

    public Quadtree(int bucketSize, int width, int height) {
        BUCKET_SIZE = bucketSize;
        WIDTH = width;
        HEIGHT = height;
        root = null;
    }

    /* For testing purposes only */
    public static void main(String[] args) {
        Double eX = Math.random() * 1000;
        Double fX = Math.random() * 1000;
        Double gX = Math.random() * 1000;
        Double eY = Math.random() * 1000;
        Double fY = Math.random() * 1000;
        Double gY = Math.random() * 1000;
        Quadtree quadtree = new Quadtree(2, 1000, 1000);
        quadtree.insert("A", 69, 420);
        quadtree.insert(new Tuple("B", 333, 69));
        quadtree.insert(new Tuple("C", 1, 2));
        quadtree.insert("D", Math.random() * 1000, Math.random() * 1000);
        quadtree.insert(new Tuple("E", eX, eY));
        quadtree.insert(new Tuple("F", fX, fY));
        quadtree.insert("G", gX, gY);
        quadtree.insert(new Tuple("H", Math.random() * 1000, Math.random() * 1000));
        quadtree.insert(new Tuple("I", Math.random() * 1000, Math.random() * 1000));
        quadtree.insert("J", Math.random() * 1000, Math.random() * 1000);
        quadtree.insert(new Tuple("K", Math.random() * 1000, Math.random() * 1000));
        quadtree.insert(new Tuple("L", Math.random() * 1000, Math.random() * 1000));
        quadtree.insert("M", Math.random() * 1000, Math.random() * 1000);
        quadtree.insert(new Tuple("N", Math.random() * 1000, Math.random() * 1000));
        quadtree.insert(new Tuple("O", Math.random() * 1000, Math.random() * 1000));
        quadtree.insert("P", Math.random() * 1000, Math.random() * 1000);
        quadtree.insert(new Tuple("Q", Math.random() * 1000, Math.random() * 1000));
        quadtree.insert(new Tuple("R", Math.random() * 1000, Math.random() * 1000));

        /*
        quadtree.delete("A", 69, 420);
        quadtree.delete(new Tuple("B", 333, 69));
        quadtree.delete(new Tuple("C", 1, 2));

         */


        quadtree.insert("S", Math.random() * 1000, Math.random() * 1000);
        quadtree.insert(new Tuple("T", Math.random() * 1000, Math.random() * 1000));
        quadtree.insert(new Tuple("U", Math.random() * 1000, Math.random() * 1000));
        quadtree.insert("V", Math.random() * 1000, Math.random() * 1000);
        quadtree.insert(new Tuple("W", Math.random() * 1000, Math.random() * 1000));
        quadtree.insert(new Tuple("X", Math.random() * 1000, Math.random() * 1000));
        quadtree.insert("Y", Math.random() * 1000, Math.random() * 1000);
        quadtree.insert(new Tuple("Z", Math.random() * 1000, Math.random() * 1000));

        quadtree.delete("E", eX, eY);
        quadtree.delete(new Tuple("F", fX, fY));
        quadtree.delete(new Tuple("G", gX, gY));

        quadtree.printTree(quadtree.root);
        System.out.println(quadtree.count);
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


    /* for testing purposes only */
    public void printTree(Node node) {
        Node[][] children = node.getChildren();
        if (node.isLeaf()) {
            System.out.println(node);
            count++;
        }
        if (children != null) {
            for (int i = 0; i <= 1; i++) {
                for (int j = 0; j <= 1; j++) {
                    if (children[i][j] != null) {
                        printTree(children[i][j]);
                    }
                }
            }
        }
    }
}
