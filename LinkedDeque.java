/**
 * Your implementation of a linked deque.
 *
 * @author Kellen Haynes
 * @version 1.0
 */
public class LinkedDeque<T> {
    // Do not add new instance variables and do not add a new constructor.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    /**
     * Adds the data to the front of the deque.
     *
     * This method must run in O(1) time.
     *
     * @param data the data to add to the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Please give"
                    + "+ data which is not null");
        }
        if (head == null) {
            head = new LinkedNode<T>(data);
            tail = head;
        } else {
            head = new LinkedNode<T>(null, data, head);
            head.getNext().setPrevious(head);
        }
        size++;
    }

    /**
     * Adds the data to the back of the deque.
     *
     * This method must run in O(1) time.
     *
     * @param data the data to add to the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Please give"
                    + " data which is not null");
        }
        if (tail == null) {
            head = new LinkedNode<T>(data);
            tail = head;
        }  else {
            tail = new LinkedNode<T>(tail, data, null);
            tail.getPrevious().setNext(tail);
        }
        size++;
    }

    /**
     * Removes the data at the front of the deque.
     *
     * This method must run in O(1) time.
     *
     * @return the data formerly at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (size ==  0) {
            throw new java.util.NoSuchElementException("The Linked "
                    + "deque is empty");
        }
        T returnData = head.getData();
        if (size == 1) {
            head = null;
        } else {
            head = head.getNext();
            head.setPrevious(null);
        }
        size--;
        if (size <= 1) {
            tail = head;
        }

        return returnData;
    }

    /**
     * Removes the data at the back of the deque.
     *
     * This method must run in O(1) time.
     *
     * @return the data formerly at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("the Linked "
                    + "Deque is empty");
        }
        T returnData = tail.getData();
        if (size == 1) {
            tail = null;
        } else {
            tail = tail.getPrevious();
            tail.setNext(null);
        }

        size--;
        if (size <= 1) {
            head = tail;
        }

        return   returnData;
    }

    /**
     * Returns the number of elements in the deque.
     *
     * Runs in O(1) for all cases.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    /**
     * Returns the head node of the linked deque.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the head of the linked deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the linked deque.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the tail of the linked deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}
