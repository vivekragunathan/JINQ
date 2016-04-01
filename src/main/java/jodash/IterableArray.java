package jodash;

import java.util.Iterator;

public class IterableArray<T> implements Iterable<T> {

	private final T[] array;

	public IterableArray(T[] array) {
		this.array = array;
	}

	public Iterator<T> iterator() {
		return new ArrayIterator<>(array);
	}
}
