package org.fpalacios.flibs.util;

import java.util.Iterator;
import java.util.ListIterator;

public class ForwardFlinkedListIterator<T> implements Iterator<T>, ListIterator<T>{

	private Node<T> currentNode;
	private FlinkedList<T> list;

	public ForwardFlinkedListIterator(FlinkedList<T> list, Node<T> start) {
		this.list = list;
		currentNode = new Node<T>(null);
		currentNode.next = start;
	}

	public void skip(Node<T> node) {
		if (currentNode == node) currentNode = currentNode.prev;
	}

	public boolean hasNext() {
		return currentNode.next != null;
	}

	public T next() {
		currentNode = currentNode.next;
		return currentNode.obj;
	}

	public void remove() {
		currentNode.remove();
	}

	public boolean hasPrevious() {
		return (currentNode.prev != null);
	}

	public T previous() {
		return currentNode.prev.obj;
	}

	public void setCurrentNode(Node<T> currentNode) {
		this.currentNode = currentNode;
	}

	/**
	 * Not implemented
	 */
	public int nextIndex() {
		return -1;
	}

	/**
	 * Not implemented
	 */
	public int previousIndex() {
		return -1;
	}

	public void set(T e) {
		currentNode.obj = e;
	}

	public void add(T e) {
		list.add(e);
	}

}
