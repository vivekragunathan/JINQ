package jinq.iterators;

import delegates.Func;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class SelectManyIterator<T, R> implements Iterator<R> {

	private final List<Iterable<T>> iterables;
	private final Func<T, R>        selector;

	@SafeVarargs
	public SelectManyIterator(Iterable<T>... iterables) {
		this(Arrays.asList(iterables), null);
	}

	public SelectManyIterator(List<Iterable<T>> iterables, Func<T, R> selector) {
		this.iterables = iterables;
		this.selector = selector;
	}

	// TODO: Not yet implemented !!!

	@Override
	public boolean hasNext() {
		return false;
	}

	// TODO: Not yet implemented !!!

	@Override
	public R next() {
		return null;
	}
}
