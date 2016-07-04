package jinq;

import misc.Throw;

import java.util.Iterator;

public class RelayIterable<T> implements Iterable<T> {

	protected final Iterable<T> source;

	public RelayIterable(Iterable<T> source) {
		Throw.whenNull(source, "Encountered null iterable object");
		this.source = source;
	}

	@Override
	public Iterator<T> iterator() {
		return new RelayIterator<>(source);
	}
}
