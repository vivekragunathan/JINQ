package jinq.clause;

import delegates.Func;
import jinq.iterators.SelectManyIterator;
import jodash.Iterables;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class SelectManyIterable<T, R> implements Iterable<R> {

	private final List<Iterable<T>> iterables;
	private final Func<T, R>        selector;

	@SafeVarargs
	public SelectManyIterable(Iterable<T>... iterables) {
		this(Arrays.asList(iterables));
	}

	public SelectManyIterable(Iterable<Iterable<T>> iterables) {
		this(Iterables.toList(iterables), null);
	}

	public SelectManyIterable(List<Iterable<T>> iterables, Func<T, R> selector) {
		this.iterables = iterables;
		this.selector = selector;
	}

	@Override
	public Iterator<R> iterator() {
		return new SelectManyIterator<>(iterables, selector);
	}
}
