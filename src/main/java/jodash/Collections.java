package jodash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Collections {

	public static <T> boolean isNullOrEmpty(T[] array) {
		return array == null || array.length == 0;
	}

	public static <T> boolean isNullOrEmpty(Iterable<T> items) {
		return items == null || !items.iterator().hasNext();
	}

	public static <T> List<T> asList(Collection<T> collection) {
		return collection instanceof List ? (List) collection : new ArrayList<T>(collection);
	}
}
