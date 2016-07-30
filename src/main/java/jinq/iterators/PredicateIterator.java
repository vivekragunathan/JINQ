package jinq.iterators;

import delegates.Predicate;

import java.util.Iterator;

public class PredicateIterator<T> implements Iterator<T> {

	private final Iterator<T>  iterator;
	private final Predicate<T> predicate;
	private final boolean      invert;

	private T next;

	public PredicateIterator(Iterable<T> source, Predicate<T> predicate) {
		this(source, predicate, false);
	}

	public PredicateIterator(Iterable<T> source,
	                         Predicate<T> predicate,
	                         boolean invert) {
		this.iterator = source.iterator();
		this.predicate = predicate;
		this.invert = invert;
	}

	@Override
	public boolean hasNext() {
		while (iterator.hasNext()) {
			final T       item   = iterator.next();
			final boolean result = predicate.evaluate(item);

			if (invert != result) {
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
