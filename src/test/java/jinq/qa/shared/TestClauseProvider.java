package jinq.qa.shared;

import delegates.*;
import jinq.core.DefaultClauseProvider;
import jinq.core.IEnumerable;

public class TestClauseProvider<T extends Comparable<T>> extends DefaultClauseProvider<T> {

	@Override
	public IEnumerable<T> getWhereEnumerable(Iterable<T> source, final Predicate<T> predicate) {
		return super.getWhereEnumerable(source, value -> !predicate.evaluate(value));
	}
}
