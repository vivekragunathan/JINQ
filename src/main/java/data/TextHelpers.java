package data;

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
}
