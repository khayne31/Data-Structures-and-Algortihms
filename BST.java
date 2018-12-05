import java.util.ArrayList;
import java.util.Queue;
import java.util.List;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Your implementation of a binary search tree.
 * @author Kellen Haynes
 * @version 1.0p
 */

public class BST<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the BST with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular
     * for loop will not work here. What other type of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        BSTNode currentNode = root;
        for (T item: data) {
            if (root == null) {
                root = new BSTNode<>(item);
                size++;
            } else if (item != null) {
                add(item);
            }
        }
    }

    /**
     * Add the data as a leaf in the BST. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     * 
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The data "
                    + "you have given is null. Please give non-null data");
        }
        boolean placed = false;

        if (root == null) {
            root = new BSTNode<>(data);
            size++;
        }
        BSTNode currentNode = root;
        while (!placed) {
            if (currentNode.getData().compareTo(data) > 0) {
                if (currentNode.getLeft() == null) {
                    currentNode.setLeft(new BSTNode(data));
                    placed = true;
                    size++;
                }
                currentNode = currentNode.getLeft();

            } else if (currentNode.getData().compareTo(data) < 0) {
                if (currentNode.getRight() == null) {
                    currentNode.setRight(new BSTNode(data));
                    size++;
                    placed = true;
                }
                currentNode = currentNode.getRight();

            } else {
                placed = true;
            }
        }
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data.
     * You must use recursion to find and remove the successor (you will likely
     * need an additional helper method to handle this case efficiently).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */


    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The data "
                    + "you have given is null Please give non-null data.");
        }

        T returndata =  recursiveSearch(root, null, false, data);
        if (returndata == null) {
            throw new java.util.NoSuchElementException("There was no "
                    + "data in the tree that match your query");
        }
        size--;
        //System.out.println(preorder());
        return returndata;
    }

    /**
     * recursivly searchs through the tree removes the data
     * accordining to  sucessor removal algorithm
     * @param currentNode the currentNode being inspected.
     * @param referenceNode the node which is to be
     *                      changed with the sucessor removal
     * @param successor a boolead value which determines
     *                 whether or not the node to be removed has been found.
     *                 it indicates when the search for the sucessor beings
     * @param  data the data to search for
     * @return the data removed from the tree.
     */
    private T recursiveSearch(BSTNode currentNode,
                              BSTNode referenceNode,
                              boolean successor, T data) {
        if (currentNode == null) {
            return null;
        } else if (currentNode.getData() == null) {
            return null;
        }
        if (currentNode.getRight() != null) {
            if (currentNode.getRight().getData() == null) {
                currentNode.setRight(null);
            }
        }
        if (currentNode.getLeft() != null) {
            if (currentNode.getLeft().getData() == null) {
                currentNode.setLeft(null);
            }
        }

        if (currentNode.getData().equals(data)
                && currentNode.getRight() == null
                && currentNode.getLeft() == null) {
            T returndata = (T) currentNode.getData();
            currentNode.setData(null);
            return returndata;
        } else if (currentNode.getData().equals(data)
                && (currentNode.getRight() == null
                || currentNode.getLeft() == null)) {
            T returndata = (T) currentNode.getData();
            if (currentNode.getLeft() == null) {
                BSTNode temp = currentNode.getRight();
                currentNode.setData(temp.getData());
                currentNode.setRight(temp.getRight());
                currentNode.setLeft(temp.getLeft());
            } else {
                BSTNode temp = currentNode.getLeft();
                currentNode.setData(temp.getData());
                currentNode.setRight(temp.getRight());
                currentNode.setLeft(temp.getLeft());
            }
            return returndata;
        } else if (currentNode.getData().equals(data) && !successor) {
            T returndata = (T) currentNode.getData();
            recursiveSearch(currentNode.getRight(),
                    currentNode, true, (T) currentNode.getData());
            return returndata;
        } else if (currentNode.getLeft() == null
                && currentNode.getRight() == null && successor) {
            referenceNode.setData(currentNode.getData());
            currentNode.setData(null);
            return null;
        } else if (successor && currentNode.getLeft() == null) {
            referenceNode.setRight(currentNode.getRight());
            referenceNode.setData(currentNode.getData());
            currentNode.setData(null);
            return null;

        } else {
            if (!successor) {
                if (currentNode.getData().compareTo(data) < 0) {
                    return recursiveSearch(currentNode.getRight(),
                            null, false, data);
                } else {
                    return recursiveSearch(currentNode.getLeft(),
                            null, false, data);
                }
            } else {
                return recursiveSearch(currentNode.getLeft(),
                        referenceNode, true, data);
            }
        }
    }
    /**
     *recursivly goes through the tree and add the
     * data to the proper location in the BST
    * @param currentNode the currentNode being inspected.
    * @param  data the data to search for
    * @return the data added
    * */
    private T recursiveFind(BSTNode currentNode, T data) {
        if (currentNode == null) {
            return null;
        } else if (currentNode.getData().equals(data)) {
            return (T) currentNode.getData();
        }

        if (currentNode.getData().compareTo(data) < 0) {
            return recursiveFind(currentNode.getRight(), data);
        } else  {
            return recursiveFind(currentNode.getLeft(), data);
        }

    }
    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
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
            throw new IllegalArgumentException("The data "
                    + "which you gave was null. Please give non-null data");
        }

        T returndata = recursiveFind(root, data);
        if (returndata == null) {
            throw new java.util.NoSuchElementException("There "
                    + "was no data in the tree which matched your query");
        }

        return returndata;
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        return recursiveFind(root, data) != null;
    }


    /**
     * adds the transverals of the BST to the list depending
     * on which transversal was selected
     * @param currentNode the current node being inspected
     * @param orderType the type of transversal. 0 for preorder
     *                  1 for inorder and 2 for postorder
     * @param returnList the list which the data of the
     *                   nodes will be added to
     * @return a list containg the preorder of the tree.
     */
    private List<T> transversal(BSTNode currentNode,
                                int orderType, List<T> returnList) {
        if (currentNode == null) {
            return null;
        }

        if (orderType == 0) {
            returnList.add((T) currentNode.getData());
        }
        transversal(currentNode.getLeft(), orderType, returnList);
        if (orderType == 1) {
            returnList.add((T) currentNode.getData());
        }
        transversal(currentNode.getRight(), orderType, returnList);
        if (orderType == 2) {
            returnList.add((T) currentNode.getData());
        }
        return returnList;
    }
    /**
     * Should run in O(n).
     *
     * @return a preorder traversal of the tree
     */
    public List<T> preorder() {
        return transversal(root, 0, new ArrayList<T>());
    }

    /**
     * Should run in O(n).
     *
     * @return an inorder traversal of the tree
     */
    public List<T> inorder() {
        return transversal(root, 1, new ArrayList<T>());
    }

    /**
     * Should run in O(n).
     *
     * @return a postorder traversal of the tree
     */
    public List<T> postorder() {
        return transversal(root, 2, new ArrayList<T>());
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * To do this, add the root node to a queue. Then, while the queue isn't
     * empty, remove one node, add its data to the list being returned, and add
     * its left and right child nodes to the queue. If what you just removed is
     * {@code null}, ignore it and continue with the rest of the nodes.
     *
     * Should run in O(n).
     *
     * @return a level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode> queue = new LinkedList<>();
        List<T> levelOrder = new ArrayList<>();
        queue.add(root);
        while (queue.peek() != null) {
            levelOrder.add((T) queue.peek().getData());
            BSTNode currentNode = queue.remove();
            if (currentNode.getLeft() != null) {
                queue.add(currentNode.getLeft());
            }
            if (currentNode.getRight() != null) {
                queue.add(currentNode.getRight());
            }
        }
        return levelOrder;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in the efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     * in the BST
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     */
    public List<T> kLargest(int k) {
        if (k > size) {
            throw new java.lang.IllegalArgumentException("k "
                    + "cannot be this size."
                    + " Please allow k to be less than"
                    + " or equal the size of the tree");
        }
        List<T> inorderTransversal = inorder();
        LinkedList<T> returnList = new LinkedList<>();
        for (int i = 1; i <= k; i++) {
            returnList.addFirst(inorderTransversal.get(inorderTransversal.size()
                    - i));
        }
        return  returnList;
    }

    /**
     * Clears the tree.
     *
     * Should run in O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Calculate and return the height of the root of the tree. A node's
     * height is defined as {@code max(left.height, right.height) + 1}. A leaf
     * node has a height of 0 and a null child should be -1.
     *
     * Should be calculated in O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return recursiveHeight(root);
    }

    /**
     * returns the max of two ints
     * @param a int 1 to be compared
     * @param  b int 2 to be compared
     * @return returns the greatest int
     * */
    private int max(int a, int b) {
        if (a > b) {
            return  a;
        }
        return b;
    }

    /**
     * @param currentNode the currentNode being inspected.
     * @return returns the height of the tree
     * */
    private int recursiveHeight(BSTNode currentNode) {
        if (currentNode == null) {
            return -1;
        }
        return max(recursiveHeight(currentNode.getLeft()),
                recursiveHeight(currentNode.getRight())) + 1;
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
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
