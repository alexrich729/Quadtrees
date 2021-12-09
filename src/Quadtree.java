
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

    public Quadtree(int bucketSize, int width, int height) {
        BUCKET_SIZE = bucketSize;
        WIDTH = width;
        HEIGHT = height;
        root = null;
    }

    public static void main(String[] args) {
        Quadtree quadtree = new Quadtree(2, 1000, 1000);
        quadtree.insert("A", 69, 420);
        quadtree.insert(new Tuple("B", 333, 69));
        quadtree.insert(new Tuple("C", 1, 2));
        quadtree.insert("D", Math.random() * 1000, Math.random() * 1000);
        quadtree.insert(new Tuple("E", Math.random() * 1000, Math.random() * 1000));
        quadtree.insert(new Tuple("F", Math.random() * 1000, Math.random() * 1000));
        quadtree.insert("G", Math.random() * 1000, Math.random() * 1000);
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
        quadtree.insert("S", Math.random() * 1000, Math.random() * 1000);
        quadtree.insert(new Tuple("T", Math.random() * 1000, Math.random() * 1000));
        quadtree.insert(new Tuple("U", Math.random() * 1000, Math.random() * 1000));
        quadtree.insert("V", Math.random() * 1000, Math.random() * 1000);
        quadtree.insert(new Tuple("W", Math.random() * 1000, Math.random() * 1000));
        quadtree.insert(new Tuple("X", Math.random() * 1000, Math.random() * 1000));
        quadtree.insert("Y", Math.random() * 1000, Math.random() * 1000);
        quadtree.insert(new Tuple("Z", Math.random() * 1000, Math.random() * 1000));



        quadtree.printTree(quadtree.root);
    }

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

    public void printTree(Node node) {
        if (node.isLeaf())
            System.out.println(node);
        Node[][] children = node.getChildren();
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
