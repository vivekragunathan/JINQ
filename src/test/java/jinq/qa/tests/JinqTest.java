package jinq.qa.tests;

import jinq.Enumerable;
import jinq.qa.shared.Name;
import jinq.qa.shared.Person;
import jinq.qa.shared.TestClauseProvider;
import jinq.qa.shared.Utils;
import jodash.Iterables;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class JinqTest {

	private List<Person> persons;

	@Before
	public void setUp() throws Exception {
		persons = Utils.getPersons();
	}

	@Test
	public void printAvailableOddWeightPersons() {
		final Iterable<Person> persons = new Enumerable<>(this.persons)
				.where(Person.Predicates.IsAvailable)
				.where(Person.Predicates.IsOddWeight)
				.select();

		Utils.print(persons, "printAvailableOddWeightPersons");
	}

	@Test
	public void printAvailableEvenWeightPersonNames() {
		final Iterable<Name> names = new Enumerable<>(this.persons)
				.where(Person.Predicates.IsAvailable)
				.where(Person.Predicates.IsOddWeight)
				.select(Person.ElementSelectors.NameFunc);

		Utils.print(names, "printAvailableEvenWeightPersons");
	}

	@Test
	public void printWeights() {
		final Iterable<Double> weights = new Enumerable<>(this.persons)
				.select(Person.ElementSelectors.WeightFunc);

		Utils.print(weights, "printWeights", true);
	}

	@Test
	public void testOrderByThenWhereAndSelect() throws Exception {
		final Iterable<Name> selected = new Enumerable<>(persons)
				.orderBy(Person.Comparators.ByName)
				.where(Person.Predicates.IsAvailable)
				.select(Person.ElementSelectors.NameFunc);

		System.out.println(Iterables.toString(selected, "\n"));
	}

	@Test
	public void testWithCustomClauseProvider() throws Exception {

		{
			final Iterable<Person> names = new Enumerable<>(persons)
					.where(Person.Predicates.IsAvailable)
					.select();

			Utils.print(names, "testWithCustomClauseProvider: Default Clause Provider");
		}

		{
			final Iterable<Name> names2 = new Enumerable<>(persons, new TestClauseProvider<Person>())
					.where(Person.Predicates.IsAvailable)
					.select(Person.ElementSelectors.NameFunc);

			Utils.print(names2, "testWithCustomClauseProvider: Custom Clause Provider");
		}
	}

	private class MyFileClass {
		final String name;
		final String absolutePath;
		final long length;

		MyFileClass(File f) {
			this.name = f.getName();
			this.absolutePath = f.getAbsolutePath();
			this.length = f.length();
		}

		@Override
		public String toString() {
			return String.format("%s (%d) => %s", name, length, absolutePath);
		}
	}

	/*@Test
	public void testWithLambdas() throws Exception {
		final Iterable<Name> selected = new Enumerable<>(persons)
				.orderBy((me, him) -> me.name.compareTo(him.name))
				.where(p -> p.isAvailable)
				.select(p -> p.name);

		System.out.println(Iterables.toString(selected, "\n"));

		final Iterable<String> query = new Enumerable<>(new File(".").listFiles())
				.where(f -> f.getName().endsWith(".xml"))
				.orderBy((left, right) -> Long.compare(left.length(), right.length()))
				.select(File::getName);

		System.out.println(Iterables.toString(query, "\n"));

		final Iterable<MyFileClass> query2 = new Enumerable<>(new File(".").listFiles())
				.where(File::isHidden)
				.orderBy((left, right) -> Long.compare(left.length(), right.length()))
				.select(MyFileClass::new);

		System.out.println(Iterables.toString(query2, "\n"));
	}*/
}
