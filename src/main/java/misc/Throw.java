package misc;

import data.TextHelpers;

public class Throw {

	public static <T> void whenNull(T obj) {
		whenNull(obj, "Encountered null object while processing");
	}

	public static <T> void whenNull(T obj, String message) {
		if (obj == null) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void ifBlank(String text, String message) throws Exception {
		Throw.ifTrue(TextHelpers.isBlank(text), message);
	}

	public static void whenBlank(String text, String message) {
		whenTrue(TextHelpers.isBlank(text), message);
	}

	public static void ifTrue(boolean condition, String message) throws Exception {
		if (condition) {
			throw new Exception(message);
		}
	}

	public static void whenTrue(boolean condition, String message) {
		if (condition) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void ifFalse(boolean condition, String message) throws Exception {
		ifTrue(!condition, message);
	}

	public static void whenFalse(boolean condition, String message) {
		whenTrue(!condition, message);
	}
}