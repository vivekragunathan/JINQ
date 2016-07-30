package jinq.core;

import delegates.*;
import jinq.clause.SelectIterable;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public interface IEnumerable<T extends Comparable<T>> extends Iterable<T> {

	// region JINQ

	IEnumerable<T> where(Predicate<T> predicate);

	IEnumerable<T> skip(Predicate<T> predicate);

	SelectIterable<T, T> select();

	<R> SelectIterable<T, R> select(Func<T, R> selector);

	IEnumerable<T> orderBy(Comparator<T> comparator);

	<K extends Comparable<K>>
	IEnumerable<GroupByEntry<K, T>> groupBy(Func<T, K> keySelector);

	<K extends Comparable<K>, E extends Comparable<E>>
	IEnumerable<GroupByEntry<K, E>> groupBy(Func<T, K> keySelector, Func<T, E> elementSelector);

	T first();

	T last();

	int count();

	Set<T> distinct();

	List<T> toList();

	// endregion

	// JODASH

	boolean exists(Predicate<T> predicate);

	T find(T needle);

	T find(Predicate<T> predicate);

	// endregion
}