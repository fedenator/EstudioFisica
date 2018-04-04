package org.fpalacios.flibs.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FlinkedList<E> implements Iterable<E>, List<E> {

	private Node<E> head;
	private Node<E> tail;

	private int size = 0;

	private ForwardFlinkedListIterator<E> iterator;

	public FlinkedList() {

	}

	@SafeVarargs
	public FlinkedList(E... elements) {
		addAll( Arrays.asList(elements) );
	}
	public Iterator<E> iterator() {
		iterator = new ForwardFlinkedListIterator<E>(this, head);
		return iterator;
	}

	public void sort(Valueator<E> valueator) {
		quicksort(valueator, head, tail);
	}

	private void swap(Node<E> node1, Node<E> node2) {
		E temp = node1.obj;
		node1.obj = node2.obj;
		node2.obj = temp;
	}

	private void quicksort(Valueator<E> valueator, Node<E> start, Node<E> end) {
		if (start.prev != end && start != end) return;

		Node<E> pivot = start;
		Node<E> left  = start.next;
		Node<E> right = end;

		while ( !(left == right || right.next == left) ) {
			while (right.next != null && valueator.value(right.obj) <= valueator.value(pivot.obj) ) right = right.prev;
			while (right.next != null && valueator.value(left.obj)  >  valueator.value(pivot.obj) ) left = left.next;

			if ( valueator.value(left.obj) > valueator.value(right.obj) ) swap(left, right);

			swap(pivot, right);
			pivot = right;
			quicksort(valueator, start, pivot.prev);
			quicksort(valueator, pivot.next, end);
		}

	}

	/**
	 * You are not suposed to use this
	 */
	public Node<E> getHead() {
		return head;
	}
	/**
	 * You are not suposed to use this
	 */
	public void setHead(Node<E> head) {
		this.head = head;
	}

	/**
	 * You are not suposed to use this
	 */
	public Node<E> getTail() {
		return tail;
	}
	/**
	 * You are not suposed to use this
	 */
	public void setTail(Node<E> tail) {
		this.tail = tail;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return head != null;
	}

	public boolean contains(Object o) {
		return getNode(o) != null;
	}

	public Object[] toArray() {
		Object array[] = new Object[size];
		int i = 0;
		for (E item : this) array[i++] = item;
		return array;
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		int i = 0;
		for (E item : this) a[i++] = (T) item;
		return a;
	}

	public boolean add(E e) {
		Node<E> newNode = new Node<E>(this, e);

		if (size == 0) {
			head = newNode;
			tail = newNode;
		} else {
			tail.setNext(newNode);
			tail = newNode;
		}

		size++;
		return true;
	}

	public E remove(int index) {
		Node<E> node = getNode(index);

		if (node != null) {
			iterator.skip(node);

			E temp = node.obj;
			node.remove();
			return temp;
		}

		return null;
	}

	public boolean remove(Object o) {
		Node<E> node = getNode(o);

		if (node != null) {
			iterator.skip(node);
			node.remove();
			return true;
		}

		return false;
	}

	/**
	 * You are not suposed to use this
	 */
	public void notifyRemove(Node<E> node) {
		if (node == head) head = node.next;
		if (node == tail) tail = node.prev;
		size--;
	}

	public boolean containsAll(Collection<?> c) {
		if (c.size() <= 0) return true;

		for (E item : this)
			if ( c.contains(item) ) c.remove(item);

		if (c.size() <= 0) return true;

		return false;
	}

	public boolean addAll(Collection<? extends E> c) {
		for (E item : c) add(item);
		return true;
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		if (index < 0 || index >= size) throw new IndexOutOfBoundsException();

		Node<E> prev = getNode(index);
		Node<E> temp;
		Node<E> next = prev.next;

		for (E item : c) {
			temp = new Node<E>(this, item);
			prev.setNext(temp);
			prev = temp;
		}

		if (next != null);
		next.setPrev(prev);

		return true;
	}

	public boolean removeAll(Collection<?> c) {
		for (Node<E> node = head; node.next != null; node = node.next)
			if ( c.contains(node) ) {
				c.remove(node);
				Node<E> temp = node.next;
				node.remove();
				c.remove(node.obj);
				node = temp;
				if (c.size() == 0) return true;
			}
		return c.size() == 0;
	}

	public boolean retainAll(Collection<?> c) {
		boolean flag = false;

		for (Iterator<?> iterator = c.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			if ( !this.contains(object) ) {
				iterator.remove();
				flag = true;
			}
		}

		return flag;
	}

	public void clear() {
		head = tail = null;
		size = 0;
	}

	public E get(int index) {
		return getNode(index).obj;
	}

	private Node<E> getNode(int index) {
		if (index >= size || index < 0) throw new IndexOutOfBoundsException();
		Node<E> node;

		if (index < size / 2) {
			node = head;
			for (int i = 0; i < index; i++) node = node.next;
		} else {
			node = tail;
			for (int i = 0; i > index; i++) node = node.prev;
		}

		return node;

	}

	private Node<E> getNode(Object o) {
		for (Node<E> i = head; i.next != null; i = i.next) if (i.obj == o) return i;

		return null;
	}

	public E set(int index, E element) {
		Node<E> node = getNode(index);
		E temp = node.obj;
		node.obj = element;
		return temp;
	}

	public void add(int index, E element) {
		Node<E> node = getNode(index);
		Node<E> newNode = new Node<E>(this, element);
		node.prev.setNext(newNode);
		node.setPrev(newNode);
	}


	public int indexOf(Object o) {
		int index = 0;
		for (E item : this) {
			if (item == o) return index++;
		}

		return -1;
	}

	public int lastIndexOf(Object o) {
		int index = -1;

		int i = 0;
		for (E item : this)
			if (item == o) index = i++;

		return index;
	}

	public ListIterator<E> listIterator() {
		return new ForwardFlinkedListIterator<E>(this, head);
	}

	/**
	 * Not implemented
	 */
	public ListIterator<E> listIterator(int index) {
		return null;
	}


	public List<E> subList(int fromIndex, int toIndex) {
		if(toIndex >= size) throw new IndexOutOfBoundsException();

		FlinkedList<E> flag = new FlinkedList<E>();

		int i = fromIndex;
		for (Node<E> node = getNode(fromIndex); i < toIndex; node = node.next, i++) {
			flag.add(node.obj);
		}

		return flag;
	}
}
