package data;

import misc.Defaults;

public class TextHelpers {

	private TextHelpers() {
	}

	public static boolean isNotEmpty(String text) {
		return !isEmpty(text);
	}

	public static boolean isEmpty(String text) {
		return text == null || text.length() == 0;
	}

	public static boolean isNotBlank(String text) {
		return !isBlank(text);
	}

	public static boolean isBlank(String text) {

		if (isEmpty(text)) {
			return true;
		}

		for (int index = 0; index < text.length(); ++index) {
			if (!Character.isSpaceChar(text.charAt(index))) {
				return false;
			}
		}

		return true;
	}

	public static String defaultIfNull(String input) {
		return defaultIfNull(input, Defaults.EMPTY_STRING);
	}

	public static String defaultIfNull(String input, String fallback) {
		return input == null ? fallback : input;
	}

	public static String defaultIfBlank(String input) {
		return defaultIfBlank(input, Defaults.EMPTY_STRING);
	}

	public static String defaultIfBlank(String input, String fallback) {
		return isBlank(input) ? fallback : input;
	}
}
