import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Your implementation of an AVL Tree.
 * @author Kellen Haynes
 * @version 1.0
 */

public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular
     * for loop will not work here. What other type of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("the data given is null");
        }
        Iterator<T> i = data.iterator();
        while (i.hasNext()) {
            T item = i.next();
            if (item == null) {
                throw new IllegalArgumentException("data in the"
                        + "given collection is null");
            }
            add(i.next());
        }
    }

    /**
     * Add the data to the AVL. Start by adding it as a leaf and rotate the tree
     * as needed. Should traverse the tree to find the appropriate location.
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("the "
                    + "given data is null");
        }
        boolean[] returnlist = new boolean[1];
        returnlist[0] = false;
        root  = add(data, root, returnlist);
        if (!returnlist[0]) {
            size++;
        }

    }

    /***
     * adds the node using pointer reinforcement and recursion
     * @param data the data to add in the tree.
     * @param node the current node being examined
     * @param list list which holds the value of a
     *             boolean which is enabled when a duplicate is found
     * @return a node useing pointer reinforcement
     */
    private AVLNode<T> add(T data, AVLNode node, boolean[] list) {
        if (node == null) {
            return new AVLNode<>(data);
        } else if (node.getData().compareTo(data) > 0) {
            node.setLeft(add(data, node.getLeft(), list));
        } else if (node.getData().compareTo(data) < 0) {
            node.setRight(add(data, node.getRight(), list));
        } else if (node.getData().equals(data)) {
            list[0] = true;
        }
        update(node);
        return rotation(node);
        //return node;
    }

    /***
     * updates the height and balancefactor of the current node
     * @param node the current node being examined
     */
    private void update(AVLNode node) {
        node.setBalanceFactor(helper(node.getLeft()) - helper(node.getRight()));
        int h1 = helper(node.getLeft());
        int h2 = helper(node.getRight());
        if (h1 < h2) {
            node.setHeight(h2 + 1);
        } else {
            node.setHeight(h1 + 1);
        }
    }

    /***
     * helper for the update fucntion
     * @param node the current node being examined
     * @return returns the height of a node or -1 if the node is null
     */
    private int helper(AVLNode node) {
        if (node == null) {
            return -1;
        }
        return node.getHeight();
    }

    /***
     * does a left rotaion
     * @param node the current node being examined
     * @return a node b which has been rotated to the left
     */
    private AVLNode  rotationLeft(AVLNode node) {
        AVLNode b = node.getRight();
        node.setRight(b.getLeft());
        b.setLeft(node);
        update(node);
        update(b);
        return b;
    }

    /***
     * performs a right rotation
     * @param node the current node being examined
     * @return a node b which has been rotated to the right
     */
    private AVLNode rotateRight(AVLNode node) {
        AVLNode b = node.getLeft();
        node.setLeft(b.getRight());
        b.setRight(node);
        update(node);
        update(b);
        return b;
    }

    /***
     * determines whether or not to perform a right rotation,
     * left roation, double rotation, or no rotation at all
     * @param node the current node being examined
     * @return returns the same thing both rotation methodsw return if the
     * magnitude of the balance factor is greater than one otherwise returns
     * the same node as was passed in
     */
    private AVLNode rotation(AVLNode node) {
        int bf = node.getBalanceFactor();
        if (bf > 1 || bf < -1) {
            if (bf > 0) {
                if (node.getLeft().getBalanceFactor() <= 0) {
                    node.setLeft(rotationLeft(node.getLeft()));
                    return rotateRight(node);
                } else {
                    return rotateRight(node);
                }
            } else {
                if (node.getRight().getBalanceFactor() >= 0) {
                    node.setRight(rotateRight(node.getRight()));
                    return rotationLeft(node);
                } else {
                    return rotationLeft(node);
                }
            }
        }
        return node;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the predecessor to replace the data,
     * not the successor.
     * You must use recursion to find and remove the predecessor (you will
     * likely need an additional helper method to handle this case efficiently).
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the given data is null. "
                   +  "please give non null data");
        }
        ArrayList<T> returnlist =  new ArrayList<>();
        root = remove(data, root, false, returnlist);
        size--;
        return returnlist.get(0);

    }

    // use a list to return the return data
    /***
     * recursivly searchs for the data in the
     * list and returns the data if found.
     * @param data the data to removed from the tree.
     * @param node the current node being examined
     * @param list list that holds the value of the item removed from the list
     * @param predecessor a boolean value which indicates
     *                    the removed value has been found and the search
     *                    for the predecessor should begin
     * @return a node useng pointer reinforcement
     */
    private AVLNode remove(T data, AVLNode node,
                           boolean predecessor, ArrayList<T> list) {
        if (node == null && !predecessor) {
            throw new java.util.NoSuchElementException("the data "
                   + "is not in this AVL");
        } else if (node.getHeight() == 0 && predecessor) {
            return node;
        } else if (!predecessor && node.getData().equals(data)) {
            list.add((T) node.getData());
            if (node.getHeight() == 0) {
                return null;
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else if (node.getLeft() == null) {
                return node.getRight();
            }
            AVLNode temp = remove(data, node.getLeft(), true, list);
            node.setData(temp.getData());
            if (node.getHeight() == 1) {
                node.setLeft(null);
            }
            update(node);
            return  rotation(node);
            //return node;
        } else if (predecessor) {
            AVLNode returnNode = remove(data,
                    node.getRight(), predecessor, list);
            if (node.getHeight() == 1) {
                node.setRight(null);
            }
            update(node);
            return rotation(returnNode);
        } else if (node.getData().compareTo(data) > 0) {
            node.setLeft(remove(data, node.getLeft(), false, list));
        } else if (node.getData().compareTo(data) < 0) {
            node.setRight(remove(data, node.getRight(), false, list));
        }
        update(node);
        return rotation(node);
        //return node;
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("please give non null data");
        }
        T returndata = get(data, root);
        if (returndata == null) {
            throw new java.util.NoSuchElementException(""
                    + "the element does not exist in the AVL");
        }
        return returndata;
    }

    /***
     * recursivly searches for the data in the list
     * @param data the data to searched for in the tree.
     * @param node the current node being examined
     * @return returns the item if its in the list
     * and returns null of its not in the list
     */
    private T get(T data, AVLNode node) {
        if (node == null) {
            return null;
        } else if (node.getData().compareTo(data) > 0) {
            return get(data, node.getLeft());
        } else if (node.getData().compareTo(data) < 0) {
            return  get(data, node.getRight());
        } else {
            return (T) node.getData();
        }
        //return (T) data;
    }
    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new  IllegalArgumentException("the data given is null,"
                    + " please give non null data");
        }
        return get(data, root) != null;
    }

    /**
     * Returns the data in the deepest node. If there are more than one node
     * with the same deepest depth, return the right most node with the
     * deepest depth.
     *
     * Must run in O(log n) for all cases
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        return maxDeepestNode(root);
    }

    /***
     * recusivly searches the list for the biggest deepest node
     * @param node the current node being examined
     * @return returns the data of the deepest node
     */
    private T maxDeepestNode(AVLNode node) {
        if (node == null) {
            return null;
        }
        if (node.getHeight() == 0) {
            return (T) node.getData();
        }
        if (node.getRight() != null && node.getLeft() == null) {
            return maxDeepestNode(node.getRight());
        } else if (node.getLeft() != null && node.getRight() == null) {
            return maxDeepestNode(node.getLeft());
        } else {
            if (node.getLeft().getHeight() > node.getRight().getHeight()) {
                return maxDeepestNode(node.getLeft());
            } else {
                return maxDeepestNode(node.getRight());
            }
        }
    }

    /**
     * Returns the data of the deepest common ancestor between two nodes with
     * the given data. The deepest common ancestor is the lowest node (i.e.
     * deepest) node that has both data1 and data2 as descendants.
     * If the data are the same, the deepest common ancestor is simply the node
     * that contains the data. You may not assume data1 < data2.
     * (think carefully: should you use value equality or reference equality?).
     *
     * Must run in O(log n) for all cases
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * deepestCommonAncestor(3, 1): 2
     *
     * Example
     * Tree:
     *           3
     *        /    \
     *       1      4
     *      / \
     *     0   2
     * deepestCommonAncestor(0, 2): 1
     *
     * @param data1 the first data
     * @param data2 the second data
     * @throws java.lang.IllegalArgumentException if one or more of the data
     *          are null
     * @throws java.util.NoSuchElementException if one or more of the data are
     *          not in the tree
     * @return the data of the deepest common ancestor
     */
    public T deepestCommonAncestor(T data1, T data2) {
        if (data2.compareTo(data1) < 0) {
            T temp = data1;
            data1 = data2;
            data2 = temp;
        }
        if (contains(data1) && contains(data2)) {
            return deepestCommonAncestor(data1, data2, root);
        }
        throw new java.util.NoSuchElementException(""
                + "the item doesnt exist in the AVL");
    }

    /***\
     * @param data1 the smallest data value to be searched
     * @param data2 the largest data value to be searched
     * @param node the current node being examined
     * @return returns the common ancestore between two nodes
     */
    private T deepestCommonAncestor(T data1, T data2, AVLNode node) {
        if (node == null) {
            return null;
        }
        if (node.getData().compareTo(data1) > 0
                && node.getData().compareTo(data2) < 0) {
            return (T) node.getData();
        } else if (node.getData().compareTo(data2) > 0) {
            return deepestCommonAncestor(data1, data2, node.getLeft());
        } else if (node.getData().compareTo(data1) < 0) {
            return deepestCommonAncestor(data1, data2, node.getRight());
        } else if (node.getData().equals(data1)) {
            return (T) node.getData();
        } else if (node.getData().equals(data2)) {
            return (T) node.getData();
        }
        return (T) node.getData();
    }
    /**
     * Clear the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Return the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the number of elements in the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     * DO NOT USE IT IN YOUR CODE
     * DO NOT CHANGE THIS METHOD
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
