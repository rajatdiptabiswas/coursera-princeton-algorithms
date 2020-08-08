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

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {

        private final Item item;
        private Node prev;
        private Node next;

        public Node(Item item) {
            this.item = item;
            this.prev = null;
            this.next = null;
        }

    }

    private Node first;
    private Node last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null && last == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) {
            Node newNode = new Node(item);
            first = newNode;
            last = newNode;
        }
        else {
            Node newNode = new Node(item);
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        }

        size++;

    }

    // add the item to the back
    public void addLast(Item item) {

        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) {
            Node newNode = new Node(item);
            first = newNode;
            last = newNode;
        }
        else {
            Node newNode = new Node(item);
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }

        size++;

    }

    // remove and return the item from the front
    public Item removeFirst() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node oldFirst = first;

        if (size() == 1) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }

        size--;

        return oldFirst.item;

    }

    // remove and return the item from the back
    public Item removeLast() {

        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node oldLast = last;

        if (size() == 1) {
            first = null;
            last = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }

        size--;

        return oldLast.item;

    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // iterator to traverse items from front to back
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        Deque<Integer> deque = new Deque<>();

        // IllegalArgumentException
        // deque.addFirst(null);
        // deque.addLast(null);

        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addLast(7);
        deque.addLast(8);
        deque.addLast(9);

        for (Integer i : deque) {
            StdOut.print(i + " ");              // 3 2 1 7 8 9
        }
        StdOut.println();

        StdOut.println(deque.removeFirst());    // 3
        StdOut.println(deque.removeLast());     // 9

        for (Integer i : deque) {
            StdOut.print(i + " ");              // 2 1 7 8
        }
        StdOut.println();

        StdOut.println(deque.isEmpty());        // false

        StdOut.println(deque.size());           // 4

        StdOut.println(deque.removeFirst());    // 2
        StdOut.println(deque.removeFirst());    // 1
        StdOut.println(deque.removeLast());     // 8
        StdOut.println(deque.removeLast());     // 7

        System.out.println(deque.size());       // 0

        System.out.println(deque.isEmpty());    // true

        // NoSuchElementException
        // deque.removeFirst();
        // deque.removeLast();

    }

}
