package delegates;

public interface Action<T> {
	void apply(T item);
}
