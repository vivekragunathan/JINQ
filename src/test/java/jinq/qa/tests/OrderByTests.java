package jinq.qa.tests;

import jinq.core.Enumerable;
import jinq.qa.shared.Person;
import jinq.qa.shared.Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class OrderByTests {

	private final List<Person> persons = new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		final List<Person> persons = Utils.getPersons();
		this.persons.clear();
		this.persons.addAll(persons);
	}

	@After
	public void tearDown() throws Exception {
		this.persons.clear();
	}

	@Test
	public void testOrderBy() throws Exception {

		final Iterable<Person> persons = new Enumerable<>(this.persons)
				.where(Person.Predicates.IsAvailable)
				.orderBy(Person.Comparators.ByFirstName);

		Utils.print(persons, 0, "testOrderBy(firstName)");
	}

	@Test
	public void testOrderByDesc() throws Exception {
		final Iterable<Person> persons = new Enumerable<>(this.persons)
				.orderBy(Person.Comparators.ByWeightDesc);

		Utils.print(persons, 0, "testOrderByDesc(weight)");
	}
}
