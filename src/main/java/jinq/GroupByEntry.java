package jinq;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GroupByEntry<
		K extends Comparable<K>,
		V extends Comparable<V>
		> implements IGroupByEntry<K, V>, Comparable<GroupByEntry<K, V>> {

	private final K key;
	private final List<V> values = new ArrayList<>();

	public GroupByEntry(K key, V value) {
		this.key = key;
		this.values.add(value);
	}

	public GroupByEntry(K key, Iterable<V> values) {
		this.key = key;

		for (V value : values) {
			this.values.add(value);
		}
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public Iterable<V> values() {
		return values;
	}

	@Override
	public Iterator<V> iterator() {
		return new RelayIterator<>(values);
	}

	@Override
	public int compareTo(GroupByEntry<K, V> other) {
		return this.key.compareTo(other.key);
	}
}
