package delegates;

@FunctionalInterface
public interface Action2<T1, T2> {
	void apply(T1 item1, T2 item2);
}
