package delegates;

@FunctionalInterface
public interface Predicate<T> {
	boolean evaluate(T value);
}
