package delegates;

public class Action2Shim<T1, T2> implements Action2<T1, T2> {

	private final Action<T1> action;

	public Action2Shim(Action<T1> action) {
		this.action = action;
	}

	@Override
	public void apply(T1 item1, T2 item2) {
		action.apply(item1);
	}
}
