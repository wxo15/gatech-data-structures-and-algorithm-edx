import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

     /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * This is the constructor that constructs a new MinHeap.
     *
     * Recall that Java does not allow for regular generic array creation,
     * so instead we cast a Comparable[] to a T[] to get the generic typing.
     */
    public MinHeap() {
        //DO NOT MODIFY THIS METHOD!
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * Method should run in amortized O(log n) time.
     *
     * @param data The data to add.
     * @throws java.lang.IllegalArgumentException If the data is null.
     */
    public void add(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (data == null) {
            throw new IllegalArgumentException();
        }
        if (size == backingArray.length - 1) {
            //resize at length - 1 b/c array
            // will never be full (indx 0 always empty)
            resize();
        }
        if (size == 0) {
            backingArray[1] = data;
            size++;
        } else {
            backingArray[size + 1] = data;
            //heapify up
            int parentIndex = (size + 1) / 2;
            int itemIndex = size + 1;
            heapifyUp(data, parentIndex, itemIndex);
            size++;
        }
    }
    
    private void heapifyUp(T item, int indexP, int indexI) {
        if (indexP == 0) {
            //base case: reached top of tree
            return;
        }
        if (backingArray[indexP].compareTo(item) < 0) {
            //base case: parent less than item
            return;
        } else if (backingArray[indexP].compareTo(item) > 0) {
            //parent greater than item
            //swap and check again
            swap(item, indexP, indexI);
            heapifyUp(item, indexP / 2, indexI / 2);
        }
    }
    
    private void resize() {
        T[] newArr = (T[]) (new Comparable[backingArray.length * 2]);
        for (int i = 0; i < backingArray.length; i++) {
            newArr[i] = backingArray[i];
        }
        backingArray = newArr;
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * Method should run in O(log n) time.
     *
     * @return The data that was removed.
     * @throws java.util.NoSuchElementException If the heap is empty.
     */
    public T remove() {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (size == 0) {
            throw new NoSuchElementException();
        }
        T removed = backingArray[1];
        backingArray[1] = backingArray[size];
        heapifyDown(backingArray[1], 1, 2, 3);
        backingArray[size] = null;
        size--;
        return removed;
    }
    
    private void heapifyDown(T item, int indexI, int indexL, int indexR) {
        if (indexI == size || indexL >= backingArray.length
                || indexR >= backingArray.length) {
            //base case: end of heap
            //need this case when left/right index out of bounds of array
            return;
        }
        if (backingArray[indexL] == null && backingArray[indexR] == null) {
            //base case: reached bottom level
            return;
        }
        //edge case: element w/ only one child
        if (backingArray[indexL] == null) {
            //only need to check right element
            if (backingArray[indexI].compareTo(backingArray[indexR]) > 0) {
                //swap, then return
                swap(item, indexR, indexI);
            }
            return;
        } else if (backingArray[indexR] == null) {
            //only need to check left element
            if (backingArray[indexI].compareTo(backingArray[indexL]) > 0) {
                swap(item, indexL, indexI);
            }
            return;
        }
        if (backingArray[indexI].compareTo(backingArray[indexL]) < 0
                && backingArray[indexI].compareTo(backingArray[indexR]) < 0) {
            //base case: order satisfied
            return;
        } else {
            //swapping necessary - swap w/ smaller child
            if (backingArray[indexR].compareTo(backingArray[indexL]) > 0) {
                swap(item, indexL, indexI);
                indexI = indexL;
            } else {
                swap(item, indexR, indexI);
                indexI = indexR;
            }
            heapifyDown(item, indexI, indexI * 2, indexI * 2 + 1);
        }
    }
    
    private void swap(T item, int indexA, int indexB) {
        T temp = backingArray[indexA];
        backingArray[indexA] = item;
        backingArray[indexB] = temp;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
