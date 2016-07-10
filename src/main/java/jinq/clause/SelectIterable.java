package jinq.clause;

import delegates.Func;
import jinq.iterators.FuncIterator;
import jodash.Iterables;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SelectIterable<T, R> implements Iterable<R> {  //, Iterator<R> {

	//private final Iterator<T> iterator;
	private final Iterable<T> source;
	private final Func<T, R>  selector;

	public SelectIterable(Iterable<T> source, Func<T, R> selector) {
		//this.iterator = source.iterator();
		this.source = source;
		this.selector = selector;
	}

	/*private SelectIterable(Iterator<T> iterator, Func<T, R> selector) {
		this.iterator = iterator;
		this.selector = selector;
	}*/

	public List<R> toList() {
		return Iterables.toList(this);
	}

	public Set<R> distinct() {
		return Iterables.distinct(this);
	}

	public int count() {
		return Iterables.count(this);
	}

	@Override
	public Iterator<R> iterator() {
		return new FuncIterator<>(source, selector);
		//return new SelectIterable<>(iterator, selector);
	}

	/*@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@SuppressWarnings("unchecked")
	@Override
	public R next() {
		final T next = iterator.next();
		return selector == null ? (R) next : selector.apply(next);
	}*/
}
