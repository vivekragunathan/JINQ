package jinq.qa.shared;

import delegates.*;
import jinq.core.DefaultClauseProvider;

public class TestClauseProvider<T extends Comparable<T>> extends DefaultClauseProvider<T> {

	@Override
	public Iterable<T> getWhereIterable(Iterable<T> source, final Predicate<T> predicate) {
		return super.getWhereIterable(source, value -> !predicate.evaluate(value));
	}
}
