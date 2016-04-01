package misc;

public class Tidbits {

	@SuppressWarnings("unchecked")
	public static <T> T silentCast(Object obj, Class<T> clazz) {
		return clazz.isInstance(obj) ? (T) obj : null;
	}
}