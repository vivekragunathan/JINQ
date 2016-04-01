package jinq;

import misc.Throw;

import java.util.Iterator;

public class RelayIterator<T> implements Iterator<T>, IEnumerator<T> {

	private final Iterator<T> iterator;

	public RelayIterator(Iterable<T> source) {
		Throw.WhenNull(source, "Encountered null iterable object");
		this.iterator = source.iterator();
	}

	@Override
	public boolean moveNext() {
		return hasNext();
	}

	@Override
	public T current() {
		return next();
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public T next() {
		return iterator.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Iterator.remove not supported");
	}
}