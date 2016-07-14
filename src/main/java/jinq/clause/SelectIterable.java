package jinq.clause;

import delegates.Action3;
import delegates.Func;
import jinq.iterators.FuncIterator;
import jodash.Iterables;

import java.util.*;

public class SelectIterable<T, R> implements Iterable<R> {

	private final Iterable<T> source;
	private final Func<T, R>  selector;

	public SelectIterable(Iterable<T> source, Func<T, R> selector) {
		this.source = source;
		this.selector = selector;
	}

	@Override
	public Iterator<R> iterator() {
		return new FuncIterator<>(source, selector);
	}

	public List<R> toList() {
		return Iterables.toList(this);
	}

	public Set<R> distinct() {
		return Iterables.distinct(this);
	}

	public int count() {
		return Iterables.count(this);
	}

	/**
	 * See {@link Iterables#toMap(Iterable, Func, Action3)} for more details
	 */
	public <K, V> Map<K, V> toMap(List<V> items, Func<V, K> keySelector) {
		return toMap(items, keySelector, null);
	}

	/**
	 * See {@link Iterables#toMap(Iterable, Func, Action3)} for more details
	 */
	public <K, V> Map<K, V> toMap(List<V> items,
	                              Func<V, K> keySelector,
	                              Action3<Map<K, V>, V, V> keyExistsAction) {

		return Iterables.toMap(items, keySelector, keyExistsAction);
	}
}
