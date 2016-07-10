package jinq.clause;

import jinq.Enumerable;
import jodash.Iterables;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class OrderByIterable<T extends Comparable<T>> implements Iterable<T>, Iterator<T> {

	private final Iterator<T> iterator;
	private final Iterable<T> source;
	private final Comparator<T> keySelector;
	private Iterable<T> orderedSource;


	public OrderByIterable(Iterable<T> source, Comparator<T> keySelector) {
		this.source = source;
		this.keySelector = keySelector;
		iterator = null;
	}

	private OrderByIterable(Iterable<T> source) {
		this.orderedSource = source;
		this.iterator = source.iterator();
		this.source = null;
		this.keySelector = null;
	}

	@Override
	public Iterator<T> iterator() {
		if (orderedSource == null) {
			orderedSource = sort(source, keySelector);
		}

		return new OrderByIterable<>(orderedSource);
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

	private static <T extends Comparable<T>> Enumerable<T> sort(Iterable<T> source, Comparator<T> comparator) {
		final List<T> list = Iterables.toList(source);
		Collections.sort(list, comparator);
		return new Enumerable<>(list);
	}
}
