package misc;

import java.io.*;

public class Helpers {

	public static <T> T deserializeFile(String filePath, Class<T> clazz) throws IOException, ClassNotFoundException {
		final InputStream       is      = new FileInputStream(filePath);
		final ObjectInputStream ois     = new ObjectInputStream(new BufferedInputStream(is));
		final Object            content = ois.readObject();
		return Tidbits.silentCast(content, clazz);
	}

	public static long getLast4OfTicks() {
		final String ticks = String.valueOf(System.currentTimeMillis());

		final int totalLen   = ticks.length();
		final int numLen     = 4;
		int       tenthPower = numLen - 1;

		long finalValue = 0;

		for (int index = totalLen - numLen; index < totalLen; ++index) {
			final int number = ticks.charAt(index) - '0';
			finalValue += number * Math.pow(10, tenthPower--);
		}

		return finalValue < 1000 ? finalValue * 10 : finalValue;
	}
}
