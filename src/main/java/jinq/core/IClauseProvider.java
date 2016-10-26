package jinq.core;

import delegates.*;
import jinq.clause.SelectIterable;

import java.util.Comparator;

public interface IClauseProvider<T extends Comparable<T>> {

	IEnumerable<T> getWhereEnumerable(Iterable<T> source, Predicate<T> predicate);

	IEnumerable<T> getSkipEnumerable(Iterable<T> source, Predicate<T> predicate);

	IEnumerable<T> getOrderByEnumerable(Iterable<T> source, Comparator<T> keySelector);

	SelectIterable<T, T> getSelectEnumerable(Iterable<T> source);

	<R> SelectIterable<T, R> getSelectEnumerable(Iterable<T> source, Func<T, R> selector);

	<K extends Comparable<K>>
	IEnumerable<GroupByEntry<K, T>> getGroupByEnumerable(Iterable<T> source, Func<T, K> keySelector);

	<K extends Comparable<K>, E extends Comparable<E>>
	IEnumerable<GroupByEntry<K, E>> getGroupByEnumerable(
			Iterable<T> source,
			Func<T, K> keySelector,
			Func<T, E> elementSelector
	);
}
