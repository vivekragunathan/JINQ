package jinq.iterators;

import delegates.Func;

import java.util.Iterator;

public class FuncIterator<T, R> implements Iterator<R> {

	private final Func<T, R>  selector;
	private final Iterator<T> iterator;

	public FuncIterator(Iterable<T> source, Func<T, R> selector) {
		this(source.iterator(), selector);
	}

	private FuncIterator(Iterator<T> iterator, Func<T, R> selector) {
		this.iterator = iterator;
		this.selector = selector;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@SuppressWarnings("unchecked")
	@Override
	public R next() {
		final T current = iterator.next();
		return selector == null ? (R) current : selector.apply(current);
	}
}
