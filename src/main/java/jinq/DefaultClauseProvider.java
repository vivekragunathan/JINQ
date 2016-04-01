package jinq;

import delegates.*;
import java.util.*;

public class DefaultClauseProvider<T extends Comparable<T>> implements IClauseProvider<T> {

	public DefaultClauseProvider() {
	}

	@Override
	public Iterable<T> getWhereIterable(Iterable<T> source, Predicate<T> predicate) {
		return new Enumerable<>(new PredicateIterable<T>(source, predicate));
	}

	@Override
	public Iterable<T> getOrderByIterable(Iterable<T> source, Comparator<T> keySelector) {
		return new Enumerable<>(new OrderByIterable<>(source, keySelector));
	}

	@Override
	public Iterable<T> getSelectIterable(Iterable<T> source) {
		return getSelectIterable(source, null);
	}

	@Override
	public <R /*extends Comparable<R>*/> Iterable<R> getSelectIterable(Iterable<T> source, Func<T, R> selector) {
		return new SelectIterable<>(source, selector);
	}

	@Override
	public <K extends Comparable<K>>
	IEnumerable<GroupByEntry<K, T>> getGroupByIterable(Iterable<T> source, Func<T, K> keySelector) {
		return getGroupByIterable(source, keySelector, null);
	}

	@Override
	public <K extends Comparable<K>, E extends Comparable<E>>
	IEnumerable<GroupByEntry<K, E>> getGroupByIterable(Iterable<T> source,
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
