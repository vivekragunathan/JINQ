package jinq.qa.shared;

import delegates.*;
import jinq.DefaultClauseProvider;

public class TestClauseProvider<T extends Comparable<T>> extends DefaultClauseProvider<T> {

	@Override
	public Iterable<T> getWhereIterable(Iterable<T> source, final Predicate<T> predicate) {
		return super.getWhereIterable(source, new Predicate<T>() {
			@Override
			public boolean evaluate(T value) {
				return !predicate.evaluate(value);
			}
		});
	}
}
