package misc;

import java.io.*;

public class FileHelpers {

	public static <T> T deserializeFile(String filePath, Class<T> clazz) throws IOException, ClassNotFoundException {
		final InputStream       is      = new FileInputStream(filePath);
		final ObjectInputStream ois     = new ObjectInputStream(new BufferedInputStream(is));
		final Object            content = ois.readObject();
		return Tidbits.silentCast(content, clazz);
	}
}
