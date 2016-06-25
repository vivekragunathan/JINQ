package jinq;

import delegates.*;
import jodash.*;

import java.util.Comparator;
import java.util.Iterator;

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
		this.clauseProvider = clauseProvider == null ? new DefaultClauseProvider<T>() : clauseProvider;
	}

	// region JINQ

	@Override
	public IEnumerable<T> where(Predicate<T> predicate) {
		return new Enumerable<>(
				clauseProvider.getWhereIterable(source, predicate)
		);
	}

	@Override
	public Iterable<T> select() {
		return select(null);
	}

	@Override
	public <R> Iterable<R> select(Func<T, R> selector) {
		return clauseProvider.getSelectIterable(source, selector);
	}

	@Override
	public IEnumerable<T> orderBy(Comparator<T> comparator) {
		return new Enumerable<>(
				clauseProvider.getOrderByIterable(source, comparator)
		);
	}

	@Override
	public <K extends Comparable<K>>
	IEnumerable<GroupByEntry<K, T>> groupBy(Func<T, K> keySelector) {
		return new Enumerable<>(
				clauseProvider.getGroupByIterable(source, keySelector)
		);
	}

	@Override
	public <K extends Comparable<K>, E extends Comparable<E>>
	IEnumerable<GroupByEntry<K, E>> groupBy(Func<T, K> keySelector, Func<T, E> elementSelector) {
		return new Enumerable<>(
				clauseProvider.getGroupByIterable(source, keySelector, elementSelector)
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
	public Iterable<T> distinct() {
		return Iterables.distinct(source);
	}

	@Override
	public Iterable<T> toList() {
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
}