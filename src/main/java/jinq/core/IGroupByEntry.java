package jinq.core;

public interface IGroupByEntry<K, V extends Comparable<V>> extends Iterable<V> {
	K getKey();
	Iterable<V> values();
}