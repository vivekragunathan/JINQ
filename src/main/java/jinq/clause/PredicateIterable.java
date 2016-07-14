package jinq.clause;

import delegates.Predicate;
import jinq.iterators.PredicateIterator;

import java.util.Iterator;

public class PredicateIterable<T> implements Iterable<T> {

	private final Iterable<T>  source;
	private final Predicate<T> predicate;

	public PredicateIterable(Iterable<T> source, Predicate<T> predicate) {
		this.source = source;
		this.predicate = predicate;
	}

	@Override
	public Iterator<T> iterator() {
		return new PredicateIterator<T>(source, predicate);
	}
}
