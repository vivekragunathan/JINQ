package jinq;

import java.util.Iterator;

public interface IEnumerator<T> extends Iterator<T> {
	boolean moveNext();
	T current();
}
