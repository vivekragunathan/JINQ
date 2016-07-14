package jodash;

import jinq.core.IEnumerator;
import jinq.iterators.RelayIterator;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class IterableMap<K, V> implements Map<K, V>, Iterable<Map.Entry<K, V>> {

	private final Map<K, V> map;

	public IterableMap(Map<K, V> map) {
		this.map = map;
	}

	public IEnumerator<Entry<K, V>> getEnumerator() {
		return new RelayIterator<>(map.entrySet());
	}

	@Override
	public Iterator<Map.Entry<K, V>> iterator() {
		return new RelayIterator<>(map.entrySet());
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return map.get(key);
	}

	@Override
	public V put(K key, V value) {
		return map.put(key, value);
	}

	@Override
	public V remove(Object key) {
		return map.remove(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> other) {
		map.putAll(other);
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return map.entrySet();
	}
}
