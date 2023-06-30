import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The new data should become a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Should be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data The data to add to the tree.
     * @throws java.lang.IllegalArgumentException If data is null.
     */
    public void add(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (data == null) {
            throw new IllegalArgumentException();
        }

        root = add(root, data);
    }

    private BSTNode<T> add(BSTNode<T> current, T data) {
        if (current == null) {
            size++;
            return new BSTNode<T>(data);
        }

        if (data.compareTo(current.getData()) < 0) {
            current.setLeft(add(current.getLeft(), data));
        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(add(current.getRight(), data));
        }
        return current;
    }
    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the SUCCESSOR to
     * replace the data. You should use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do NOT return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data The data to remove.
     * @return The data that was removed.
     * @throws java.lang.IllegalArgumentException If data is null.
     * @throws java.util.NoSuchElementException   If the data is not in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data.");
        }
        //create dummy node reference to return data that was removed
        BSTNode<T> removeRef = new BSTNode(null);
        root = removeHelper(data, root, removeRef);
        size--;
        return removeRef.getData();
    }

    /**
     * recursively traverses the BST to find the node containing the data
     * @param data data to remove
     * @param node node to check for data
     * @param removeRef reference to dummy node w/ removed data
     * @return node containing data
     */
    private BSTNode<T> removeHelper(T data, BSTNode<T> node,
                                    BSTNode<T> removeRef) {
        if (node == null) {
            throw new NoSuchElementException(data + " was not "
                    + " found in the BST.");
        }
        //recurse until you reach the node
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(removeHelper(data, node.getLeft(), removeRef));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(removeHelper(data, node.getRight(), removeRef));
        } else {
            //if data is the same as node's data, delete this node
            //case 1 and 2: no child or one child
            if (removeRef.getData() == null) {
                //ensures dummy node's data is only changed once
                removeRef.setData(node.getData());
            }
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }
            //case 3: two children
            // get successor, swap data, delete old successor node
            node.setData(successor(node.getRight()));
            node.setRight(removeHelper(node.getData(), node.getRight(),
                    removeRef));
        }
        return node;
    }

    /**
     * returns the data from a node's succcessor
     * @param node starting point from which to find the successor
     * @return data from the successor node
     */
    private T successor(BSTNode<T> node) {
        T data = node.getData();
        while (node.getLeft() != null) {
            data = node.getLeft().getData();
            node = node.getLeft();
        }
        return data;
    }
    
    

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
