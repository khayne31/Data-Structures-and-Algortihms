
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Kellen Haynes
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement cocktail sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * You may assume that the array doesn't contain any null elements.
     * <p>
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("invalid parameters given to "
                    + "the method.Check to make sure no null values are given");
        }
        int lastIndexSwapdown = 0;
        int lastIndexSwapup = arr.length - 1;
        boolean swap = true;
        A:
        while (swap) {
            swap = false;
            int temp1 = lastIndexSwapdown;
            int temp2 = lastIndexSwapup;

            for (int i = lastIndexSwapdown; i < lastIndexSwapup; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    temp1 = i;
                    swap = true;
                }

            }

            for (int i = temp1; i > lastIndexSwapdown; i--) {
                if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                    T temp = arr[i];
                    arr[i] = arr[i - 1];
                    arr[i - 1] = temp;
                    temp2 = i;
                    swap = true;
                }
            }
            /*if(!swap){
                break A;
            }*/
            lastIndexSwapup = temp1;
            lastIndexSwapdown = temp2;

        }

    }

    /**
     * Implement insertion sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * You may assume that the array doesn't contain any null elements.
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("invalid parameters given to "
                    + "the method.Check to make sure no null values are given");
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                    T temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                } else {
                    break;
                }
            }
        }
    }

    /**
     * Implement selection sort.
     * <p>
     * It should be:
     * in-place
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n^2)
     * <p>
     * <p>
     * You may assume that the array doesn't contain any null elements.
     * <p>
     * Note that there may be duplicates in the array.
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("invalid parameters given to "
                    + "the method.Check to make sure no null values are given");
        }
        T max = arr[0];
        int index = 0;
        for (int i = arr.length - 1; i > 1; i--) {
            for (int j = 0; j < i; j++) {
                if (comparator.compare(arr[j], max) > 0) {
                    max = arr[j];
                    index = j;
                }
            }
            T temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;
        }
    }

    /**
     * Implement merge sort.
     * <p>
     * It should be:
     * stable
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * You may assume that the array doesn't contain any null elements.
     * <p>
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     * <p>
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        T[] temp = mergesort(arr, comparator);

    }
    /**
     * recursive mergesort helper function
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @return returns the array that has been sorted
     */
    private static <T> T[] mergesort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("invalid parameters given to "
                    + "the method.Check to make sure no null values are given");
        }
        if (arr.length == 1) {
            return arr;
        }
        T[] left = (T[]) new Object[arr.length / 2];
        T[] right = (T[]) new Object[arr.length - arr.length / 2];
        T[] merge = (T[]) new Object[arr.length];
        for (int i = 0; i < left.length; i++) {
            left[i] = arr[i];
        }
        for (int i = 0; i < right.length; i++) {
            right[i] = arr[i + arr.length / 2];
        }

        left = mergesort(left, comparator);
        right = mergesort(right, comparator);
        int j = 0; //right pointer
        int m = 0; //left pointer
        for (int i = 0; i < arr.length; i++) {
            if (right[right.length - 1] == null) {
                for (int k = m; k < left.length; k++) {
                    arr[i] = left[k];
                    i++;
                }
                break;
            } else if (left[left.length - 1] == null) {
                for (int k = j; k < right.length; k++) {
                    arr[i] = right[k];
                    i++;
                }
                break;
            } else {
                int result = comparator.compare(left[m], right[j]);
                if (result < 0 || result == 0) {
                    arr[i] = left[m];
                    left[m] = null;
                    m++;

                } else if (result > 0) {
                    arr[i] = right[j];
                    right[j] = null;
                    j++;

                }

            }

        }
        return arr;
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     * <p>
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     * <p>
     * It should be:
     * stable
     * <p>
     * Have a worst case running time of:
     * O(kn)
     * <p>
     * And a best case running time of:
     * O(kn)
     * <p>
     * Refer to the PDF for more information on LSD Radix Sort.
     * <p>
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     * <p>
     * Do NOT use anything from the Math class except Math.abs
     *
     * @param arr the array to be sorted
     * @throws IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("invalid parameters given to "
                    + "the method.Check to make sure no null values are given");
        }
        boolean cont = true;
        Queue[] counter = new Queue[19];
        for (int p = 0; p < counter.length; p++) {
            counter[p] = new LinkedList();
        }
        int dev = 1;
        int mod = 10;
        while (cont) {
            cont = false;
            for (int i = 0; i < arr.length; i++) {
                int beforeMod = arr[i] / dev;
                if (beforeMod != 0) {
                    cont = true;
                }
                int bucket;
                if (beforeMod == Integer.MIN_VALUE) {
                    beforeMod = -Integer.MAX_VALUE;
                }
                if (beforeMod < 0) {
                    bucket = -((-beforeMod) % mod) + 9;
                } else {
                    bucket = beforeMod % mod + 9;
                }

                counter[bucket].add(arr[i]);
            }

            int index = 0;
            for (int i = 0; i < counter.length; i++) {
                while (!counter[i].isEmpty()) {
                    arr[index] = (int) counter[i].remove();
                    index++;
                }
            }
            dev *= 10;
        }


    }

    /**
     * Implement kth select.
     * <p>
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     * <p>
     * int pivotIndex = r.nextInt(b - a) + a;
     * <p>
     * It should be:
     * in-place
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * You may assume that the array doesn't contain any null elements.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to 0-indexing) if
     *                   the array was sorted; the 'k' in "kth select"; e.g. if k ==
     *                   1, return the smallest element in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws IllegalArgumentException if the array or comparator or rand is
     *                                  null or k is not in the range of 1 to arr.length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
        if (rand == null || arr == null || comparator == null
                || k - 1 > arr.length - 1 || k - 1 < 0) {
            throw new IllegalArgumentException("invalid parameters given to "
                    + "the method.Check to make sure no null values are given");
        }
        return quicksortmodified(k, arr, comparator, rand, 0, arr.length - 1);
    }


    /**
     *
     * quicksort algorithm
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to 0-indexing) if
     *                   the array was sorted; the 'k' in "kth select"; e.g. if k ==
     *                   1, return the smallest element in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @param start      index to start at
     * @param end        index to end at
     * @return the kth smallest element

     */
    private static <T> T quicksortmodified(int k, T[] arr,
                                           Comparator<T> comparator,
                                           Random rand,
                                           int start, int end) {
        if (start >= end) {
            return arr[k - 1];
        }
        int pivot = rand.nextInt(end - start) + start;

        T temp = arr[start];
        arr[start] = arr[pivot];
        arr[pivot] = temp;
        int i = start + 1;
        int j = end;

        A:
        while (i <= j) {
            if (i > arr.length - 1 || j < 0) {
                break;
            }
            while (comparator.compare(arr[i], arr[start]) <= 0 && i <= j) {
                i++;
                if (i > arr.length - 1) {
                    break A;
                }
            }
            while (comparator.compare(arr[j], arr[start]) >= 0 && i <= j) {
                j--;
            }
            if (i <= j) {
                temp = arr[j];
                arr[j] = arr[i];
                arr[i] = temp;
                i++;
                j--;
            }

        }
        temp = arr[start];
        arr[start] = arr[j];
        arr[j] = temp;
        //T[] newarr;
        if (j > k - 1 || j == 0) {
            return quicksortmodified(k, arr, comparator, rand, j + 1, end);
        } else if (j < k - 1) {
            return quicksortmodified(k, arr, comparator, rand, start, j - 1);
        } else {
            return arr[j];
        }

    }

}
