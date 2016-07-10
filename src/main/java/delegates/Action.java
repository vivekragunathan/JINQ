package delegates;

@FunctionalInterface
public interface Action<T> {
	void apply(T item);
}
