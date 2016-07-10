package jodash;

import data.TextHelpers;
import delegates.*;
import misc.Defaults;

import java.util.*;

public class Iterables {

	private Iterables() {
	}

	public static <T> void forEach(Iterable<T> haystack, Action<T> action) {
		forEach(
				haystack,
				action,
				false,
				false
		);
	}

	public static <T> void forEachNonNull(Iterable<T> haystack, Action<T> action) {
		forEach(
				haystack,
				action,
				true,
				false
		);
	}

	public static <T> List<Exception> forEach(Iterable<T> haystack,
	                                          final Action<T> action,
	                                          boolean skipNulls,
	                                          boolean swallowExceptions) {

		return forEach(
				haystack,
				new Action2Shim<T, Integer>(action),
				skipNulls,
				swallowExceptions
		);
	}

	public static <T> void forEach(Iterable<T> haystack, Action2<T, Integer> action) {
		forEach(
				haystack,
				action,
				false,
				false
		);
	}

	public static <T> void forEachNonNull(Iterable<T> haystack, Action2<T, Integer> action) {
		forEach(
				haystack,
				action,
				true,
				false
		);
	}

	public static <T> List<Exception> forEach(Iterable<T> haystack,
	                                          Action2<T, Integer> action,
	                                          boolean skipNulls,
	                                          boolean swallowExceptions) {
		List<Exception> exceptions = null;
		int             index      = 0;

		for (T needle : haystack) {
			if (needle == null && skipNulls) {
				continue;
			}

			if (!swallowExceptions) {
				action.apply(needle, index++);
				continue;
			}

			if (exceptions == null) {
				exceptions = new ArrayList<>();
			}

			try {
				action.apply(needle, index++);
			} catch (Exception ex) {
				ex.printStackTrace();
				exceptions.add(ex);
			}
		}

		return exceptions;
	}

	public static <T> boolean exists(Iterable<T> haystack, Predicate<T> predicate) {
		final T item = find(haystack, predicate);
		return item != null;
	}

	public static <T> T find(Iterable<T> haystack, Predicate<T> predicate) {
		for (T item : haystack) {
			if (predicate.evaluate(item)) {
				return item;
			}
		}

		return null;
	}

	public static <T> T find(Iterable<T> haystack, T needle) {
		for (T current : haystack) {
			if (current.equals(needle)) {
				return current;
			}
		}

		return null;
	}

	public static <T, R> Iterable<R> select(Iterable<T> items, Func<T, R> transformer) {
		return select(items, null, transformer);
	}

	public static <T, R> Iterable<R> select(Iterable<T> items,
	                                        Predicate<T> predicate,
	                                        Func<T, R> transformer) {
		return smartSelect(
				items,
				predicate,
				transformer,
				false,
				false
		);
	}

	public static <T> List<T> toList(Iterable<T> items) {
		if (items instanceof List) {
			return (List<T>) items;
		}

		final Iterable<T> iterable = smartSelect(
				items,
				null,
				null,
				false,
				false
		);

		return (List<T>) iterable;
	}

	public static <T> Set<T> distinct(Iterable<T> items) {
		return distinct(items, null, null);
	}

	public static <T, R> Set<R> distinct(Iterable<T> items, Func<T, R> selector) {
		return distinct(items, null, selector);
	}

	public static <T, R> Set<R> distinct(Iterable<T> items,
	                                     Predicate<T> predicate,
	                                     Func<T, R> selector) {
		final Iterable<R> iterable = smartSelect(
				items,
				predicate,
				selector,
				false,
				true
		);

		return (Set<R>) iterable;
	}

	public static <T> Iterable<T> remove(Iterable<T> items, Predicate<T> predicate) {
		final Collection<T> out      = new ArrayList<>();
		final Iterator<T>   iterator = items.iterator();

		while (iterator.hasNext()) {
			final T next = iterator.next();
			if (predicate.evaluate(next)) {
				out.add(next);
				iterator.remove();
			}
		}

		return out;
	}

	@SuppressWarnings("unchecked")
	public static <T, R> Iterable<R> smartSelect(Iterable<T> items,
	                                             Predicate<T> predicate,
	                                             Func<T, R> transformer,
	                                             boolean skipNullItems,
	                                             boolean returnDistinct) {

		final Collection<R> out            = returnDistinct ? new HashSet<R>() : new ArrayList<R>();
		final boolean       hasTransformer = transformer != null;
		final boolean       hasPredicate   = predicate != null;

		if (items == null) {
			return out;
		}

		for (T item : items) {
			if (item == null && skipNullItems) {
				continue;
			}

			if (hasPredicate && !predicate.evaluate(item)) {
				continue;
			}

			out.add(hasTransformer ? transformer.apply(item) : (R) item);
		}

		return out;
	}

	public static <T, R> R foldLeft(Iterable<T> items, R initial, Func2<T, R, R> folder) {
		R result = initial;

		for (T item : items) {
			result = folder.apply(item, result);
		}

		return result;
	}

	/**
	 * case Nil => z, case x :: xs => f(x, xs.foldRight(z)(f))
	 */
	public static <T, R> R foldRight(Iterable<T> items, R initial, Func2<T, R, R> folder) {
		return foldRight(items.iterator(), initial, folder);
//		throw new UnsupportedOperationException("foldRight is not yet implemented");
	}

	private static <T, R> R foldRight(Iterator<T> iterator, R aggregate, Func2<T, R, R> folder) {
		if (!iterator.hasNext()) {
			return aggregate;
		}

		return folder.apply(
				iterator.next(),
				foldRight(iterator, aggregate, folder)
		);
	}

	public static <T> int count(Iterable<T> iterable) {
		return count(iterable, null);
	}

	public static <T> int count(Iterable<T> iterable, Predicate<T> predicate) {
		int count = 0;

		for (T item : iterable) {
			count += predicate == null || predicate.evaluate(item) ? 1 : 0;
		}

		return count;
	}

	public static <T> String toString(Iterable<T> items) {
		return toString(
				items,
				Defaults.CONJUNCTION_DELIMITER,
				Defaults.CONJUNCTION_PREFIX,
				Defaults.CONJUNCTION_SUFFIX
		);
	}

	public static <T> String toString(Iterable<T> items, String delimiter) {
		return toString(
				items,
				delimiter,
				Defaults.CONJUNCTION_PREFIX,
				Defaults.CONJUNCTION_SUFFIX
		);
	}

	public static <T> String toString(Iterable<T> items, String delimiter, String prefix, String suffix) {

		final String pfx = TextHelpers.defaultIfNull(prefix);
		final String sfx = TextHelpers.defaultIfBlank(suffix);

		if (items == null) {
			return pfx + sfx;
		}

		return String.format(
				"%s%s%s",
				pfx,
				concat(items, delimiter),
				sfx
		);
	}

	@SafeVarargs
	public static <T> String concat(T... items) {
		return concat2(Defaults.CONJUNCTION_DELIMITER, items);
	}

	@SafeVarargs
	public static <T> String concat2(String delimiter, T... items) {
		if (items == null) {
			return Defaults.EMPTY_STRING;
		}

		final String        conjunction     = TextHelpers.defaultIfNull(delimiter);
		final int           delimiterLength = conjunction.length();
		final StringBuilder sb              = new StringBuilder();
		int                 lastInsertIndex = -1;

		for (T item : items) {
			sb.append(item.toString());
			sb.append(conjunction);
			lastInsertIndex = sb.length() - delimiterLength;
		}

		if (lastInsertIndex >= 0) {
			sb.replace(lastInsertIndex, sb.length(), Defaults.EMPTY_STRING);
		}

		return sb.toString();
	}
}
