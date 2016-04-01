package jinq.qa.shared;

public class Number2<T extends Number> extends Number {

	private final T number;

	public Number2(T number) {
		this.number = number;
	}

	public boolean isInt() {
		return number instanceof Integer;
	}

	public boolean isLong() {
		return number instanceof Long;
	}

	public boolean isShort() {
		return number instanceof Short;
	}

	public boolean isDouble() {
		return number instanceof Double;
	}

	public boolean isFloat() {
		return number instanceof Float;
	}

	@SuppressWarnings("unchecked")
	public <K extends Number> K cast() {
		return (K) number;
	}

	@SuppressWarnings("unchecked")
	public T add(T right) {

		if (isInt()) {
			final Integer result = this.<Integer>cast() + (Integer) right;
			return (T) result;
		}

		if (isLong()) {
			final Long result = (Long) number + (Long) right;
			return (T) result;
		}

		if (isShort()) {
			final Short result = (short) ((Short) number + (Short) right);
			return (T) result;
		}

		if (isFloat()) {
			final Float result = (Float) number + (Float) right;
			return (T) result;
		}

		if (isDouble()) {
			final Double result = (Double) number + (Double) right;
			return (T) result;
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public T subtract(T right) {

		if (isInt()) {
			final Integer iLeft  = (Integer) number;
			final Integer iRight = right == null ? 0 : (Integer) right;
			final Integer result = iLeft - iRight;
			return (T) result;
		}

		if (isLong()) {
			final Long lLeft  = (Long) number;
			final Long lRight = right == null ? 0 : (Long) right;
			final Long result = lLeft - lRight;
			return (T) result;
		}

		if (isShort()) {
			final Short sLeft  = (Short) number;
			final Short sRight = right == null ? 0 : (Short) right;
			final Short result = (short) (sLeft - sRight);
			return (T) result;
		}

		if (isFloat()) {
			final Float dLeft  = (Float) number;
			final Float dRight = right == null ? 0 : (Float) right;
			final Float result = dLeft - dRight;
			return (T) result;
		}

		if (isDouble()) {
			final Double dLeft  = (Double) number;
			final Double dRight = right == null ? 0 : (Double) right;
			final Double result = dLeft - dRight;
			return (T) result;
		}

		return null;
	}

	@Override
	public int intValue() {
		return number.intValue();
	}

	@Override
	public long longValue() {
		return number.longValue();
	}

	@Override
	public float floatValue() {
		return number.floatValue();
	}

	@Override
	public double doubleValue() {
		return number.doubleValue();
	}
}
