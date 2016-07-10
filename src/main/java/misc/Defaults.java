package misc;

import java.util.*;

public class Defaults {

	private static final List     EMPTY_LIST_REF  = Collections.unmodifiableList(new ArrayList<>());
	private static final Set      EMPTY_SET_REF   = Collections.unmodifiableSet(new HashSet<>());
	private static final Map      EMPTY_MAP       = Collections.unmodifiableMap(new HashMap<>());
	private static final Object[] EMPTY_ARRAY_REF = EMPTY_LIST_REF.toArray();

	public static final String EMPTY_STRING = "";

	public static final String CONJUNCTION_PREFIX    = "[";
	public static final String CONJUNCTION_SUFFIX    = "]";
	public static final String CONJUNCTION_DELIMITER = ", ";

	@SuppressWarnings("unchecked")
	public static <T> T[] emptyArray() {
		return (T[]) EMPTY_ARRAY_REF;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> emptyList() {
		return EMPTY_LIST_REF;
	}

	@SuppressWarnings("unchecked")
	public static <T> Set<T> emptySet() {
		return EMPTY_SET_REF;
	}

	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> emptyMap() {
		return EMPTY_MAP;
	}
}
