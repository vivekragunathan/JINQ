package jinq.qa.tests;

import jinq.core.Enumerable;
import jinq.clause.SelectIterable;
import jinq.qa.shared.Name;
import jinq.qa.shared.Person;
import jinq.qa.shared.TestClauseProvider;
import jinq.qa.shared.Utils;
import jodash.Iterables;
import misc.DateTimeHelpers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class JinqTest {

	private List<Person> allPersons;

	@Before
	public void setUp() throws Exception {
		allPersons = Utils.getPersons();
	}

	@Test
	public void printAvailableOddWeightPersons() {

		final SelectIterable<Person, Person> query = new Enumerable<>(this.allPersons)
				.where(Person.Predicates.IsAvailable)
				.where(Person.Predicates.IsOddWeight)
				.select(/*personNameProvider*/);

		final List<Person> persons = query.toList();
		final int          count   = query.count();

		Assert.assertTrue(persons.size() == count);

		Utils.print(persons, count, "printAvailableOddWeightPersons");
	}

	@Test
	public void printAvailableEvenWeightPersonNames() {

		final SelectIterable<Person, Name> query = new Enumerable<>(this.allPersons)
				.where(Person.Predicates.IsAvailable)
				.where(Person.Predicates.IsOddWeight)
				.select(Person.ElementSelectors.NameFunc);

		final List<Name> names = query.toList();
		final int        count = query.count();

		Utils.print(names, count, "printAvailableEvenWeightPersons (" + count + ")");
	}

	@Test
	public void printWeights() {
		final SelectIterable<Person, Double> query = new Enumerable<>(this.allPersons)
				.select(Person.ElementSelectors.WeightFunc);

		final List<Double> weights = query.toList();
		final int          count   = query.count();

		Utils.print(weights, count, "printWeights", true);
	}

	@Test
	public void testWithCustomClauseProvider() throws Exception {

		{
			final SelectIterable<Person, Person> query = new Enumerable<>(allPersons)
					.where(Person.Predicates.IsAvailable)
					.select(/*Person.ElementSelectors.NameFunc*/);

			final List<Person> persons = query.toList();
			final int          count   = query.count();

			Assert.assertTrue(persons.size() == count);

			Utils.print(persons, count, "testWithCustomClauseProvider: Default Clause Provider");
		}

		{
			final SelectIterable<Person, Person> query = new Enumerable<>(allPersons, new TestClauseProvider<>())
					.where(Person.Predicates.IsAvailable)
					.select(/*Person.ElementSelectors.NameFunc*/);

			final List<Person> persons = query.toList();
			final int          count   = query.count();

			Assert.assertTrue(persons.size() == count);

			Utils.print(persons, count, "testWithCustomClauseProvider: Custom Clause Provider");
		}
	}

	@Test
	public void testFirstLast() throws Exception {

		final SelectIterable<Person, Name> query = new Enumerable<>(allPersons)
				.where(Person.Predicates.IsAvailable)
				.select(Person::getName);

		final List<Name> names = query.toList();
		final int        count = query.count();

		Assert.assertTrue(names.size() == count);

		Utils.print(names, count, "testFirstLast(isAvailable, getName)");

		System.out.println(Iterables.toString(names));

		{
			final Person person = new Enumerable<>(allPersons)
					.where(Person.Predicates.IsAvailable)
					.first();

			System.out.println("First Available: " + person);
		}

		{
			final Person person = new Enumerable<>(allPersons)
					.where(Person.Predicates.IsAvailable)
					.last();

			System.out.println("Last Available: " + person);
		}
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