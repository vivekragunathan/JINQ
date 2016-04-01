package jinq;

import delegates.Func;

import java.util.Iterator;

public class SelectIterable<T, R> implements Iterable<R>, Iterator<R> {

	private final Iterator<T> iterator;
	private final Func<T, R>  selector;

	public SelectIterable(Iterable<T> source, Func<T, R> selector) {
		this.iterator = source.iterator();
		this.selector = selector;
	}

	private SelectIterable(Iterator<T> iterator, Func<T, R> selector) {
		this.iterator = iterator;
		this.selector = selector;
	}

	public Iterable<R> asIterable() {
		return this;
	}

	@Override
	public Iterator<R> iterator() {
		return new SelectIterable<>(iterator, selector);
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public R next() {
		final T next = iterator.next();
		return selector == null ? (R) next : selector.apply(next);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Iterator.remove not supported");
	}
}
