import java.util.ArrayList;
/**
 * Your implementation of a min heap.
 *
 * @author Kellen Haynes
 * @version 1.0
 */
public class MinHeap<T extends Comparable<? super T>> {

    public static final int INITIAL_CAPACITY = 13;

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables. Do not change the declaration
    // of the instance variables above.

    /**
     * Creates a Heap with an initial capacity of {@code INITIAL_CAPACITY}
     * for the backing array.
     *
     * Use the constant field provided. Do not use magic numbers!
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the Build Heap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     *
     * The data in the backingArray should be in the same order as it appears
     * in the ArrayList before you start the Build Heap Algorithm.
     *
     * The {@code backingArray} should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY from
     * the interface). Index 0 should remain empty, indices 1 to n should
     * contain the data in proper order, and the rest of the indices should
     * be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("the array given is null");
        }
        backingArray = (T[]) new Comparable[data.size() * 2 + 1];
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("data in "
                        + "the array at index: " + i + "is null");
            }
            backingArray[i + 1] = data.get(i);
            size++;
        }

        int currentIndex = size / 2;
        int placeHolder = currentIndex;
        int child1 = currentIndex * 2;
        int child2 = currentIndex * 2 + 1;

        while (placeHolder > 0) {
            while (backingArray[child1] != null
                    && backingArray[child2] != null) {
                if (backingArray[currentIndex]
                        .compareTo(backingArray[child1]) > 0
                       && backingArray[currentIndex].compareTo(
                               backingArray[child2]) > 0) {
                    if (backingArray[child1]
                            .compareTo(backingArray[child2]) < 0) {
                        T temp = backingArray[currentIndex];
                        backingArray[currentIndex] = backingArray[child1];
                        backingArray[child1] = temp;
                        currentIndex = child1;
                    } else {
                        T temp = backingArray[currentIndex];
                        backingArray[currentIndex] = backingArray[child2];
                        backingArray[child2] = temp;
                        currentIndex = child2;
                    }
                } else if (backingArray[currentIndex]
                        .compareTo(backingArray[child1]) > 0) {
                    T temp = backingArray[currentIndex];
                    backingArray[currentIndex] = backingArray[child1];
                    backingArray[child1] = temp;

                    currentIndex = child1;
                } else if (backingArray[currentIndex]
                        .compareTo(backingArray[child2]) > 0) {
                    T temp = backingArray[currentIndex];
                    backingArray[currentIndex] = backingArray[child2];
                    backingArray[child2] = temp;

                    currentIndex = child2;
                } else {
                    break;
                }
                child1 = currentIndex * 2;
                child2 = currentIndex * 2 + 1;
                if (child1 > backingArray.length) {
                    child1 = 0;
                }

                if (child2 > backingArray.length) {
                    child2 = 0;
                }
            }
            placeHolder--;
            currentIndex = placeHolder;
            child1 = currentIndex * 2;
            child2 = currentIndex * 2 + 1;
        }

    }

    /**
     * Adds an item to the heap. If the backing array is full and you're trying
     * to add a new item, then double its capacity.
     *
     * @throws IllegalArgumentException if the item is null
     * @param item the item to be added to the heap
     */
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("the data given is null."
                    + " please give actual data");
        }
        if (size == 0) {
            backingArray[1] = item;
        } else {
            if (size + 1 == backingArray.length) {
                T[] newArray = (T[]) new Comparable[backingArray.length * 2];
                for (int i = 0; i < backingArray.length; i++) {
                    newArray[i] = backingArray[i];
                }
                newArray[size + 1] = item;
                backingArray = newArray;
            } else {
                backingArray[size + 1] = item;
            }
            int currentIndex = (size + 1) / 2;
            int previousIndex = size + 1;
            while (previousIndex > 1) {
                if (backingArray[previousIndex]
                        .compareTo(backingArray[currentIndex]) < 0) {
                    T temp = backingArray[previousIndex];
                    backingArray[previousIndex] = backingArray[currentIndex];
                    backingArray[currentIndex] = temp;
                    previousIndex = currentIndex;
                    currentIndex /= 2;
                } else {
                    break;
                }
            }
        }
        size++;
    }

    /**
     * Removes and returns the min item of the heap. Null out all elements not
     * existing in the heap after this operation. Do not decrease the capacity
     * of the backing array.
     *
     * @throws java.util.NoSuchElementException if the heap is empty
     * @return the removed item
     */
    public T remove() {
        if (backingArray[1] == null) {
            throw new java.util.NoSuchElementException("the heap is empty");
        }

        T returnData = backingArray[1];
        backingArray[1] = backingArray[size];

        int currentIndex = 1;
        int child1 = currentIndex * 2;
        int child2 = currentIndex * 2 + 1;

        while (backingArray[child1] != null
                && backingArray[child2] != null) {
            if (backingArray[currentIndex]
                    .compareTo(backingArray[child1]) > 0
                   && backingArray[currentIndex]
                    .compareTo(backingArray[child2]) > 0) {
                if (backingArray[child1].compareTo(backingArray[child2]) < 0) {
                    T temp = backingArray[currentIndex];
                    backingArray[currentIndex] = backingArray[child1];
                    backingArray[child1] = temp;
                    currentIndex = child1;
                } else {
                    T temp = backingArray[currentIndex];
                    backingArray[currentIndex] = backingArray[child2];
                    backingArray[child2] = temp;
                    currentIndex = child2;
                }
            } else if (backingArray[currentIndex]
                    .compareTo(backingArray[child1]) > 0) {
                T temp = backingArray[currentIndex];
                backingArray[currentIndex] = backingArray[child1];
                backingArray[child1] = temp;

                currentIndex = child1;

            } else if (backingArray[currentIndex]
                    .compareTo(backingArray[child2]) > 0) {
                T temp = backingArray[currentIndex];
                backingArray[currentIndex] = backingArray[child2];
                backingArray[child2] = temp;

                currentIndex = child2;
            } else {
                break;
            }
            child1 = currentIndex * 2;
            child2 = currentIndex * 2 + 1;
            if (child1 >= backingArray.length) {
                child1 = 0;
            }

            if (child2 >= backingArray.length) {
                child2 = 0;
            }
        }
        backingArray[size] = null;
        size--;
        return returnData;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element, null if the heap is empty
     */
    public T getMin() {
        return backingArray[1];
    }

    /**
     * Returns if the heap is empty or not.
     *
     * @return true if the heap is empty, false otherwise
     */
    public boolean isEmpty() {
        return backingArray[1] == null;
    }

    /**
     * Clears the heap and returns array to {@code INITIAL_CAPACITY}.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the number of elements in the heap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Return the backing array for the heap.
     *
     * For grading purposes only. DO NOT USE THIS METHOD IN YOUR CODE!
     *
     * @return the backing array for the heap
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

}
