package jinq.clause;

import delegates.Predicate;
import jinq.iterators.PredicateIterator;

import java.util.Iterator;

public class PredicateIterable<T> implements Iterable<T> { //, Iterator<T> {

	//private final Iterator<T>  iterator;
	private final Iterable<T>  source;
	private final Predicate<T> predicate;
	private       T            next;

	public PredicateIterable(Iterable<T> source, Predicate<T> predicate) {
		//this.iterator = source.iterator();
		this.source = source;
		this.predicate = predicate;
	}

	/*private PredicateIterable(Iterator<T> iterator, Predicate<T> predicate) {
		this.iterator = iterator;
		this.predicate = predicate;
	}*/

	@Override
	public Iterator<T> iterator() {
		return new PredicateIterator<T>(source, predicate);
	}

	/*public Iterator<T> iterator() {
		return new RelayIterator<T>(iterator, predicate);
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
	}*/
}
