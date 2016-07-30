package jinq.clause;

import delegates.Predicate;
import jinq.iterators.PredicateIterator;

import java.util.Iterator;

public class PredicateIterable<T> implements Iterable<T> {

	private final Iterable<T>  source;
	private final Predicate<T> predicate;
	private final boolean      invert;

	public PredicateIterable(Iterable<T> source, Predicate<T> predicate) {
		this(source, predicate, false);
	}

	public PredicateIterable(Iterable<T> source,
	                         Predicate<T> predicate,
	                         boolean invert) {
		this.source = source;
		this.predicate = predicate;
		this.invert = invert;
	}

	@Override
	public Iterator<T> iterator() {
		return new PredicateIterator<>(source, predicate, invert);
	}
}
