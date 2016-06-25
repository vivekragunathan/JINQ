package jinq.qa.tests;

import jinq.Enumerable;
import jinq.qa.shared.Name;
import jinq.qa.shared.Person;
import jinq.qa.shared.TestClauseProvider;
import jinq.qa.shared.Utils;
import jodash.Iterables;
import misc.DateTimeHelpers;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
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
				.select(/*personNameProvider*/);

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
					.select(/*Person.ElementSelectors.NameFunc*/);

			Utils.print(names, "testWithCustomClauseProvider: Default Clause Provider");
		}

		{
			final Iterable<Person> names2 = new Enumerable<>(persons, new TestClauseProvider<Person>())
					.where(Person.Predicates.IsAvailable)
					.select(/*Person.ElementSelectors.NameFunc*/);

			Utils.print(names2, "testWithCustomClauseProvider: Custom Clause Provider");
		}
	}

	@Test
	public void testFirstLast() throws Exception {

		final Iterable<Name> names = new Enumerable<>(persons)
				.where(Person.Predicates.IsAvailable)
				.select(p -> p.name);

		System.out.println(Iterables.toString(names));

		{
			final Person person = new Enumerable<>(persons)
					.where(Person.Predicates.IsAvailable)
					.first();

			System.out.println("First Available: " + person);
		}

		{
			final Person person = new Enumerable<>(persons)
					.where(Person.Predicates.IsAvailable)
					.last();

			System.out.println("Last Available: " + person);
		}
	}

	@Test
	public void testOrderBy() throws Exception {

		final String dir   = "/usr/local/bin/";
		final int    days  = 5;
		final File[] files = new File(dir).listFiles();

		System.out.printf("Print files from %s that were modified in the last %d days%n", dir, days);

		final Iterable<String> query = new Enumerable<>(files)
				.where(f -> DateTimeHelpers.getDaysDifference(f.lastModified()) <= 5)
				.orderBy((left, right) -> Long.compare(left.length(), right.length()))
				.select(File::getName);

		System.out.println(Iterables.toString(query, "\n"));
	}

	private static void printFiles(File[] files) {
		final DateFormat dateFormat = DateFormat.getDateInstance();
		final Date       today      = new Date();

		for (File file : files) {

			final Date start    = new Date(file.lastModified());
			final long daysDiff = DateTimeHelpers.getDaysDifference(start, today);

			System.out.printf(
					"%s [%s - %s]: %d days%n",
					file.getName(),
					dateFormat.format(start),
					dateFormat.format(today),
					daysDiff
			);
		}
	}
}