package jinq;

import java.util.Iterator;

public class Enumerator<T> implements IEnumerator<T> {

	private final Iterator<T> iterator;

	public Enumerator(Iterable<T> iterable) {
		this.iterator = iterable.iterator();
	}

	public boolean moveNext() {
		return hasNext();
	}

	public T current() {
		return next();
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	public T next() {
		return iterator.next();
	}

	public void remove() {
		throw new UnsupportedOperationException("FromEnumerator.remove");
	}
}
