package delegates;

public interface Func<T, R> {
	R apply(T item);
}

