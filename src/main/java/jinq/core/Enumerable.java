package jinq.core;

import delegates.*;
import jinq.clause.PredicateIterable;
import jinq.clause.RelayIterable;
import jinq.clause.SelectIterable;
import jodash.*;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Enumerable<T extends Comparable<T>> extends RelayIterable<T> implements IEnumerable<T> {

	private final IClauseProvider<T> clauseProvider;

	public Enumerable(T[] array) {
		this(array, null);
	}

	public Enumerable(T[] array, IClauseProvider<T> clauseProvider) {
		this(new IterableArray<>(array), clauseProvider);
	}

	public Enumerable(Iterable<T> iterable) {
		this(iterable, null);
	}

	public Enumerable(Iterable<T> iterable, IClauseProvider<T> clauseProvider) {
		super(iterable);
		this.clauseProvider = clauseProvider == null ? new DefaultClauseProvider<>() : clauseProvider;
	}

	// region JINQ

	@Override
	public IEnumerable<T> where(Predicate<T> predicate) {
		// Case: Where chaining eg. where(cond1).where(cond2)
		// Instead of creating additional where enumerable(s), the
		// predicate is added to existing PredicateIterable.
		if (isPredicateIterable() && !isSkipIterable()) {
			((PredicateIterable<T>) this.source).add(predicate);
			return this;
		}

		return clauseProvider.getWhereEnumerable(source, predicate);
	}

	@Override
	public IEnumerable<T> skip(Predicate<T> predicate) {
		// Case: skip chaining eg. skip(cond1).skip(cond2)
		// Instead of creating additional where enumerable(s), the
		// predicate is added to existing PredicateIterable.
		if (isPredicateIterable() && isSkipIterable()) {
			((PredicateIterable<T>) this.source).add(predicate);
			return this;
		}

		return clauseProvider.getSkipEnumerable(source, predicate);
	}

	@Override
	public SelectIterable<T, T> select() {
		return select(null);
	}

	@Override
	public <R> SelectIterable<T, R> select(Func<T, R> selector) {
		return clauseProvider.getSelectEnumerable(source, selector);
	}

	@Override
	public IEnumerable<T> orderBy(Comparator<T> comparator) {
		return new Enumerable<>(
				clauseProvider.getOrderByEnumerable(source, comparator)
		);
	}

	@Override
	public <K extends Comparable<K>>
	IEnumerable<GroupByEntry<K, T>> groupBy(Func<T, K> keySelector) {
		return new Enumerable<>(
				clauseProvider.getGroupByEnumerable(source, keySelector)
		);
	}

	@Override
	public <K extends Comparable<K>, E extends Comparable<E>>
	IEnumerable<GroupByEntry<K, E>> groupBy(Func<T, K> keySelector, Func<T, E> elementSelector) {
		return new Enumerable<>(
				clauseProvider.getGroupByEnumerable(source, keySelector, elementSelector)
		);
	}

	@Override
	public T first() {
		final Iterator<T> iterator = source.iterator();
		return iterator.hasNext() ? iterator.next() : null;
	}

	@Override
	public T last() {
		final Iterator<T> iterator = source.iterator();

		T last = null;

		while (iterator.hasNext()) {
			last = iterator.next();
		}

		return last;
	}

	@Override
	public int count() {
		return Iterables.count(source);
	}

	@Override
	public Set<T> distinct() {
		return Iterables.distinct(source);
	}

	@Override
	public List<T> toList() {
		return Iterables.toList(source);
	}

	// endregion

	// region JODASH

	@Override
	public boolean exists(Predicate<T> predicate) {
		return Iterables.exists(this.source, predicate);
	}

	@Override
	public T find(T needle) {
		return Iterables.find(this.source, needle);
	}

	@Override
	public T find(Predicate<T> predicate) {
		return Iterables.find(this.source, predicate);
	}

	// endregion

	// region Private and Helper Methods

	private boolean isPredicateIterable() {
		return this.source instanceof PredicateIterable;
	}

	/**
	 * NOTE: Has to be called in conjunction with isPredicateIterable.
	 * Otherwise, a return value of false might end up suggesting
	 * that the source is a whereIterable (not a skipIterable) where as
	 * the source might not be a {@link PredicateIterable} in the
	 * first place.
	 */
	private boolean isSkipIterable() {
		if (isPredicateIterable()) {
			final PredicateIterable<T> source = (PredicateIterable<T>) this.source;
			return source.isSkipIterable();
		}

		return false;
	}

	private static <T extends Comparable<T>> Enumerable<T> toEnumerable(Iterable<T> iterable) {
		return iterable instanceof Enumerable
		       ? (Enumerable<T>) iterable
		       : new Enumerable<>(iterable);
	}

	// endregion
}