package jinq;

import delegates.Predicate;

import java.util.Iterator;

public class PredicateIterable<T> implements Iterable<T>, Iterator<T> {

	private final Iterator<T>  iterator;
	private final Predicate<T> predicate;
	private       T            next;

	public PredicateIterable(Iterable<T> source, Predicate<T> predicate) {
		this.iterator = source.iterator();
		this.predicate = predicate;
	}

	private PredicateIterable(Iterator<T> iterator, Predicate<T> predicate) {
		this.iterator = iterator;
		this.predicate = predicate;
	}

	@Override
	public Iterator<T> iterator() {
		return new PredicateIterable<>(iterator, predicate);
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
		return next;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Iterator.remove not supported");
	}
}
