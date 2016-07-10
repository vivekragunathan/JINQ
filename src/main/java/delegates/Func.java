package delegates;

@FunctionalInterface
public interface Func<T, R> {
	R apply(T item);
}

