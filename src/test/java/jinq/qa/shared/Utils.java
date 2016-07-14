package jinq.qa.shared;

import jodash.Iterables;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Utils {

	private Utils() {
	}

	public static <T> void print(Iterable<T> source, int count, String title) {
		print(source, count, title, false);
	}

	public static <T> void print(Iterable<T> source, int count, String title, boolean itemsOnSingleLine) {

		final String message = source == null
		                       ? title
		                       : String.format("%s (%d~%d)", title, Iterables.count(source), count);

		printTestTitle(message);

		if (source == null) {
			return;
		}

		final String conjunction = itemsOnSingleLine ? ", " : "\n";

		for (T item : source) {

			if (item instanceof Iterable) {
				final Iterable iterable = (Iterable) item;
				final int      size     = Iterables.count(iterable);
				print(iterable, size, "<iterable>", itemsOnSingleLine);
				continue;
			}

			System.out.print(item + conjunction);
		}
	}

	public static void printTestTitle(String title) {
		if (title != null) {
			System.out.println("\n[ " + title + " ] ---------------------------------- \n");
		}
	}

	public static List<Person> getPersons() {

		final List<Person> persons = new ArrayList<>();

		for (int index = 1; index <= 15; ++index) {

			final String suffix = String.format("F%02d", 15 - index);

			final Person person = new Person(
					Name.deriveName(suffix),
					49 + index,
					index % 2 == 0
			);

			persons.add(person);
		}

		return persons;
	}
}