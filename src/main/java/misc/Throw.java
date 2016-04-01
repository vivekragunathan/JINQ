package misc;

import data.TextHelpers;

public class Throw {

	public static <T> void WhenNull(T obj) {
		WhenNull(obj, "Encountered null object while processing");
	}

	public static <T> void WhenNull(T obj, String message) {
		if (obj == null) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void IfTrue(boolean condition, String message) throws Exception {
		if (condition) {
			throw new Exception(message);
		}
	}

	public static void WhenTrue(boolean condition, String message) {
		if (condition) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void IfFalse(boolean condition, String message) throws Exception {
		IfTrue(!condition, message);
	}

	public static void WhenFalse(boolean condition, String message) {
		WhenTrue(!condition, message);
	}

	public static void ThrowIfBlank(String text, String message) throws Exception {
		Throw.IfTrue(TextHelpers.isBlank(text), message);
	}

	public static void WhenBlank(String text, String message) {
		WhenTrue(TextHelpers.isBlank(text), message);
	}
}