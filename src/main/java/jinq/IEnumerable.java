package jinq;

import delegates.*;

import java.util.Comparator;

public interface IEnumerable<T extends Comparable<T>> extends Iterable<T> {

	// region JINQ

	IEnumerable<T> where(Predicate<T> predicate);

	Iterable<T> select();

	<R /*extends Comparable<R>*/> Iterable<R> select(Func<T, R> selector);

	IEnumerable<T> orderBy(Comparator<T> comparator);

	<K extends Comparable<K>>
	IEnumerable<GroupByEntry<K, T>> groupBy(Func<T, K> keySelector);

	<K extends Comparable<K>, E extends Comparable<E>>
	IEnumerable<GroupByEntry<K, E>> groupBy(Func<T, K> keySelector, Func<T, E> elementSelector);

	Iterable<T> distinct();

	Iterable<T> toList();

	// endregion

	// JODASH

	boolean exists(Predicate<T> predicate);

	T find(T needle);

	T find(Predicate<T> predicate);

	// endregion
}
