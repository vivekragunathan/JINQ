package misc;

import java.util.*;

public class Defaults {
	public static final String CONJUNCTION_PREFIX    = "[";
	public static final String CONJUNCTION_SUFFIX    = "]";
	public static final String CONJUNCTION_DELIMITER = ", ";

	public static final  String EMPTY_STRING = "";
	private static final List   EMPTY_LIST   = Collections.unmodifiableList(new ArrayList());
	private static final Map    EMPTY_MAP    = Collections.unmodifiableMap(new HashMap());

	@SuppressWarnings("unchecked")
	public static <T> List<T> emptyList() {
		return EMPTY_LIST;
	}

	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> emptyMap() {
		return EMPTY_MAP;
	}
}
