package org.fpalacios.flibs.util;

public class Node<T> {
	public T obj;
	public Node<T> prev;
	public Node<T> next;

	public FlinkedList<T> list;

	public Node(FlinkedList<T> parentList) {
		this.list = parentList;
	}

	public Node(FlinkedList<T> parentList, T obj) {
		this.list = parentList;
		this.obj = obj;
	}

	public Node(FlinkedList<T> parentList, T obj, Node<T> prev, Node<T> next) {
		this.obj  = obj;
		this.prev = prev;
		this.next = next;

		this.list = parentList;
	}

	public void remove() {
		if (prev != null) prev.setNext(next);

		else if (next != null) next.setPrev(prev);


		list.notifyRemove(this);
	}

	public void setNext(Node<T> next) {
		this.next = next;
		if (next != null) next.prev = this;
	}
	public void setPrev(Node<T> prev) {
		this.prev = prev;
		if (prev != null) prev.next = this;
	}

	public String toString() {
		return obj.toString();
	}
}
