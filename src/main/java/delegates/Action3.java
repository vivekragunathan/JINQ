package delegates;

@FunctionalInterface
public interface Action3<T1, T2, T3> {
	void apply(T1 item1, T2 item2, T3 item3);
}
