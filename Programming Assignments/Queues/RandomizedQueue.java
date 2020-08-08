/*******************************************************************************
 * Course:          Algorithms, Part I by Princeton University
 * Instructor:      Bob Sedgewick, Kevin Wayne
 *
 * Assignment:      Week 2, Queues
 *
 * Author:          Rajat Dipta Biswas
 * Last modified:   8/8/2020
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        size = 0;
    }

    // resize the Item[] array to a new capacity
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int x = 0; x < size; x++) {
            copy[x] = items[x];
        }
        items = copy;
    }

    // swap indices i and j of array
    private void swap(Item[] array, int i, int j) {
        Item temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (size == items.length) {
            resize(2 * items.length);
        }

        items[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniform(size);
        Item itemChosen = items[index];
        swap(items, index, size - 1);
        size--;
        items[size] = null;

        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }

        return itemChosen;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniform(size);
        Item itemChosen = items[index];

        return itemChosen;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    // iterator to traverse items in random order
    private class RandomIterator implements Iterator<Item> {

        private int index;
        private final Item[] itemsCopy;

        public RandomIterator() {
            index = 0;
            itemsCopy = (Item[]) new Object[size];

            for (int x = 0; x < size; x++) {
                itemsCopy[x] = items[x];
            }

            for (int x = 0; x < size; x++) {
                int i = StdRandom.uniform(x + 1);
                swap(itemsCopy, x, i);
            }
        }

        public boolean hasNext() {
            return index < size;
        }

        public Item next() {
            if (index == size) {
                throw new NoSuchElementException();
            }

            Item current = itemsCopy[index];
            index++;

            return current;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    /*
     * // print information about the Item[] array
     * private void arrayStats() {
     *
     *     StdOut.println();
     *
     *     for (int x = 0; x < size; x++) {
     *         StdOut.print(items[x] + " ");
     *     }
     *     StdOut.println();
     *
     *     StdOut.println("capacity = " + items.length);
     *     StdOut.println("size = " + size);
     *
     * }
     */

    // unit testing (required)
    public static void main(String[] args) {

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        randomizedQueue.enqueue("X");
        StdOut.println("dequeued = " + randomizedQueue.dequeue());

        String[] strings = {
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P"
        };

        for (int x = 0; x < 10; x++) {
            randomizedQueue.enqueue(strings[x]);
            // randomizedQueue.arrayStats();
        }

        StdOut.println();
        StdOut.println("printing using iterator");
        for (String i : randomizedQueue) {
            StdOut.print(i + " ");
        }
        StdOut.println();

        for (int x = 0; x < 8; x++) {
            StdOut.println();
            StdOut.println("dequeued = " + randomizedQueue.dequeue());
            // randomizedQueue.arrayStats();
        }

    }

}
