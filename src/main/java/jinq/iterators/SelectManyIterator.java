package jinq.iterators;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class SelectManyIterator<T> implements Iterable<T> {

	private final List<Iterable<T>> iterables;

	public SelectManyIterator(Iterable<T>... iterables) {
		this(Arrays.asList(iterables));
	}

	public SelectManyIterator(List<Iterable<T>> iterables) {
		this.iterables = iterables;
	}

	@Override
	public Iterator<T> iterator() {
		return null;
	}
}
