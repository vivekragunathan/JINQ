package jinq.iterators;

import delegates.Predicate;

import java.util.Iterator;

public class PredicateIterator<T> implements Iterator<T> {

	private final Iterator<T>  iterator;
	private final Predicate<T> predicate;

	private T next;


	public PredicateIterator(Iterable<T> source, Predicate<T> predicate) {
		this.iterator = source.iterator();
		this.predicate = predicate;
	}

	private PredicateIterator(Iterator<T> iterator, Predicate<T> predicate) {
		this.iterator = iterator;
		this.predicate = predicate;
	}

	@Override
	public boolean hasNext() {
		while (iterator.hasNext()) {
			final T item = iterator.next();
			if (predicate.evaluate(item)) {
				next = item;
				return true;
			}
		}

		return false;
	}

	@Override
	public T next() {
		return this.next;
	}
}
