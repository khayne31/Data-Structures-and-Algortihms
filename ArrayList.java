/**
 * Your implementation of an ArrayList.
 *
 * @author Kellen Haynes
 * @version 1.0p
 */
public class ArrayList<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * The initial capacity of the array list.
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * Constructs a new ArrayList.
     *
     * You may add statements to this method.
     */
    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds the element to the index specified.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Adding to index {@code size} should be amortized O(1),
     * all other adds are O(n).
     *
     * @param index The index where you want the new element.
     * @param data The data to add to the list.
     * @throws java.lang.IndexOutOfBoundsException if index is negative
     * or index > size
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new java.lang.IndexOutOfBoundsException("Cannot put data "
                    + "at this index");
        }
        if (data == null) {
            throw new java.lang.IllegalArgumentException("The data given is "
                     + "null. Please give actual data.");
        }


        if (size == backingArray.length) {
            T[] newArray = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < size; i++) {
                newArray[i] = backingArray[i];
            }
            backingArray = newArray;

        }

        if (index == size) {
            backingArray[index] = data;
        } else if (index != size) {
            int hasObjectBeenAdded = 0;
            T previousVariable = null;
            for (int i = 0; i < size + 1; i++) {
                if (i != index) {
                    if (hasObjectBeenAdded == 1) {
                        T temp = backingArray[i];
                        backingArray[i] = previousVariable;
                        previousVariable = temp;
                    } else {
                        backingArray[i] = backingArray[i];
                    }
                } else if (i == index) {
                    previousVariable = backingArray[i];
                    backingArray[i] = data;
                    hasObjectBeenAdded = 1;
                }
            }
        }
        size++;

    }

    /**
     * Add the given data to the front of your array list
     *
     * Remember that this add may require elements to be shifted.
     * 
     * Must be O(n).
     *
     * @param data The data to add to the list.
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    /**
     * Add the given data to the back of your array list.
     *
     * Must be amortized O(1).
     *
     * @param data The data to add to the list.
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the element at index.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * This method should be O(1) for index {@code size - 1} and O(n) in 
     * all other cases.
     *
     * @param index The index of the element
     * @return The object that was formerly at that index.
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException("Cannot remove data "
                    + "at this index. Please give a valid index.");
        }

        if (index == size - 1) {
            T removedVariable = backingArray[index];
            backingArray[index] = null;
            size--;
            return removedVariable;
        } else {
            int hasObjectBeenRemoved = 0;
            T removedVariable = null;
            for (int i = 0; i < size + 1; i++) {
                if (i != index) {
                    if (hasObjectBeenRemoved == 1) {
                        backingArray[i] = backingArray[i + 1];
                    } else {
                        backingArray[i] = backingArray[i];
                    }
                } else if (i == index) {
                    removedVariable = backingArray[i];
                    backingArray[i] = backingArray[i + 1];
                    hasObjectBeenRemoved = 1;
                }
            }
            size--;
            return  removedVariable;
        }




    }

    /**
     * Remove the first element in the list and return it.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return The data from the front of the list or null if the list is empty
     */
    public T removeFromFront() {
        if (size != 0) {
            return removeAtIndex(0);
        }

        return null;
    }

    /**
     * Remove the last element in the list and return it.
     * 
     * Must be O(1).
     *
     * @return The data from the back of the list or null if the list is empty
     */
    public T removeFromBack() {
        if (size != 0) {
            return removeAtIndex(size - 1);
        }

        return null;

    }

    /**
     * Returns the element at the given index.
     *
     * Must be O(1).
     *
     * @param index The index of the element
     * @return The data stored at that index.
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Cannot give data"
                   +  "at this index");
        }
        return backingArray[index];
    }

    /**
     * Return a boolean value representing whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clear the list. Reset the backing array to a new array of the initial
     * capacity.
     *
     * Must be O(1).
     */
    public void clear() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Return the size of the list as an integer.
     *
     * For grading purposes only. DO NOT USE THIS METHOD IN YOUR CODE!
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Return the backing array for this list.
     *
     * For grading purposes only. DO NOT USE THIS METHOD IN YOUR CODE!
     *
     * @return the backing array for this list
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }
}
