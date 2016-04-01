package jodash;

import java.util.Iterator;

public class ArrayIterator<T> implements Iterator<T> {

	private final T[] array;
	private int index = 0;

	public ArrayIterator(T[] array) {
		this.array = array;
	}

	public boolean hasNext() {
		return array.length > 0 && index < array.length;
	}

	public T next() {
		return array[index++];
	}

	public void remove() {
		throw new UnsupportedOperationException("Iterator.remove");
	}
}
