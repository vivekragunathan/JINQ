package jinq.qa.shared;

import java.util.List;
import java.util.Stack;

public class Numbers {

	public static <T extends Number> T sum(Iterable<T> items, T initial) {
		T result = initial;

		for (T n : items) {
			result = add(result, n);
		}

		return result;
	}

	public static <T extends Number> T diffLeft(Iterable<T> items, T initial) {
		T result = initial;

		for (T n : items) {
			result = subtract(result, n);
		}

		return result;
	}

	public static <T extends Number> T diffRight(Iterable<T> items, T initial) {

		final Stack<T> stack             = Utils.toStack(items);
		final List<T>  list              = Utils.toList(stack);
		final int      reverseStartIndex = list.size() - 1;
		int            index             = reverseStartIndex;
		T              result            = initial;

		for (T item : list) {
			final boolean isStartingWithNullInitialValue = index == reverseStartIndex && initial == null;
			final T       right                          = isStartingWithNullInitialValue ? null : item;

			result = isStartingWithNullInitialValue ? item : result;

			result = subtract(
					result,
					right
			);

			--index;
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Number> T add(T left, T right) {
		if (left instanceof Integer) {
			final Integer result = (Integer) left + (Integer) right;
			return (T) result;
		}

		if (left instanceof Long) {
			final Long result = (Long) left + (Long) right;
			return (T) result;
		}

		if (left instanceof Short) {
			final Short result = (short) ((Short) left + (Short) right);
			return (T) result;
		}

		if (left instanceof Double) {
			final Double result = (Double) left + (Double) right;
			return (T) result;
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Number> T subtract(T left, T right) {

		if (left instanceof Integer) {
			final Integer iLeft  = (Integer) left;
			final Integer iRight = right == null ? 0 : (Integer) right;
			final Integer result = iLeft - iRight;
			return (T) result;
		}

		if (left instanceof Long) {
			final Long lLeft  = (Long) left;
			final Long lRight = right == null ? 0 : (Long) right;
			final Long result = lLeft - lRight;
			return (T) result;
		}

		if (left instanceof Short) {
			final Short sLeft  = (Short) left;
			final Short sRight = right == null ? 0 : (Short) right;
			final Short result = (short) (sLeft - sRight);
			return (T) result;
		}

		if (left instanceof Double) {
			final Double dLeft  = (Double) left;
			final Double dRight = right == null ? 0 : (Double) right;
			final Double result = dLeft - dRight;
			return (T) result;
		}

		return null;
	}
}
