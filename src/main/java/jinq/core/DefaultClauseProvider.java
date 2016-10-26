package jinq.core;

import delegates.*;
import jinq.clause.OrderByIterable;
import jinq.clause.PredicateIterable;
import jinq.clause.SelectIterable;

import java.util.*;

public class DefaultClauseProvider<T extends Comparable<T>> implements IClauseProvider<T> {

	public DefaultClauseProvider() {
	}

	@Override
	public IEnumerable<T> getWhereEnumerable(Iterable<T> source, Predicate<T> predicate) {
		return new Enumerable<>(new PredicateIterable<>(source, predicate));
	}

	@Override
	public IEnumerable<T> getSkipEnumerable(Iterable<T> source, Predicate<T> predicate) {
		return new Enumerable<>(new PredicateIterable<>(source, predicate, true));
	}

	@Override
	public IEnumerable<T> getOrderByEnumerable(Iterable<T> source, Comparator<T> keySelector) {
		return new Enumerable<>(new OrderByIterable<>(source, keySelector));
	}

	@Override
	public SelectIterable<T, T> getSelectEnumerable(Iterable<T> source) {
		return getSelectEnumerable(source, null);
	}

	@Override
	public <R> SelectIterable<T, R> getSelectEnumerable(Iterable<T> source, Func<T, R> selector) {
		return new SelectIterable<>(source, selector);
	}

	@Override
	public <K extends Comparable<K>>
	IEnumerable<GroupByEntry<K, T>> getGroupByEnumerable(Iterable<T> source, Func<T, K> keySelector) {
		return getGroupByEnumerable(source, keySelector, null);
	}

	@Override
	public <K extends Comparable<K>, E extends Comparable<E>>
	IEnumerable<GroupByEntry<K, E>> getGroupByEnumerable(Iterable<T> source,
	                                                     Func<T, K> keySelector,
	                                                     Func<T, E> elementSelector) {
		final Iterable<GroupByEntry<K, E>> groups = performGroupBy(
				source,
				keySelector,
				elementSelector
		);

		return new Enumerable<>(groups);
	}

	public static <T, K extends Comparable<K>, V extends Comparable<V>>
	Iterable<GroupByEntry<K, V>> performGroupBy(Iterable<T> source,
	                                            Func<T, K> keySelector,
	                                            Func<T, V> elementSelector) {

		final Map<K, List<V>> groupMap = new HashMap<>();

		for (T item : source) {
			final K       key       = keySelector.apply(item);
			final V       element   = elementSelector == null ? (V) item : elementSelector.apply(item);
			final boolean keyExists = groupMap.containsKey(key);
			final List<V> elements  = keyExists ? groupMap.get(key) : new ArrayList<V>();
			elements.add(element);

			if (!keyExists) {
				groupMap.put(key, elements);
			}
		}

		return toGroupEntries(groupMap);
	}

	public static <K extends Comparable<K>, V extends Comparable<V>>
	Iterable<GroupByEntry<K, V>> toGroupEntries(Map<K, List<V>> map) {
		final List<GroupByEntry<K, V>> groupings = new ArrayList<>();

		for (K key : map.keySet()) {
			groupings.add(new GroupByEntry<>(key, map.get(key)));
		}

		return groupings;
	}
}
