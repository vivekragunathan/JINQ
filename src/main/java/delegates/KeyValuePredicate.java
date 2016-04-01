package delegates;

public interface KeyValuePredicate<K, V> {
	boolean apply(K key, V value);
}