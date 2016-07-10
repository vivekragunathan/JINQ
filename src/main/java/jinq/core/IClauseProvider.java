package jinq.core;

import delegates.*;
import jinq.GroupByEntry;
import jinq.clause.SelectIterable;

import java.util.Comparator;

public interface IClauseProvider<T extends Comparable<T>> {

	Iterable<T> getWhereIterable(Iterable<T> source, Predicate<T> predicate);

	Iterable<T> getOrderByIterable(Iterable<T> source, Comparator<T> keySelector);

	SelectIterable<T, T> getSelectIterable(Iterable<T> source);

	<R> SelectIterable<T, R> getSelectIterable(Iterable<T> source, Func<T, R> selector);

	<K extends Comparable<K>>
	IEnumerable<GroupByEntry<K, T>> getGroupByIterable(Iterable<T> source, Func<T, K> keySelector);

	<K extends Comparable<K>, E extends Comparable<E>>
	IEnumerable<GroupByEntry<K, E>> getGroupByIterable(
			Iterable<T> source,
			Func<T, K> keySelector,
			Func<T, E> elementSelector
	);
}
