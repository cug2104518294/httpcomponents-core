package org.apache.hc.core5.http.config;

import org.apache.hc.core5.util.Args;

/**
 * Chain of doubly linked elements.
 * <p>
 * This implementation makes no attempts to ensure uniqueness of element names.
 * <p>
 * 双向循环链表   所谓first是以master节点为标准 来区别是第一个节点还是最后一个节点
 */
public class NamedElementChain<E> {

    private final Node master;
    private int size;

    public NamedElementChain() {
        this.master = new Node("master", null);
        this.master.previous = this.master;
        this.master.next = this.master;
        this.size = 0;
    }

    public Node getFirst() {
        return master.next != master ? master.next : null;
    }

    public Node getLast() {
        return master.previous != master ? master.previous : null;
    }

    public Node addFirst(final E value, final String name) {
        Args.notBlank(name, "Name");
        Args.notNull(value, "Value");
        final Node newNode = new Node(name, value);
        final Node oldNode = master.next;
        master.next = newNode;
        newNode.previous = master;
        newNode.next = oldNode;
        oldNode.previous = newNode;
        size++;
        return newNode;
    }

    public Node addLast(final E value, final String name) {
        Args.notBlank(name, "Name");
        Args.notNull(value, "Value");
        final Node newNode = new Node(name, value);
        final Node oldNode = master.previous;
        master.previous = newNode;
        newNode.previous = oldNode;
        newNode.next = master;
        oldNode.next = newNode;
        size++;
        return newNode;
    }

    public Node find(final String name) {
        Args.notBlank(name, "Name");
        return doFind(name);
    }

    private Node doFind(final String name) {
        Node current = master.next;
        while (current != master) {
            if (name.equals(current.name)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public Node addBefore(final String existing, final E value, final String name) {
        Args.notBlank(name, "Name");
        Args.notNull(value, "Value");
        final Node current = doFind(existing);
        if (current == null) {
            return null;
        }
        final Node newNode = new Node(name, value);
        final Node previousNode = current.previous;
        previousNode.next = newNode;
        newNode.previous = previousNode;
        newNode.next = current;
        current.previous = newNode;
        size++;
        return newNode;
    }

    public Node addAfter(final String existing, final E value, final String name) {
        Args.notBlank(name, "Name");
        Args.notNull(value, "Value");
        final Node current = doFind(existing);
        if (current == null) {
            return null;
        }
        final Node newNode = new Node(name, value);
        final Node nextNode = current.next;
        current.next = newNode;
        newNode.previous = current;
        newNode.next = nextNode;
        nextNode.previous = newNode;
        size++;
        return newNode;
    }

    public boolean remove(final String name) {
        final Node node = doFind(name);
        if (node == null) {
            return false;
        }
        node.previous.next = node.next;
        node.next.previous = node.previous;
        node.previous = null;
        node.next = null;
        size--;
        return true;
    }

    public boolean replace(final String existing, final E value) {
        final Node node = doFind(existing);
        if (node == null) {
            return false;
        }
        node.value = value;
        return true;
    }

    public int getSize() {
        return size;
    }

    public class Node {

        private final String name;
        private E value;
        private Node previous;
        private Node next;

        Node(final String name, final E value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public E getValue() {
            return value;
        }

        public Node getPrevious() {
            return previous != master ? previous : null;
        }

        public Node getNext() {
            return next != master ? next : null;
        }

        @Override
        public String toString() {
            return name + ": " + value;
        }

    }

}