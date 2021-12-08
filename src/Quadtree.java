/**
 * Implmentation of a Quadtree. Find more information about this data structure that helps with spacial indexing here:
 * https://medium.com/@waleoyediran/spatial-indexing-with-quadtrees-b998ae49336
 *
 * @author alexrich
 */

public class Quadtree {
    private final int BUCKET_SIZE;   // amount of data that can be held in a leaf
    private Node root;               // root of tree

    public Quadtree(int bucketSize) {
        BUCKET_SIZE = bucketSize;
        root = null;
    }

    public void insert(String name, int x, int y) {
        recursiveInsert(root, new Tuple(name, x, y));
    }

    public void insert(Tuple data) {
        if (root == null) {
            root = new Node(BUCKET_SIZE);
            root.addData(data);
        }
        recursiveInsert(root, data);
    }

    private void recursiveInsert(Node root, Tuple data) {
        if (root == null) {

        }
    }
}
