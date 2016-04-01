package jinq.qa.shared;

import delegates.Func;
import delegates.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Utils {

	private Utils() {
	}

	public static <T> Stack<T> toStack(Iterable<T> items) {
		final Stack<T> stack = new Stack<>();

		for (T item : items) {
			stack.push(item);
		}

		return stack;
	}

	public static <T> List<T> toList(Stack<T> stack) {
		final List<T> list = new ArrayList<>(stack.size());

		for (int index = stack.size() - 1; index >= 0; index--) {
			list.add(stack.get(index));
		}

		return list;
	}

	public static <T> Predicate<T> makeTruthyPredicate() {
		return new Predicate<T>() {
			@Override
			public boolean evaluate(T value) {
				return value != null;
			}
		};
	}

	public static <T> Predicate<T> makeFalsePredicate() {
		return new Predicate<T>() {
			@Override
			public boolean evaluate(T value) {
				return false;
			}
		};
	}

	public static <T> Func<T, T> makeIdentityTransformer() {
		return new Func<T, T>() {
			@Override
			public T apply(T item) {
				return item;
			}
		};
	}


	public static <T> void print(Iterable<T> source, String title) {
		print(source, title, false);
	}

	public static <T> void print(Iterable<T> source, String title, boolean itemsOnSingleLine) {
		printTestTitle(title);

		if (source == null) {
			return;
		}

		final String conjunction = itemsOnSingleLine ? ", " : "\n";

		for (T item : source) {

			if (item instanceof Iterable) {
				print((Iterable) item, null, itemsOnSingleLine);
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
